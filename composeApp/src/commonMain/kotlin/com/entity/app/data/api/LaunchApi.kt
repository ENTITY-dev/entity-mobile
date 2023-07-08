package com.entity.app.data.api

import com.entity.app.data.model.LaunchResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.url

class LaunchApi(
  private val client: HttpClient,
) {
  suspend fun getLaunchResponse(): LaunchResponseModel {
    return client.submitForm(encodeInQuery = true) { url(URL_LAUNCH) }.body()
  }

  companion object {
    const val URL_LAUNCH = "/backend/launch"
  }
}