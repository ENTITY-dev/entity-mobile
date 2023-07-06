package com.entity.app.ui.screens.scene

sealed interface EntitySceneScreenEvent {
  object OnSceneLoaded: EntitySceneScreenEvent
}