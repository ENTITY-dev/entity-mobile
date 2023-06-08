package com.entity.app.ui.tabs.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.entity.app.ui.screens.feed.FeedScreen
import com.entity.app.ui.screens.scene.SceneViewerScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.List

internal data class FeedTab(
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

  @Composable
  override fun Content() {
    Navigator(FeedScreen) {
      hideBottomNavigationCallback.invoke(it.lastItem !is SceneViewerScreen)
      CurrentScreen()
    }
  }
}
