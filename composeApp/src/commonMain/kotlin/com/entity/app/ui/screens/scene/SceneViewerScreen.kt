package com.entity.app.ui.screens.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.entity.app.di.ENTITY_DEFAULT_HOST
import com.entity.app.utils.EntitySceneWebView
import compose.icons.FeatherIcons
import compose.icons.feathericons.XCircle

data class SceneViewerScreen(
  private val sceneId: String,
) : Screen {

  override val key: ScreenKey = sceneId

  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { EntitySceneViewModel() }
    val viewState by screenModel.viewStates().collectAsState()
    val viewAction by screenModel.viewActions().collectAsState(null)

    when (val state = viewState) {
      else -> {
        SceneViewer(sceneId) {
          navigator.pop()
        }
      }
    }

    LaunchedEffect(Unit) {
//      screenModel.obtainEvent()
    }
  }
}

@Composable
private fun SceneViewer(sceneId: String, onCloseClick: () -> Unit) {
  val url = "https://${ENTITY_DEFAULT_HOST}/scenes/${sceneId}/embed"
  val painter = rememberVectorPainter(FeatherIcons.XCircle)
  EntitySceneWebView(Modifier.fillMaxSize(), url)
  Box {
    Image(
      modifier =
      Modifier.clickable {
        onCloseClick.invoke()
      },
      painter = painter,
      contentDescription = ""
    )
  }
}