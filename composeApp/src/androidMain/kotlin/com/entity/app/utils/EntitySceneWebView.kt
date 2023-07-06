package com.entity.app.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.entity.app.ui.screens.scene.SceneWebApi
import io.github.aakira.napier.Napier

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun EntitySceneWebView(modifier: Modifier, url: String, onSceneLoaded: () -> Unit) {
  var webView by remember { mutableStateOf<WebView?>(null) }

  AndroidView(
    factory = { context ->
      WebView.setWebContentsDebuggingEnabled(true)
      WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewClient = object : WebViewClient() {
          override fun onPageFinished(view: WebView?, url: String?) {
            if (view == null) {
              return
            }
            view.visibility = VISIBLE
          }
        }
        settings.javaScriptEnabled = true
        addJavascriptInterface(SceneWebApiAndroid(context, onSceneLoaded), SceneWebApi.NAME)
        visibility = INVISIBLE
        loadUrl(url)
      }.also {
        webView = it
      }
    }
  )

  webView?.setOnTouchListener { v, event ->
    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        webView?.evaluateJavascript("window.name") {
          Napier.d("evaluateJavascript result $it")
        }
      }

      MotionEvent.ACTION_UP -> v.performClick()
      else -> {}
    }
    return@setOnTouchListener false
  }
}