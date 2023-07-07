package com.entity.app.data.api

import com.entity.app.data.model.LaunchResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.url
import kotlin.jvm.Volatile

class LaunchScreenApi(
  private val client: HttpClient,
) {

  @Volatile
  private var lastResponse: LaunchResponseModel? = null

  suspend fun getLaunchResponse(): LaunchResponseModel {
    val lastResponse = lastResponse
    return if (lastResponse == null) {
      val response: LaunchResponseModel = client.submitForm(encodeInQuery = true) { url(URL_LAUNCH) }.body()
      this.lastResponse = response
      response
    } else {
      lastResponse
    }
  }

  companion object {
    const val URL_LAUNCH = "/backend/launch"
  }
}