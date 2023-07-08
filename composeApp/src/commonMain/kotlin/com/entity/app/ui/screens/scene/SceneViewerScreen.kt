package com.entity.app.ui.screens.scene

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.entity.app.ui.components.EntityCircularProgressIndicator
import com.entity.app.ui.screens.main.MainScreen
import com.entity.app.ui.screens.scene.EntitySceneScreenEvent.OnSceneLoaded
import com.entity.app.ui.screens.scene.EntitySceneViewState.Visible
import com.entity.app.ui.theme.EntityTheme
import com.entity.app.utils.ENTITY_DEFAULT_HOST
import com.entity.app.utils.EntitySceneWebView
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

data class SceneViewerScreen(
  private val param: SceneScreenParam
) : Screen {

  override val key: ScreenKey = param.sceneId

  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { EntitySceneViewModel() }
    val viewState by screenModel.viewStates().collectAsState()

    when (val state = viewState) {
      is Visible -> {
        SceneViewer(
          sceneId = param.sceneId,
          onCloseClick = {
            if (param.isPromo) {
              navigator.popUntilRoot()
              navigator.replace(MainScreen)
            } else {
              navigator.pop()
            }
          },
          onSceneLoaded = {
            screenModel.obtainEvent(OnSceneLoaded)
          },
          visible = state.isLoading
        )
      }
    }
  }
}

@Composable
private fun SceneViewer(
  sceneId: String,
  onCloseClick: () -> Unit,
  onSceneLoaded: () -> Unit,
  visible: Boolean,
) {
  val url = remember { "https://${ENTITY_DEFAULT_HOST}/scenes/${sceneId}/embed" }
  val painter = rememberVectorPainter(FeatherIcons.ArrowLeft)
  val visibleAnimTimeMs = 800
  Box(modifier = Modifier.fillMaxSize()) {
    EntitySceneWebView(
      modifier = Modifier
        .fillMaxSize().background(EntityTheme.colors().bgMain),
      url = url,
      onSceneLoaded = onSceneLoaded,
      visible = visible
    )
  }
  Box {
    Image(
      modifier = Modifier
        .size(36.dp)
        .padding(start = 8.dp, top = 8.dp)
        .clickable {
          onCloseClick.invoke()
        },
      painter = painter,
      contentDescription = "",
      colorFilter = ColorFilter.tint(Color.White)
    )
  }
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    AnimatedVisibility(
      visible = !visible,
      enter = fadeIn(animationSpec = tween(visibleAnimTimeMs)),
      exit = fadeOut(animationSpec = tween(visibleAnimTimeMs)),
    ) {
      EntityCircularProgressIndicator(modifier = Modifier.size(68.dp))
    }
  }

}