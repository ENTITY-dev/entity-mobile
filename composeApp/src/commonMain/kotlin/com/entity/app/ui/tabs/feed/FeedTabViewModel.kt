package com.entity.app.ui.tabs.feed

import com.entity.app.data.repository.BottomBarNavigationStateRepository
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.scene.SceneViewerScreen
import com.entity.app.ui.tabs.feed.FeedTabEvent.ScreenChange
import org.koin.core.component.inject

class FeedTabViewModel : EntityViewModel<FeedTabViewState, FeedTabEvent, FeedTabAction>(
  initialState = FeedTabViewState.Normal
) {

  private val bottomBarNavigationStateRepository: BottomBarNavigationStateRepository by inject()

  override fun obtainEvent(viewEvent: FeedTabEvent) {
    when (viewEvent) {
      is ScreenChange -> {
        bottomBarNavigationStateRepository.setVisible(viewEvent.lastItem !is SceneViewerScreen)
      }
    }
  }
}