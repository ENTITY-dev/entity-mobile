package com.entity.app.ui.screens.login

import com.adeo.kviewmodel.BaseSharedViewModel
import com.entity.app.ui.screens.login.LoginScreenViewState.Companion
import org.koin.core.component.KoinComponent

class LoginScreenViewModel :
  BaseSharedViewModel<LoginScreenViewState, LoginScreenAction, LoginScreenEvent>(initialState = LoginScreenViewState.EMPTY),
  KoinComponent {

  override fun obtainEvent(viewEvent: LoginScreenEvent) {
    when (viewEvent) {
      else -> {}
    }
  }
}