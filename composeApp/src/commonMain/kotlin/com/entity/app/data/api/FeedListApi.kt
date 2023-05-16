package com.entity.app.data.api

import com.entity.app.data.model.ScenesResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class FeedListApi constructor(
  private val client: HttpClient,
) {
  suspend fun getFeedPostResponseModels(skipValue: Int): ScenesResponseModel {
    val response: ScenesResponseModel = client.get {
      url(URL_SCENES)
      headers { }
      formData {
        parameter(LIMIT_PARAM, LIMIT_VALUE)
        parameter(SKIP_PARAM, skipValue)
      }
    }.body()
    return response
  }

  private companion object {
    const val URL_SCENES = "/scenes"
    const val LIMIT_PARAM = "limit"
    const val LIMIT_VALUE = 10
    const val SKIP_PARAM = "skip"
  }

}