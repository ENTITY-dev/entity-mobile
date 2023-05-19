package com.entity.app.data.api

import com.entity.app.data.model.ScenesResponseModel
import com.entity.app.data.model.TokensResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType

class UserSettingsApi constructor(
  private val client: HttpClient,
) {

  suspend fun postAuthUser(userName: String, password: String): TokensResponseModel {
    val response: TokensResponseModel = client.submitForm(url = URL_AUTH, formParameters = Parameters.build {
      append(USERNAME_PARAM, userName)
      append(PASSWORD_PARAM, password)
    }) {
      headers { }
    }.body()
    return response
  }

  suspend fun postRegisterUser(userName: String, password: String, name: String): TokensResponseModel {
    val response: TokensResponseModel = client.submitForm(url = URL_REGISTER, formParameters = Parameters.build {
      append(USERNAME_PARAM, userName)
      append(NAME_PARAM, name)
      append(PASSWORD_PARAM, password)
    }) {
      contentType(ContentType.Application.Json)
      headers { }
    }.body()
    return response
  }

  private companion object {
    const val URL_AUTH = "/backend/auth/login"
    const val URL_REGISTER = "/backend/auth/register"
    const val USERNAME_PARAM = "username"
    const val NAME_PARAM = "name"
    const val PASSWORD_PARAM = "password"
  }

}