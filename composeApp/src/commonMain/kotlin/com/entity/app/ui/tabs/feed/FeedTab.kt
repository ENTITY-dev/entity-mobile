package com.entity.app.ui.tabs.feed

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.entity.app.EntityRes
import com.entity.app.ui.screens.feed.FeedScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.List

internal object FeedTab : Tab {

  override val options: TabOptions
    @Composable
    get() {
      val title = EntityRes.string.feed_tab
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
    val screenModel = rememberScreenModel { FeedTabViewModel() }

    Navigator(FeedScreen) { navigator ->
      LaunchedEffect(navigator.lastItem.key) {
        screenModel.obtainEvent(FeedTabEvent.ScreenChange(navigator.lastItem))
      }
      SlideTransition(
        navigator,
        animationSpec = tween(durationMillis = 300)
      )
    }
  }
}
