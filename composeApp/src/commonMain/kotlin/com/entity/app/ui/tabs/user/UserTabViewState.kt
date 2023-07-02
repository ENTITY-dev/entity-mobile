package com.entity.app.ui.tabs.user

sealed interface UserTabViewState {
  object Auth : UserTabViewState
  object NotAuth : UserTabViewState
}