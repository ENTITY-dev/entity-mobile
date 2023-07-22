package com.entity.app.data.interacotor

import com.entity.app.data.repository.UserInfoRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UserInfoInteractor(
  private val userInfoRepository: UserInfoRepository,
) {

  suspend fun getUserInfo() = withContext(Dispatchers.IO) {
    try {
      userInfoRepository.getUserInfo()
    } catch (error: Exception) {
      Napier.e(error.message ?: "", error)
    }
  }

}