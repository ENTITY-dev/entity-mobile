package com.entity.app.data.provider

import com.entity.app.EntityRes
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.utils.io.errors.IOException

class TextErrorProvider {

  fun getText(throwable: Throwable): String {
    return when (throwable) {
      is IOException -> getIOErrorTextMessage(throwable)
      else -> {
        EntityRes.string.error_unknown
      }
    }
  }

  private fun getIOErrorTextMessage(throwable: Throwable): String {
    return when (throwable) {
      is HttpRequestTimeoutException -> {
        EntityRes.string.error_timeout_text
      }

      else -> {
        EntityRes.string.error_unknown
      }
    }
  }
}