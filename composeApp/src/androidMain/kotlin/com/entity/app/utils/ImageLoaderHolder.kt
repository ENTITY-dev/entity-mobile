package com.entity.app.utils

import android.content.Context
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupCommonComponents
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.component.setupKtorComponents
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import okio.Path.Companion.toOkioPath


fun generateImageLoader(context: Context, httpClient: () -> HttpClient): ImageLoader {
  return ImageLoader(requestCoroutineContext = Dispatchers.IO) {
    components {
      setupDefaultComponents(context = context, httpClient = httpClient)
    }
    interceptor {
      memoryCacheConfig {
        // Set the max size to 25% of the app's available memory.
        maxSizePercent(context, 0.25)
      }
      diskCacheConfig {
        directory(context.cacheDir.resolve("image_cache").toOkioPath())
        maxSizeBytes(512L * 1024 * 1024) // 512MB
      }
      useDefaultInterceptors = true
    }

  }
}
