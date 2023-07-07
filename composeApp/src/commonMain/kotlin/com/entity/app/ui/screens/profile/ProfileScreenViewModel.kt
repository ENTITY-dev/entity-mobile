package com.entity.app.ui.screens.profile

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.interacotor.UserInfoInteractor
import com.entity.app.data.interacotor.UserSettingsInteractor
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.profile.ProfileScreenEvent.Logout
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class ProfileScreenViewModel : EntityViewModel<ProfileScreenState, ProfileScreenEvent, ProfileScreenAction>(
  initialState = ProfileScreenState.Normal(
    isRefreshing = false
  )
) {

  private val userInfoInteractor: UserInfoInteractor by inject()
  private val userSetInteractor: UserSettingsInteractor by inject()

  init {
    coroutineScope.launch {
      userInfoInteractor.getUserInfo()
    }
  }


  override fun obtainEvent(viewEvent: ProfileScreenEvent) {
    when (val event = viewEvent) {
      Logout -> {
        userSetInteractor.logoutUser()
      }
    }
  }
}