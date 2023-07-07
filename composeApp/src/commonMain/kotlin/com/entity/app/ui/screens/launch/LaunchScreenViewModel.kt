package com.entity.app.ui.screens.launch

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.interacotor.LaunchInteractor
import com.entity.app.ui.EntityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class LaunchScreenViewModel :
  EntityViewModel<LaunchScreenViewState, LaunchScreenEvent, LaunchScreenAction>(LaunchScreenViewState.Loading) {


  private val launchInteractor: LaunchInteractor by inject()

  init {
    coroutineScope.launch {
      animationDelayFlow().zip(getPromoSceneFlow()) { _, promo ->
        if (promo.isNotBlank()) {
        } else {
          viewAction = LaunchScreenAction.OpenMainScreen
        }
      }.collect()
    }
  }

  private fun animationDelayFlow(): Flow<Unit> = flow {
    delay(3000)
    emit(Unit)
  }

  private fun getPromoSceneFlow(): Flow<String> = flow {
    val id = launchInteractor.getPromoSceneId() ?: ""
    emit(id)
  }

  override fun obtainEvent(viewEvent: LaunchScreenEvent) {

  }

}