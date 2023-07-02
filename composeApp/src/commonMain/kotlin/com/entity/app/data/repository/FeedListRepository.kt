package com.entity.app.data.repository

import com.entity.app.data.api.FeedListApi
import com.entity.app.data.model.PostResponseModel
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
      val response = feedListApi.getTestFeedPostResponseModels(skipValue)
      val feedPostResponseModels = response.items
      maxFeedModelsCount = response.maxCount
      cachedFeedPostResponseModels.addAll(feedPostResponseModels)
    }
    return FeedListResponseModel(
      models = cachedFeedPostResponseModels,
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
}