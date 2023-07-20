package com.entity.app.ui.screens.feed

sealed interface FeedScreenEvent {
  object RefreshFeedListScreen : FeedScreenEvent
  object LoadNewPage : FeedScreenEvent
  object ViewAppear : FeedScreenEvent

  object PullToRefresh : FeedScreenEvent
  data class OptionsClick(val model: PostUiModel) : FeedScreenEvent
  data class SceneClick(val model: PostUiModel) : FeedScreenEvent
  data class AutoPlayItem(val model: PostUiModel) : FeedScreenEvent
}