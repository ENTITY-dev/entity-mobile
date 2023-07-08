package com.entity.app.utils

import com.entity.app.data.api.UserSettingsApi
import com.entity.app.data.model.Tokens
import com.entity.app.data.model.TokensResponseModel
import com.entity.app.data.repository.UserSettingsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.http.ContentType.Application
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

const val ENTITY_DEFAULT_HOST = "entity.lol"
private const val CONNECT_TIMEOUT_MS = 15000L
private const val REQUEST_TIMEOUT_MS = 15000L

@OptIn(ExperimentalSerializationApi::class)
class EntityHttpClientFactory(
  private val userSettingsRepository: UserSettingsRepository,
) {
  fun create() = HttpClient(HttpEngineFactory().createEngine()) {
    install(Logging) {
      logger = Logger.SIMPLE
      level = ALL
    }

    install(ContentNegotiation) {
      json(
        Json {
          isLenient = true
          ignoreUnknownKeys = true
          prettyPrint = true
          explicitNulls = false
        }
      )
    }

    defaultRequest {
      url {
        protocol = URLProtocol.HTTPS
        host = ENTITY_DEFAULT_HOST
      }
      contentType(Application.Json)
    }

    install(HttpTimeout) {
      connectTimeoutMillis = CONNECT_TIMEOUT_MS
      requestTimeoutMillis = REQUEST_TIMEOUT_MS
    }

    install(Auth) {
      bearer {
        loadTokens {
          userSettingsRepository.getUserTokens()
        }
        sendWithoutRequest { request ->
          !request.url.toString().contains(UserSettingsApi.URL_REFRESH) &&
              !request.url.toString().contains(UserSettingsApi.URL_AUTH) &&
              !request.url.toString().contains(UserSettingsApi.URL_REGISTER)
        }
        refreshTokens {
          val tokensResponse: Tokens = if (response.status == Unauthorized) {
            requestNewTokens()
          } else {
            response.body<TokensResponseModel>().tokens // expect auth or register request
          }
          val newAccessToken = tokensResponse.accessToken
          val newRefreshToken = tokensResponse.refreshToken
          userSettingsRepository.saveUserTokens(newAccessToken, newRefreshToken)
          userSettingsRepository.userTokenFlow.value
        }
      }
    }
  }

  private suspend inline fun RefreshTokensParams.requestNewTokens(): Tokens =
    client.submitForm(
      url = "https://${ENTITY_DEFAULT_HOST}/${UserSettingsApi.URL_REFRESH}",
    ) {
      markAsRefreshTokenRequest()
      headers {
        bearerAuth(userSettingsRepository.userTokenFlow.value.refreshToken)
      }
    }.body()

}