package com.entity.app.di

import com.entity.app.utils.HttpEngineFactory
import com.entity.app.data.repository.FeedListRepository
import com.entity.app.data.interacotor.UserSettingsInteractor
import com.entity.app.data.api.FeedListApi
import com.entity.app.data.api.UserSettingsApi
import com.entity.app.data.repository.UserSettingsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val DEFAULT_HOST = "entity.lol"
private const val CONNECT_TIMEOUT_MS = 15000L
private const val REQUEST_TIMEOUT_MS = 5000L

val commonModule = module {
  singleOf(::FeedListRepository)
  singleOf(::UserSettingsRepository)

  singleOf(::FeedListApi)
  singleOf(::UserSettingsApi)


  factoryOf(::UserSettingsInteractor)

  single {
    HttpClient(HttpEngineFactory().createEngine()) {
      install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
      }

      install(DefaultRequest)

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
          host = DEFAULT_HOST
        }
        contentType(ContentType.Application.Json)
      }

      install(HttpTimeout) {
        connectTimeoutMillis = CONNECT_TIMEOUT_MS
        requestTimeoutMillis = REQUEST_TIMEOUT_MS
      }
    }
  }
}