package com.entity.app.ui.screens.login

sealed interface LoginScreenAction {
  object AuthSuccess: LoginScreenAction
}