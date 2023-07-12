package com.entity.app.data.interacotor

import com.entity.app.data.repository.UserInfoRepository
import io.github.aakira.napier.Napier

class UserInfoInteractor(
  private val userInfoRepository: UserInfoRepository,
) {

  suspend fun getUserInfo() {
    try {
      userInfoRepository.getUserInfo()
    } catch (error: Exception) {
      Napier.e(error.message ?: "", error)
    }
  }

}