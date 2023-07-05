package com.entity.app.data.repository

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSettingsRepository {

  private val settings = Settings()

  private val _userTokenFlow = MutableStateFlow(BearerTokens(getAccessToken(), getRefreshToken()))
  val userTokenFlow = _userTokenFlow.asStateFlow()

  fun saveUserTokens(accessToken: String, refreshToken: String) {
    settings[ACCESS_TOKEN_KEY] = accessToken
    settings[REFRESH_TOKEN_KEY] = refreshToken
    _userTokenFlow.value = BearerTokens(accessToken, refreshToken)
  }

  fun getUserTokens() = userTokenFlow.value

  fun removeTokens() {
    settings[ACCESS_TOKEN_KEY] = ""
    settings[REFRESH_TOKEN_KEY] = ""
    _userTokenFlow.value = BearerTokens(getAccessToken(), getRefreshToken())
  }

  fun clearSettings() {
    settings.clear()
  }

  private fun getAccessToken() = settings.getString(ACCESS_TOKEN_KEY, "")

  private fun getRefreshToken() = settings.getString(REFRESH_TOKEN_KEY, "")

  private companion object {
    const val ACCESS_TOKEN_KEY = "access_token_key"
    const val REFRESH_TOKEN_KEY = "refresh_token_key"
  }
}