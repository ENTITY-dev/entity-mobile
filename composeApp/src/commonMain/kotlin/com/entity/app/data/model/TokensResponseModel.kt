package com.entity.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensResponseModel(
  @SerialName("tokens")
  val tokens: Tokens
)

@Serializable
data class Tokens(
  @SerialName("accessToken")
  val accessToken: String,
  @SerialName("refreshToken")
  val refreshToken: String,
)
