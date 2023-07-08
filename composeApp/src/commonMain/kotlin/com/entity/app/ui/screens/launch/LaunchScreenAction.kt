package com.entity.app.ui.screens.launch

sealed interface LaunchScreenAction {
  class OpenPromoScreen(val promo: String) : LaunchScreenAction
  object OpenMainScreen: LaunchScreenAction
}