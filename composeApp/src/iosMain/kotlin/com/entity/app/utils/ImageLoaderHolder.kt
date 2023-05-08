package com.entity.app.utils

import androidx.compose.ui.unit.Density
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.ComponentRegistryBuilder
import com.seiko.imageloader.component.decoder.Decoder.Factory
import com.seiko.imageloader.component.decoder.GifDecoder
import com.seiko.imageloader.component.decoder.SkiaImageDecoder
import com.seiko.imageloader.component.decoder.SvgDecoder
import com.seiko.imageloader.component.setupCommonComponents
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.component.setupKtorComponents
import com.seiko.imageloader.intercept.InterceptorsBuilder
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

fun generateImageLoader(httpClient: HttpClient): ImageLoader {
  return ImageLoader {
    components {
      setupDefaultComponents { httpClient }
    }
    interceptor {
      useDefaultInterceptors = true
    }
  }
}
