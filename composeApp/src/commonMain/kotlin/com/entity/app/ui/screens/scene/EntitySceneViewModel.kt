package com.entity.app.ui.screens.scene

import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.scene.EntitySceneScreenEvent.OnSceneLoaded
import com.entity.app.ui.screens.scene.EntitySceneViewState.Visible
import org.koin.core.component.KoinComponent

class EntitySceneViewModel :
  EntityViewModel<EntitySceneViewState, EntitySceneScreenEvent, EntitySceneScreenAction>(initialState = Visible(false)),
  KoinComponent {

  override fun obtainEvent(viewEvent: EntitySceneScreenEvent) {
    when (viewEvent) {
      OnSceneLoaded -> {
        val currentState = viewStates().value as? Visible ?: return
        viewState = currentState.copy(isLoading = true)
      }

      else -> {}
    }
  }
}