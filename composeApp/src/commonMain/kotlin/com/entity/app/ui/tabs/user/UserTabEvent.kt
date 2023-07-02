package com.entity.app.ui.tabs.user

sealed interface UserTabEvent {
  object Logout : UserTabEvent
}