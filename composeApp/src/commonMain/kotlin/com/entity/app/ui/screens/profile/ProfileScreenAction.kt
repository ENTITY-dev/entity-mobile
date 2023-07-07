package com.entity.app.ui.screens.profile

sealed interface ProfileScreenAction {
  data class OpenWebViewer(val sceneId: String) : ProfileScreenAction
}