package com.entity.app.ui.screens.scene

sealed interface EntitySceneViewState {
  data class Visible(
    val isLoading: Boolean,
  ) : EntitySceneViewState
}