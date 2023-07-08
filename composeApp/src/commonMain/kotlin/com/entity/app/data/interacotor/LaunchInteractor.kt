package com.entity.app.data.interacotor

import com.entity.app.data.repository.LaunchRepository

class LaunchInteractor(
  private val launchRepository: LaunchRepository
) {
  suspend fun getPromoSceneId(): String? {
    return launchRepository.getLaunchResponse().url
  }
}