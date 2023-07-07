package com.entity.app.ui.screens.launch

sealed interface LaunchScreenAction {
  object OpenMainScreen: LaunchScreenAction
}