package com.entity.app.ui.screens.launch

sealed interface LaunchScreenAction {
  class OpenPromoScreen(val sceneId: String) : LaunchScreenAction
  object OpenMainScreen: LaunchScreenAction
}