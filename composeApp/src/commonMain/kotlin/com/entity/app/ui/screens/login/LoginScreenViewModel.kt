package com.entity.app.ui.screens.login

import com.entity.app.ui.EntityViewModel
import org.koin.core.component.KoinComponent

class LoginScreenViewModel :
  EntityViewModel<LoginScreenViewState, LoginScreenEvent, LoginScreenAction>(initialState = LoginScreenViewState.EMPTY),
  KoinComponent {

  override fun obtainEvent(viewEvent: LoginScreenEvent) {
    when (viewEvent) {
      else -> {}
    }
  }
}