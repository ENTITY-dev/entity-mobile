package com.entity.app.data.repository

import com.entity.app.data.api.LaunchApi
import com.entity.app.data.model.LaunchResponseModel
import kotlin.jvm.Volatile

class LaunchRepository constructor(
  private val launchApi: LaunchApi,
) {

  @Volatile
  private var lastResponse: LaunchResponseModel? = null

  suspend fun getLaunchResponse(): LaunchResponseModel {
    val lastResponse = lastResponse
    return if (lastResponse == null) {
      val response = launchApi.getLaunchResponse()
      this.lastResponse = response
      response
    } else {
      lastResponse
    }
  }
}