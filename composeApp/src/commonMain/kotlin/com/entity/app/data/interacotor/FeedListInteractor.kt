package com.entity.app.data.interacotor

import com.entity.app.data.ResponseState
import com.entity.app.data.repository.FeedListRepository
import com.entity.app.data.repository.FeedListRepository.FeedListResponseModel
import kotlinx.coroutines.flow.Flow


class FeedListInteractor constructor(
  private val feedListRepository: FeedListRepository,
) {

  suspend fun getFeedPostResponseModels(loadMore: Boolean): FeedListResponseModel {
    return feedListRepository.getFeedPostResponseModels(loadMore)
  }

  suspend fun getFeedPostResponseModelsFlow(loadMore: Boolean): Flow<ResponseState<FeedListResponseModel>> =
    feedListRepository.getFeedPostResponseModelsFlow(loadMore)


  fun clearFeedList() {
    feedListRepository.clearCache()
  }

}