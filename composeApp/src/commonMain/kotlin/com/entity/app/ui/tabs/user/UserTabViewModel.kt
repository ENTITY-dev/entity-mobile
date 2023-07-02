package com.entity.app.ui.tabs.user

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.interacotor.UserSettingsInteractor
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.tabs.user.UserTabEvent.Logout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserTabViewModel :
  EntityViewModel<UserTabViewState, UserTabEvent, UserTabAction>(initialState = UserTabViewState.NotAuth),
  KoinComponent {

  private val userSettingsInteractor: UserSettingsInteractor by inject()

  init {
    coroutineScope.launch {
      userSettingsInteractor.isUserAuthFlow().collectLatest {
        viewState = if (it) {
          UserTabViewState.Auth
        } else {
          UserTabViewState.NotAuth
        }
      }
    }
  }

  override fun obtainEvent(viewEvent: UserTabEvent) {
    when (viewEvent) {
      Logout -> {
        userSettingsInteractor.logoutUser()
      }
    }
  }
}