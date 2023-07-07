package com.entity.app.ui.screens.profile


sealed interface ProfileScreenState {

  data class Normal(
    val isRefreshing: Boolean,
  ) : ProfileScreenState

  data class Error(
    val errorText: String,
  ) : ProfileScreenState

  object EMPTY : ProfileScreenState

  object LOADING : ProfileScreenState

}