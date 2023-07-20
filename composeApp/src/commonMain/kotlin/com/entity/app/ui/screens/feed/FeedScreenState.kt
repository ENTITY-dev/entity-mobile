package com.entity.app.ui.screens.feed


sealed interface FeedScreenState {

  data class Result(
    val models: List<PostUiModel>,
    val isRefreshing: Boolean,
    val showPlaceHolder: Boolean,
    val canLoadMore: Boolean,
  ) : FeedScreenState

  data class Error(
    val errorText: String,
    val isRefreshing: Boolean
  ) : FeedScreenState

  object Empty : FeedScreenState

  object LoadingWithPlaceholders : FeedScreenState

}