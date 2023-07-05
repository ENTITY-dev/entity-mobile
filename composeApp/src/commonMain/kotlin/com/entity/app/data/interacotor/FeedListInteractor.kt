package com.entity.app.data.interacotor

import com.entity.app.data.repository.FeedListRepository
import com.entity.app.data.repository.FeedListRepository.FeedListResponseModel


class FeedListInteractor constructor(
  private val feedListRepository: FeedListRepository
) {

  suspend fun getFeedPostResponseModels(loadMore: Boolean): FeedListResponseModel {
    return feedListRepository.getFeedPostResponseModels(loadMore)
  }

  fun clearFeedList() {
    feedListRepository.clearCache()
  }

}