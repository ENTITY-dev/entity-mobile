package com.entity.app.data.interacotor

import com.entity.app.data.api.UserSettingsApi
import com.entity.app.data.repository.UserSettingsRepository
import io.ktor.client.HttpClient


class UserSettingsInteractor constructor(
  private val api: UserSettingsApi,
  private val repository: UserSettingsRepository,
) {
  suspend fun authUser(userName: String, password: String) {
    val response = api.postAuthUser(userName, password)
    repository.saveUserTokens(response.tokens.accessToken, response.tokens.refreshToken)
  }

  suspend fun registerUser(userName: String, password: String, name: String) {
    val response = api.postRegisterUser(userName, password, name)
    repository.saveUserTokens(response.tokens.accessToken, response.tokens.refreshToken)
  }

}