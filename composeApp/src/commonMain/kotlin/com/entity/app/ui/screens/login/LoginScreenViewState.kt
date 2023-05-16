package com.entity.app.ui.screens.login

sealed interface LoginScreenViewState {

  data class Normal(
    val username: String,
    val password: String,
    val notificationText: String,
    val isLoading: Boolean,
  ): LoginScreenViewState


}