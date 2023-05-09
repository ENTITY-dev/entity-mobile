package com.entity.app.ui.screens.scene

import com.entity.app.ui.EntityViewModel
import org.koin.core.component.KoinComponent

class EntitySceneViewModel :
  EntityViewModel<EntitySceneViewState, EntitySceneScreenEvent, EntitySceneScreenAction>(initialState = EntitySceneViewState.EMPTY),
  KoinComponent {

  override fun obtainEvent(viewEvent: EntitySceneScreenEvent) {
    when (viewEvent) {
      else -> {}
    }
  }
}