package com.entity.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIScreen
import platform.WebKit.WKPreferences
import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerProtocol
import platform.WebKit.WKUserContentController
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled
import platform.darwin.NSObject

@Composable
actual fun EntitySceneWebView(modifier: Modifier, url: String, onSceneLoaded: () -> Unit) {
  val webView = remember {
    val bounds = UIScreen.mainScreen.bounds()
    val preferences = WKPreferences()
    preferences.javaScriptEnabled = true
    preferences.javaScriptCanOpenWindowsAutomatically = true
    val configuration = WKWebViewConfiguration()
    configuration.preferences = preferences
    configuration.userContentController.addScriptMessageHandler(
      scriptMessageHandler = EntityWKScriptMessageHandlerProtocol(onSceneLoaded),
      name = "iosHandler"
    )
    val webView = WKWebView(bounds, configuration)
    webView
  }
  UIKitView(
    factory = {
      webView
    },
    modifier = modifier,
    update = { view ->
      view.loadRequest(NSURLRequest(NSURL(string = url)))
    }
  )
  LaunchedEffect(Unit) {
    delay(5000)
    webView.evaluateJavaScript("window.name") { result, error ->
      Napier.d("evaluateJavaScript result ${result.toString()}")
    }
  }
}

class EntityWKScriptMessageHandlerProtocol(
  private val onSceneLoaded: () -> Unit,
) : NSObject(), WKScriptMessageHandlerProtocol {

  override fun userContentController(
    userContentController: WKUserContentController,
    didReceiveScriptMessage: WKScriptMessage,
  ) {
    onSceneLoaded.invoke()
  }
}