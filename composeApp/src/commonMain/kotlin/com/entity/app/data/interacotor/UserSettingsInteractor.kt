package com.entity.app.data.interacotor

import com.entity.app.data.api.UserSettingsApi
import com.entity.app.data.repository.UserSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class UserSettingsInteractor constructor(
  private val api: UserSettingsApi,
  private val repository: UserSettingsRepository,
  ) {
  @OptIn(ExperimentalCoroutinesApi::class)
  fun isUserAuthFlow(): Flow<Boolean> {
    return repository.userTokenFlow.mapLatest { it.accessToken.isNotBlank() }
  }

  fun isUserAuth(): Boolean {
    return repository.userTokenFlow.value.accessToken.isNotBlank()
  }

  suspend fun authUser(userName: String, password: String) {
    api.postAuthUser(userName, password)
  }

  suspend fun registerUser(userName: String, password: String, name: String) {
    api.postRegisterUser(userName, password, name)
  }

  fun logoutUser() {
    repository.removeTokens()
    api.removeUserToken()
  }

}