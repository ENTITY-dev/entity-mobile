package com.entity.app.ui.tabs.feed

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.entity.app.ui.screens.feed.FeedScreen
import com.entity.app.ui.screens.scene.SceneViewerScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.List

internal class FeedTab(
  private val hideBottomNavigationCallback: (Boolean) -> Unit,
) : Tab {

  override val options: TabOptions
    @Composable
    get() {
      val title = "Feed"
      val icon = rememberVectorPainter(FeatherIcons.List)
      return remember {
        TabOptions(
          index = 0u,
          title = title,
          icon = icon
        )
      }
    }

  @OptIn(ExperimentalAnimationApi::class)
  @Composable
  override fun Content() {
    Navigator(FeedScreen) { navigator ->
      hideBottomNavigationCallback.invoke(navigator.lastItem !is SceneViewerScreen)
      SlideTransition(
        navigator,
        animationSpec = spring(
          stiffness = 500f,
          visibilityThreshold = IntOffset.VisibilityThreshold
        )
      )
    }
  }
}
