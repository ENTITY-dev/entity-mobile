package com.entity.app.data.interacotor

import com.entity.app.data.api.LaunchScreenApi

class LaunchInteractor(
  private val launchScreenApi: LaunchScreenApi
) {
  suspend fun getPromoSceneId(): String? {
    return launchScreenApi.getLaunchResponse().url
  }
}