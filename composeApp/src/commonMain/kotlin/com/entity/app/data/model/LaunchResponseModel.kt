package com.entity.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LaunchResponseModel(
  @SerialName("promo_scene_url")
  val url: String?,
)

