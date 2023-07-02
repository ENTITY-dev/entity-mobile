package com.entity.app.data.repository

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSettingsRepository {

  private val settings = Settings()

  private val _userTokenFlow = MutableStateFlow(getAccessToken())
  val userTokenFlow = _userTokenFlow.asStateFlow()

  fun saveUserTokens(accessToken: String, refreshToken: String) {
    settings[ACCESS_TOKEN_KEY] = accessToken
    settings[REFRESH_TOKEN_KEY] = refreshToken
    _userTokenFlow.value = accessToken
  }

  fun getAccessToken() = settings.getStringOrNull(ACCESS_TOKEN_KEY)

  fun getRefreshToken() = settings.getStringOrNull(REFRESH_TOKEN_KEY)

  fun removeTokens() {
    settings[ACCESS_TOKEN_KEY] = null
    settings[REFRESH_TOKEN_KEY] = null
    _userTokenFlow.value = null
  }

  fun clearSettings() {
    settings.clear()
    _userTokenFlow.value = null
  }

  private companion object {
    const val ACCESS_TOKEN_KEY = "access_token_key"
    const val REFRESH_TOKEN_KEY = "refresh_token_key"
  }
}