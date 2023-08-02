package com.entity.app.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import com.entity.app.ui.theme.EntityTheme
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
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

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun EntitySceneWebView(modifier: Modifier, url: String, onSceneLoaded: () -> Unit, visible: Boolean) {
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
//    webView.backgroundColor = EntityTheme.colors().bgMain.toUi UIColor
    webView
  }
  val alphaVisible: Float by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(durationMillis = 800)
  )
  UIKitView(
    factory = { webView },
    modifier = modifier,
    update = { view ->
      view.alpha = alphaVisible.toDouble()
    },
    background = EntityTheme.colors().bgMain
  )
  LaunchedEffect(Unit) {
    webView.loadRequest(NSURLRequest(NSURL(string = url)))
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