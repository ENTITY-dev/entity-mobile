package com.entity.app.ui.screens.profile

sealed interface ProfileScreenEvent {
  object Logout : ProfileScreenEvent
}