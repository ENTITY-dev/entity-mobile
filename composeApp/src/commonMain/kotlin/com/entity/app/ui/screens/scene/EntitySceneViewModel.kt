package com.entity.app.ui.screens.scene

import com.adeo.kviewmodel.BaseSharedViewModel
import com.entity.app.ui.screens.scene.EntitySceneViewState.Companion
import org.koin.core.component.KoinComponent

class EntitySceneViewModel :
  BaseSharedViewModel<EntitySceneViewState, EntitySceneScreenEvent, EntitySceneScreenEvent>(initialState = EntitySceneViewState.EMPTY),
  KoinComponent {

  override fun obtainEvent(viewEvent: EntitySceneScreenEvent) {
    when (viewEvent) {
      else -> {}
    }
  }
}