package com.entity.app.utils

import android.content.Context
import android.webkit.JavascriptInterface
import com.entity.app.ui.screens.scene.SceneWebApi

class SceneWebApiAndroid(context: Context) : SceneWebApi {
  @JavascriptInterface
  override fun onSceneLoaded() {

  }
}