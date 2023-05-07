package com.entity.app.ui.screens.scene

sealed interface EntitySceneScreenAction {
  data class OpenWebViewer(val url: String) : EntitySceneScreenAction
}