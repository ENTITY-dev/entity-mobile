package com.entity.app.utils

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import io.ktor.client.HttpClient
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun generateImageLoader(httpClient: HttpClient): ImageLoader {
  return ImageLoader {
    components {
      setupDefaultComponents { httpClient }
    }
    interceptor {
      memoryCacheConfig {
        // Set the max size to 25% of the app's available memory.
        maxSizePercent(0.25)
      }
      diskCacheConfig {
        directory(getCacheDir().toPath().resolve("image_cache"))
        maxSizeBytes(512L * 1024 * 1024)
      }
      useDefaultInterceptors = true
    }
  }
}
@OptIn(ExperimentalForeignApi::class)
private fun getCacheDir(): String {
  return NSFileManager.defaultManager.URLForDirectory(
    NSCachesDirectory,
    NSUserDomainMask,
    null,
    true,
    null,
  )!!.path.orEmpty()
}
