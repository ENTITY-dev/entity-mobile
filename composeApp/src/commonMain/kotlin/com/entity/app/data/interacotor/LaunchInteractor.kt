package com.entity.app.data.interacotor

import com.entity.app.data.ResponseState
import com.entity.app.data.model.LaunchResponseModel
import com.entity.app.data.repository.LaunchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LaunchInteractor(
  private val launchRepository: LaunchRepository,
) {
  private suspend fun getLaunchSceneResponse(): ResponseState<LaunchResponseModel> {
    return try {
      ResponseState.Success(launchRepository.getLaunchResponse())
    } catch (error: Exception) {
      ResponseState.Error(error)
    }
  }

  suspend fun getLaunchSceneResponseFlow(): Flow<ResponseState<LaunchResponseModel>> = flow {
    emit(ResponseState.Loading)
    emit(getLaunchSceneResponse())
  }
}