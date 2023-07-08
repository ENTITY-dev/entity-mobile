package com.entity.app.ui.screens.scene

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import kotlinx.serialization.Serializable

@Serializable
data class SceneScreenParam(
  val sceneId: String,
  val isPromo: Boolean
) : JavaSerializable
