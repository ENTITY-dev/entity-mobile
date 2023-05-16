package com.entity.app.data.repository

import com.entity.app.data.model.Tokens
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import io.github.xxfast.kstore.KStore

class UserSettingsRepository {

  private val settings = Settings()

  fun saveUserTokens(accessToken: String, refreshToken: String) {
    settings[ACCESS_TOKEN_KEY] = accessToken
    settings[REFRESH_TOKEN_KEY] = refreshToken
  }

  fun getAccessToken() = settings.getStringOrNull(ACCESS_TOKEN_KEY)

  fun getRefreshToken() = settings.getStringOrNull(REFRESH_TOKEN_KEY)

  fun removeTokens() {
    settings[ACCESS_TOKEN_KEY] = null
    settings[REFRESH_TOKEN_KEY] = null
  }

  fun clearSettings() {
    settings.clear()
  }

  private companion object {
    const val ACCESS_TOKEN_KEY = "access_token_key"
    const val REFRESH_TOKEN_KEY = "refresh_token_key"
  }
}