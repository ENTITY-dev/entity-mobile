package com.entity.app.ui.screens.feed


data class FeedScreenViewState(
  val models: List<PostUiModel>,
  val isRefreshing: Boolean,
  val showPlaceHolder: Boolean,
  val canLoadMore: Boolean
) {

  companion object {
    val EMPTY = FeedScreenViewState(
      models = emptyList(),
      isRefreshing = false,
      showPlaceHolder = false,
      canLoadMore = false
    )
    val PLACEHOLDERS = FeedScreenViewState(
      models = listOf(PostUiModel.Empty, PostUiModel.Empty, PostUiModel.Empty),
      isRefreshing = false,
      showPlaceHolder = true,
      canLoadMore = false
    )
  }

}