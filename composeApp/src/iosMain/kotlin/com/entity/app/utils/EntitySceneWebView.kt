package com.entity.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import co.touchlab.stately.freeze
import kotlinx.cinterop.CValue
import kotlinx.cinterop.CValues
import platform.CoreGraphics.CGRect
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIScreen
import platform.WebKit.WKPreferences
import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerWithReplyProtocol
import platform.WebKit.WKUserContentController
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled
import platform.darwin.NSObject

@Composable
actual fun EntitySceneWebView(modifier: Modifier, url: String) {
  val webView = remember {
    val bounds = UIScreen.mainScreen.bounds()
    val preferences = WKPreferences()
    preferences.javaScriptEnabled = true
    preferences.javaScriptCanOpenWindowsAutomatically = true
    val configuration = WKWebViewConfiguration()
    configuration.preferences = preferences
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
}

class WKWebViewWrapper(
  private val webView : WKWebView
) : NSObject(), WKScriptMessageHandlerWithReplyProtocol{

  override fun userContentController(
    userContentController: WKUserContentController,
    didReceiveScriptMessage: WKScriptMessage,
    replyHandler: (Any?, String?) -> Unit,
  ) {

  }
}