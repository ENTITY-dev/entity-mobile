package com.entity.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.entity.app.utils.generateImageLoader
import com.seiko.imageloader.LocalImageLoader
import io.ktor.client.HttpClient
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

  private val httpClient by inject<HttpClient>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CompositionLocalProvider(
        LocalImageLoader provides generateImageLoader(context = baseContext, httpClient = { httpClient }),
      ) {
        App()
      }
    }
  }
}