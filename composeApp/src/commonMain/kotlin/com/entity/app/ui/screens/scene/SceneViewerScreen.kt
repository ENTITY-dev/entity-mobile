package com.entity.app.ui.screens.scene

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.entity.app.di.ENTITY_DEFAULT_HOST
import com.entity.app.utils.EntitySceneWebView

class SceneViewerScreen(
  private val sceneId: String
): Screen {
  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { EntitySceneViewModel() }
    val viewState by screenModel.viewStates().collectAsState()
    val viewAction by screenModel.viewActions().collectAsState(null)

    when (val state = viewState) {
      else -> {
        SceneViewer(sceneId)
      }
    }

    when (viewAction) {
      else -> {}
    }

    LaunchedEffect(Unit) {
//      screenModel.obtainEvent()
    }
  }
}

@Composable
private fun SceneViewer(sceneId: String) {
  val url = "https://${ENTITY_DEFAULT_HOST}/scenes/${sceneId}/embed"
  EntitySceneWebView(Modifier.fillMaxSize(), url)
}