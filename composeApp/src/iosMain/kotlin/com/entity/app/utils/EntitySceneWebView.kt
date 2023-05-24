package com.entity.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest

@Composable
actual fun EntitySceneWebView(modifier: Modifier, url: String) {
  UIKitView(
    factory = { platform.WebKit.WKWebView() },
    modifier = modifier,
    update = { view ->
      view.loadRequest(NSURLRequest(NSURL(string = url)))
    }
  )
}