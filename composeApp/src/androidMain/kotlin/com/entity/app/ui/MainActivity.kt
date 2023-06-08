package com.entity.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.entity.app.utils.generateImageLoader
import com.entity.app.utils.initSentry
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.seiko.imageloader.LocalImageLoader
import io.ktor.client.HttpClient
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

  private val httpClient by inject<HttpClient>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initSentry(this)
    setContent {
      val systemUiController = rememberSystemUiController()
      SideEffect {
        systemUiController.apply {
          setSystemBarsColor(
            color = Color.Black,
            darkIcons = false,
          )
          isNavigationBarContrastEnforced = true
        }
      }
      CompositionLocalProvider(
        LocalImageLoader provides generateImageLoader(context = baseContext, httpClient = { httpClient }),
      ) {
        App()
      }
    }
  }
}