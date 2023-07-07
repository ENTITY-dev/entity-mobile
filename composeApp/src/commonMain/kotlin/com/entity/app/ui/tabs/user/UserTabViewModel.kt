package com.entity.app.ui.tabs.user

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.interacotor.UserSettingsInteractor
import com.entity.app.ui.EntityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class UserTabViewModel :
  EntityViewModel<UserTabViewState, UserTabEvent, UserTabAction>(
    initialState = UserTabViewState.NotAuth
  ) {

  private val userSettingsInteractor: UserSettingsInteractor by inject()

  init {
    setAuthState(userSettingsInteractor.isUserAuth())
    coroutineScope.launch {
      userSettingsInteractor.isUserAuthFlow().collectLatest(::setAuthState)
    }
  }

  private fun setAuthState(isUserAuth: Boolean) {
    viewState = if (isUserAuth) {
      UserTabViewState.Auth
    } else {
      UserTabViewState.NotAuth
    }
  }


  override fun obtainEvent(viewEvent: UserTabEvent) {
    when (viewEvent) {
      else -> {}
    }
  }
}