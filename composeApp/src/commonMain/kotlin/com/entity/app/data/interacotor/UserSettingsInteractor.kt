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
    return repository.userTokenFlow.mapLatest { !it.isNullOrEmpty() }
  }

  suspend fun authUser(userName: String, password: String) {
    val response = api.postAuthUser(userName, password)
    repository.saveUserTokens(response.tokens.accessToken, response.tokens.refreshToken)
  }

  suspend fun registerUser(userName: String, password: String, name: String) {
    val response = api.postRegisterUser(userName, password, name)
    repository.saveUserTokens(response.tokens.accessToken, response.tokens.refreshToken)
  }

  suspend fun userIsAuthorised(): Boolean {
    return !repository.getAccessToken().isNullOrEmpty() || !repository.getRefreshToken().isNullOrEmpty()
  }

  fun logoutUser() {
    repository.removeTokens()
  }

}