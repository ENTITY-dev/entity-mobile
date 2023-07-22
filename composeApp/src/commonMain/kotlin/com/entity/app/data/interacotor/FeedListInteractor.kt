package com.entity.app.data.interacotor

import com.entity.app.data.ResponseState
import com.entity.app.data.ResponseState.Error
import com.entity.app.data.ResponseState.Success
import com.entity.app.data.provider.TextErrorProvider
import com.entity.app.data.repository.FeedListRepository
import com.entity.app.data.repository.FeedListRepository.FeedListResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class FeedListInteractor constructor(
  private val feedListRepository: FeedListRepository,
  private val textErrorProvider: TextErrorProvider,
) {

  private suspend fun getFeedPostResponseModels(
    shouldLoadMore: Boolean,
    shouldRefreshList: Boolean,
  ): ResponseState<FeedListResponseModel> = withContext(
    Dispatchers.IO
  ) {
    return@withContext try {
      Success(feedListRepository.getFeedPostResponseModels(shouldLoadMore, shouldRefreshList))
    } catch (error: Exception) {
      Error(error, textErrorProvider.getText(error))
    }
  }

  suspend fun getFeedPostResponseModelsFlow(
    shouldLoadMore: Boolean,
    shouldRefreshList: Boolean,
  ): Flow<ResponseState<FeedListResponseModel>> = flow {
    emit(ResponseState.Loading)
    emit(getFeedPostResponseModels(shouldLoadMore, shouldRefreshList))
  }

  fun clearFeedList() {
    feedListRepository.clearCache()
  }

}