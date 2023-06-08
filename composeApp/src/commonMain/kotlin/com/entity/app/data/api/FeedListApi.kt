package com.entity.app.data.api

import com.entity.app.data.model.AuthorResponseModel
import com.entity.app.data.model.PostResponseModel
import com.entity.app.data.model.ScenesResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.delay

class FeedListApi constructor(
  private val client: HttpClient,
) {
  suspend fun getFeedPostResponseModels(skipValue: Int): ScenesResponseModel {
    val response: ScenesResponseModel = client.get {
      url(URL_SCENES)
      headers { }
      formData {
        parameter(LIMIT_PARAM, LIMIT_VALUE)
        parameter(SKIP_PARAM, skipValue)
      }
    }.body()
    return response
  }

  suspend fun getTestFeedPostResponseModels(skipValue: Int): ScenesResponseModel {
    delay(3000)
    return ScenesResponseModel(
      testList,
      testList.size.toLong(),
    )
  }

  private companion object {
    const val URL_SCENES = "/backend/scenes"
    const val LIMIT_PARAM = "limit"
    const val LIMIT_VALUE = 10
    const val SKIP_PARAM = "skip"
    val testList = listOf(
      PostResponseModel(
        id = "1",
        name = "Magische",
        status = "",
        scenePreviewUrl = "https://i.pinimg.com/originals/c1/5b/eb/c15beb5770999f05679d4133f408c462.jpg",
        viewsCount = 300,
        createdAt = "2022-06-17T12:34:56+00:00",
        authorId = "",
        author = AuthorResponseModel(
          id = "",
          name = "andreyb0y",
          username = "",
          role = "",
          _joinedAt = "",
          _updatedAt = "",
          authorImageUrl = "https://mir-s3-cdn-cf.behance.net/project_modules/2800_opt_1/d07bca98931623.5ee79b6a8fa55.jpg"
        ),
      ),
      PostResponseModel(
        id = "2",
        name = "Magische",
        status = "",
        scenePreviewUrl = "https://i.pinimg.com/originals/c1/5b/eb/c15beb5770999f05679d4133f408c462.jpg",
        viewsCount = 300,
        createdAt = "2022-06-17T12:34:56+00:00",
        authorId = "",
        author = AuthorResponseModel(
          id = "",
          name = "andreyb0y",
          username = "",
          role = "",
          _joinedAt = "",
          _updatedAt = "",
          authorImageUrl = "https://mir-s3-cdn-cf.behance.net/project_modules/2800_opt_1/d07bca98931623.5ee79b6a8fa55.jpg"
        )
      )
    )
  }

}