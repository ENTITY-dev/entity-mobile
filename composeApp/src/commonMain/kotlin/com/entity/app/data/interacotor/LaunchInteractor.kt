package com.entity.app.data.interacotor

import com.entity.app.data.ResponseState
import com.entity.app.data.model.LaunchResponseModel
import com.entity.app.data.provider.TextErrorProvider
import com.entity.app.data.repository.LaunchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LaunchInteractor(
  private val launchRepository: LaunchRepository,
  private val textErrorProvider: TextErrorProvider
) {
  private suspend fun getLaunchSceneResponse(): ResponseState<LaunchResponseModel> = withContext(Dispatchers.IO) {
    return@withContext try {
      ResponseState.Success(launchRepository.getLaunchResponse())
    } catch (error: Exception) {
      ResponseState.Error(error, textErrorProvider.getText(error))
    }
  }

  suspend fun getLaunchSceneResponseFlow(): Flow<ResponseState<LaunchResponseModel>> = flow {
    emit(ResponseState.Loading)
    emit(getLaunchSceneResponse())
  }
}