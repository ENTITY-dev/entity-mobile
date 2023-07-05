package com.entity.app.data.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType

class UserSettingsApi constructor(
  private val client: HttpClient,
) {

  private val bearerAuthProvider = client.plugin(Auth).providers
    .filterIsInstance<BearerAuthProvider>()
    .first()

  suspend fun postAuthUser(userName: String, password: String) {
    val response = client.submitForm(url = URL_AUTH, formParameters = Parameters.build {
      append(USERNAME_PARAM, userName)
      append(PASSWORD_PARAM, password)
    })
    refreshUserToken(response)
  }

  suspend fun postRegisterUser(userName: String, password: String, name: String) {
    val response = client.submitForm(url = URL_REGISTER, formParameters = Parameters.build {
      append(USERNAME_PARAM, userName)
      append(NAME_PARAM, name)
      append(PASSWORD_PARAM, password)
    }) {
      contentType(ContentType.Application.Json)
    }
    refreshUserToken(response)
  }

  fun removeUserToken() {
    bearerAuthProvider.clearToken()
  }

  private suspend fun refreshUserToken(response: HttpResponse) {
    bearerAuthProvider.refreshToken(response)
  }

  companion object {
    const val URL_AUTH = "/backend/auth/login"
    const val URL_REGISTER = "/backend/auth/register"
    const val URL_REFRESH = "backend/auth/refresh"
    private const val USERNAME_PARAM = "username"
    private const val NAME_PARAM = "name"
    private const val PASSWORD_PARAM = "password"
  }

}