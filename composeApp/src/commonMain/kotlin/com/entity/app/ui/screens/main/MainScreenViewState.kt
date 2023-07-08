package com.entity.app.ui.screens.main

sealed interface MainScreenViewState {

  data class Normal(
    val bottomBarVisible: Boolean
  ) : MainScreenViewState

}