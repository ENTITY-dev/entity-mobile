package com.entity.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScenesResponseModel(
  @SerialName("items")
  val items: List<PostResponseModel>,
  @SerialName("count")
  val maxCount: Long
)

@Serializable
data class PostResponseModel(
  @SerialName("_id")
  val id: String,
  @SerialName("name")
  val name: String,
  @SerialName("status")
  val status: String,
  @SerialName("_createdAt")
  val createdAt: String,
  @SerialName("author")
  val author: AuthorResponseModel,
  @SerialName("scenePreviewUrl")
  val scenePreviewUrl: String?,
  @SerialName("viewsCount")
  val viewsCount: Long?,
)

@Serializable
data class AuthorResponseModel(
  @SerialName("_id")
  val id: String,
  @SerialName("username")
  val username: String,
  @SerialName("role")
  val role: String,
  @SerialName("name")
  val name: String,
  @SerialName("_joinedAt")
  val _joinedAt: String,
  @SerialName("_updatedAt")
  val _updatedAt: String,
  @SerialName("authorImageUrl")
  val authorImageUrl: String?,
  )