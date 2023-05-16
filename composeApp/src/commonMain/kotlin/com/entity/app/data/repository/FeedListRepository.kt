package com.entity.app.data.repository

import com.entity.app.data.model.AuthorResponseModel
import com.entity.app.data.model.PostResponseModel
import com.entity.app.data.api.FeedListApi
import kotlinx.coroutines.delay
import kotlin.jvm.Volatile


class FeedListRepository constructor(
  private val feedListApi: FeedListApi
) {

  private val cachedFeedPostResponseModels: MutableList<PostResponseModel> = mutableListOf()

  @Volatile
  private var maxFeedModelsCount = 1L

  suspend fun getFeedPostResponseModels(loadMore: Boolean): FeedListResponseModel {
    val canLoadMore = cachedFeedPostResponseModels.size < maxFeedModelsCount
    if (shouldMakeRequest(loadMore, canLoadMore)) {
      val skipValue = cachedFeedPostResponseModels.size
      val response = feedListApi.getFeedPostResponseModels(skipValue)
      val feedPostResponseModels = response.items
      maxFeedModelsCount = response.maxCount
      cachedFeedPostResponseModels.addAll(feedPostResponseModels)
    }
    return FeedListResponseModel(
      models = cachedFeedPostResponseModels,
      canLoadMore = canLoadMore
    )
  }

  suspend fun getTestFeedPostResponseModels(loadMore: Boolean): FeedListResponseModel {
    val canLoadMore = cachedFeedPostResponseModels.size < maxFeedModelsCount
    if (shouldMakeRequest(loadMore, canLoadMore)) {
      delay(3000)
      val feedPostResponseModels = testList
      maxFeedModelsCount = 1000
      cachedFeedPostResponseModels.addAll(feedPostResponseModels)
    }
    return FeedListResponseModel(
      models = cachedFeedPostResponseModels.mapIndexed { index, postResponseModel ->
        postResponseModel.copy(id = index.toString())
      },
      canLoadMore = canLoadMore
    )
  }

  fun clearCache() {
    cachedFeedPostResponseModels.clear()
  }

  private fun shouldMakeRequest(loadMore: Boolean, canLoadMore: Boolean): Boolean {
    return (loadMore || cachedFeedPostResponseModels.isEmpty()) && canLoadMore
  }

  inner class FeedListResponseModel(
    val models: List<PostResponseModel>,
    val canLoadMore: Boolean,
  )

  private companion object {
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
        ),
      ),
      PostResponseModel(
        id = "3",
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
        id = "4",
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
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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
      ),
      PostResponseModel(
        id = "5",
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