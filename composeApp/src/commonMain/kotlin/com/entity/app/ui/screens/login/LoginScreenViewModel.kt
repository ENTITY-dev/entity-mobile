package com.entity.app.ui.screens.login

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.interacotor.UserSettingsInteractor
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.login.LoginScreenEvent.OnLoginClick
import com.entity.app.ui.screens.login.LoginScreenEvent.OnRegistrationClick
import com.entity.app.ui.screens.login.LoginScreenEvent.PasswordChange
import com.entity.app.ui.screens.login.LoginScreenEvent.UsernameChange
import com.entity.app.ui.screens.login.LoginScreenViewState.Normal
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val LoginScreenInitialState = Normal(
  username = "", password = "", notificationText = "", isLoading = false
)

class LoginScreenViewModel :
  EntityViewModel<LoginScreenViewState, LoginScreenEvent, LoginScreenAction>(LoginScreenInitialState), KoinComponent {

  private val userSettingsInteractor: UserSettingsInteractor by inject()

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
    viewState = state.copy(isLoading = true)
    coroutineScope.launch {
      val username = state.username.trim()
      val password = state.password.trim()
      try {
        userSettingsInteractor.authUser(username, password)
        viewState = state.copy(isLoading = false)
        viewAction = LoginScreenAction.AuthSuccess
      } catch (e: Exception) {
        viewState = state.copy(isLoading = false, notificationText = e.message ?: "")
      }
    }
  }

  private fun onRegistrationClick() {
    val state = (viewState as? Normal) ?: return
    viewState = state.copy(isLoading = true)
    coroutineScope.launch {
      val username = state.username.trim()
      val password = state.password.trim()
      val name = state.username.trim()
      try {
        userSettingsInteractor.registerUser(username, password, name)
        viewState = state.copy(isLoading = false)
        viewAction = LoginScreenAction.AuthSuccess
      } catch (e: Exception) {
        viewState = state.copy(isLoading = false, notificationText = e.message ?: "")
      }
    }
  }

}