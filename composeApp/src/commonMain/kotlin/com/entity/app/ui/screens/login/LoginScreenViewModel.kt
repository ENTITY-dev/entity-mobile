package com.entity.app.ui.screens.login

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.login.LoginScreenEvent.OnLoginClick
import com.entity.app.ui.screens.login.LoginScreenEvent.OnRegistrationClick
import com.entity.app.ui.screens.login.LoginScreenEvent.PasswordChange
import com.entity.app.ui.screens.login.LoginScreenEvent.UsernameChange
import com.entity.app.ui.screens.login.LoginScreenViewState.Normal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LoginScreenViewModel :
  EntityViewModel<LoginScreenViewState, LoginScreenEvent, LoginScreenAction>(
    initialState = Normal(
      username = "",
      password = "",
      notificationText = "",
      isLoading = false
    )
  ),
  KoinComponent {

  override fun obtainEvent(viewEvent: LoginScreenEvent) {
    when (viewEvent) {
      is UsernameChange -> {
        val state = (viewState as? Normal) ?: return
        viewState = state.copy(username = viewEvent.text)
      }

      is PasswordChange -> {
        val state = (viewState as? Normal) ?: return
        viewState = state.copy(password = viewEvent.text)
      }

      OnLoginClick -> {
        onLoginClick()
      }

      OnRegistrationClick -> {
        onRegistrationClick()
      }
    }
  }

  private fun onLoginClick() {
    val state = (viewState as? Normal) ?: return

  }

  private fun onRegistrationClick() {
    val state = (viewState as? Normal) ?: return
    viewState = state.copy(notificationText = "dfsdfsdf", isLoading = true)
    coroutineScope.launch {
      delay(4000)
      viewState = state.copy(notificationText = "123", isLoading = false)

    }
  }

}