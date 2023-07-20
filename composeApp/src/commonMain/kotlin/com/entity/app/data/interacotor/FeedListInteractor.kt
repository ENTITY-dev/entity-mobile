package com.entity.app.data.interacotor

import com.entity.app.data.ResponseState
import com.entity.app.data.provider.TextErrorProvider
import com.entity.app.data.repository.FeedListRepository
import com.entity.app.data.repository.FeedListRepository.FeedListResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FeedListInteractor constructor(
  private val feedListRepository: FeedListRepository,
  private val textErrorProvider: TextErrorProvider
) {

  private suspend fun getFeedPostResponseModels(loadMore: Boolean): ResponseState<FeedListResponseModel> {
    return try {
      ResponseState.Success(feedListRepository.getFeedPostResponseModels(loadMore))
    } catch (error: Exception) {
      ResponseState.Error(error, textErrorProvider.getText(error))
    }
  }

  suspend fun getFeedPostResponseModelsFlow(loadMore: Boolean): Flow<ResponseState<FeedListResponseModel>> = flow {
    emit(ResponseState.Loading)
    emit(getFeedPostResponseModels(loadMore))
  }

  fun clearFeedList() {
    feedListRepository.clearCache()
  }

}