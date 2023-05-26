package com.entity.app.utils

import io.sentry.kotlin.multiplatform.Context
import io.sentry.kotlin.multiplatform.OptionsConfiguration
import io.sentry.kotlin.multiplatform.Sentry
import io.sentry.kotlin.multiplatform.SentryOptions

fun initSentry(context: Context?) {
  if (context == null) {
    Sentry.init { options ->
      options.dsn = "https://2faefd1608994f438e4567e2b16ea288@o4505194261315584.ingest.sentry.io/4505205072265216"
      applyOptions(options)
    }
  } else {
    Sentry.init(context) { options ->
      options.dsn = "https://054e31db21b14152ba9b104b81bf7114@o4505194261315584.ingest.sentry.io/4505205056995328"
      applyOptions(options)
    }
  }
}

private fun applyOptions(options: SentryOptions) {
  // set general settings
}