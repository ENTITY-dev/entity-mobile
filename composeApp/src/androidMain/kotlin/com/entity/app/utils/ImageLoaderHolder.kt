package com.entity.app.utils

import android.content.Context
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupCommonComponents
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.component.setupKtorComponents
import io.ktor.client.HttpClient


fun generateImageLoader(context: Context, httpClient: () -> HttpClient): ImageLoader {
  return ImageLoader {
    components {
      setupDefaultComponents(context = context, httpClient = httpClient)
    }
    interceptor {
      useDefaultInterceptors = true
    }
  }
}
