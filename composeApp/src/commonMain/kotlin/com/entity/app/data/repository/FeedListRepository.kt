package com.entity.app.data.repository

import com.entity.app.data.api.FeedListApi
import com.entity.app.data.model.PostResponseModel
import kotlin.jvm.Volatile

class FeedListRepository constructor(
  private val feedListApi: FeedListApi,
) {

  private val cachedFeedPostResponseModels: MutableList<PostResponseModel> = mutableListOf()

  @Volatile
  private var maxFeedModelsCount = 1L

  suspend fun getFeedPostResponseModels(loadMore: Boolean, shouldRefreshList: Boolean): FeedListResponseModel {
    val canLoadMore = cachedFeedPostResponseModels.size < maxFeedModelsCount
    if (shouldMakeRequest(loadMore, canLoadMore, shouldRefreshList)) {
      val skipValue = if (shouldRefreshList) 0 else cachedFeedPostResponseModels.size
      val response = feedListApi.getFeedPostResponseModels(skipValue)
      val feedPostResponseModels = response.items
      maxFeedModelsCount = response.maxCount
      if (shouldRefreshList) cachedFeedPostResponseModels.clear()
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

  private fun shouldMakeRequest(loadMore: Boolean, canLoadMore: Boolean, shouldRefreshList: Boolean): Boolean {
    return ((loadMore || cachedFeedPostResponseModels.isEmpty()) && canLoadMore) || shouldRefreshList
  }

  inner class FeedListResponseModel(
    val models: List<PostResponseModel>,
    val canLoadMore: Boolean,
  )
}