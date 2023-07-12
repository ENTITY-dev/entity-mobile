package com.entity.app.data.interacotor

import com.entity.app.data.ResponseState
import com.entity.app.data.api.UserSettingsApi
import com.entity.app.data.repository.UserSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

class UserSettingsInteractor constructor(
  private val api: UserSettingsApi,
  private val repository: UserSettingsRepository,
) {
  @OptIn(ExperimentalCoroutinesApi::class)
  fun isUserAuthFlow(): Flow<Boolean> {
    return repository.userTokenFlow.mapLatest { it.accessToken.isNotBlank() }
  }

  fun isUserAuth(): Boolean {
    return repository.userTokenFlow.value.accessToken.isNotBlank()
  }

  private suspend fun authUser(userName: String, password: String): ResponseState<Unit> {
    return try {
      api.postAuthUser(userName, password)
      ResponseState.Success(Unit)
    } catch (error: Exception) {
      ResponseState.Error(error)
    }
  }

  suspend fun authUserFlow(userName: String, password: String): Flow<ResponseState<Unit>>  = flow {
    emit(ResponseState.Loading)
    emit(authUser(userName, password))
  }

  private suspend fun registerUser(userName: String, password: String, name: String): ResponseState<Unit> {
    return try {
      api.postRegisterUser(userName, password, name)
      ResponseState.Success(Unit)
    } catch (error: Exception) {
      ResponseState.Error(error)
    }
  }

  suspend fun registerUserFlow(userName: String, password: String, name: String): Flow<ResponseState<Unit>> = flow {
    emit(ResponseState.Loading)
    emit(registerUser(userName, password, name))
  }

  fun logoutUser() {
    repository.removeTokens()
    api.removeUserToken()
  }

}