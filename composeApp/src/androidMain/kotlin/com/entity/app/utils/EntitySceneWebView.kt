package com.entity.app.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import com.entity.app.ui.screens.scene.SceneWebApi

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun EntitySceneWebView(modifier: Modifier, url: String) {
  AndroidView(factory = {
    WebView.setWebContentsDebuggingEnabled(true)
    val webView = WebView(it).apply {
      layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
      )
      webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
          if (view == null || view.isVisible) {
            return
          }
          view.visibility = VISIBLE
          view.alpha = 0f
          view.animate()
            .alpha(1f)
            .duration = 350
        }
      }
      settings.javaScriptEnabled = true
      addJavascriptInterface(SceneWebApiAndroid(context), SceneWebApi.NAME)
      visibility = INVISIBLE
      loadUrl(url)
    }
//    webView.setOnTouchListener { v, event ->
//      when (event.action) {
//        MotionEvent.ACTION_DOWN -> {
//          webView.evaluateJavascript("window.postMessage({event: \"autoPlay\"})", null)
//        }
//
//        MotionEvent.ACTION_UP -> v.performClick()
//        else -> {}
//      }
//      return@setOnTouchListener false
//    }
    webView
  })
}