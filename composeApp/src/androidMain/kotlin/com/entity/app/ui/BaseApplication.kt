package com.entity.app.ui

import android.app.Application
import com.entity.app.di.appModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
  companion object {
    lateinit var INSTANCE: BaseApplication
  }

  override fun onCreate() {
    super.onCreate()
    INSTANCE = this


    Napier.base(DebugAntilog())

    startKoin {
      androidContext(this@BaseApplication)
      androidLogger()
      modules(appModule())
    }
  }
}