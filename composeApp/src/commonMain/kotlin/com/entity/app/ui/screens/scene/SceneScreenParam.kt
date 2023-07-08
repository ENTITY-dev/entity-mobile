package com.entity.app.ui.screens.scene

import kotlinx.serialization.Serializable

@Serializable
data class SceneScreenParam(
  val sceneId: String,
  val isPromo: Boolean
)
