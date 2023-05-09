package com.entity.app.ui.screens.feed

data class PostUiModel(
  val sceneId: String,
  val sceneTitle: String,
  val sceneSubtitle: String,
  val authorImageUrl: String?,
  val scenePreviewUrl: String?,
) {
  fun isPlaceholder() = this == Empty

  companion object {
    const val PLACEHOLDER_ID = "-1"
    val Empty = PostUiModel(PLACEHOLDER_ID, "", "", "", "")
  }
}