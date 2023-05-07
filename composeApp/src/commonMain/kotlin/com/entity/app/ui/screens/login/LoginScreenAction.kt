package com.entity.app.ui.screens.login

sealed interface LoginScreenAction {
  data class OpenWebViewer(val url: String) : LoginScreenAction
}