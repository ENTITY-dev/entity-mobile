package com.entity.app.ui.screens.login

sealed interface LoginScreenEvent {
  object OnLoginClick : LoginScreenEvent
  object OnRegistrationClick : LoginScreenEvent

  class UsernameChange(val text: String) : LoginScreenEvent
  class PasswordChange(val text: String) : LoginScreenEvent

}