package com.entity.app.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm

class UserInfoApi constructor(
  private val client: HttpClient,
) {

  suspend fun getCurrentUserInfo() {
    val response = client.submitForm(url = URL_USER_INFO, encodeInQuery = true).body<Any>()
  }


  companion object {
    const val URL_USER_INFO = "/backend/user/info"
  }

}