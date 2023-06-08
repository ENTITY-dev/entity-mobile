package com.entity.app.ui.tabs.user

import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.tabs.user.UserTabViewState.EMPTY
import org.koin.core.component.KoinComponent

class UserTabViewModel :
  EntityViewModel<UserTabViewState, UserTabEvent, UserTabAction>(initialState = EMPTY),
  KoinComponent {

  override fun obtainEvent(viewEvent: UserTabEvent) {
    when (viewEvent) {
      else -> {}
    }
  }
}