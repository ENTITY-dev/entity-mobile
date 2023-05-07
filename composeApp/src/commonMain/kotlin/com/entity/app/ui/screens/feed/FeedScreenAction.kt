package com.entity.app.ui.screens.feed

sealed interface FeedScreenAction {
  data class OpenWebViewer(val sceneId: String) : FeedScreenAction
}