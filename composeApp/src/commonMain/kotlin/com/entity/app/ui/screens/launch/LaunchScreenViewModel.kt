package com.entity.app.ui.screens.launch

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.ResponseState
import com.entity.app.data.interacotor.FeedListInteractor
import com.entity.app.data.interacotor.LaunchInteractor
import com.entity.app.ui.EntityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class LaunchScreenViewModel :
  EntityViewModel<LaunchScreenViewState, LaunchScreenEvent, LaunchScreenAction>(LaunchScreenViewState.Loading) {

  private val launchInteractor: LaunchInteractor by inject()
  private val feedListInteractor: FeedListInteractor by inject()

  init {
    coroutineScope.launch {
      combine(
        animationDelayFlow(),
        launchInteractor.getLaunchSceneResponseFlow().filter { it !is ResponseState.Loading },
        feedListInteractor.getFeedPostResponseModelsFlow(shouldLoadMore = false, shouldRefreshList = false)
          .filter { it !is ResponseState.Loading },
      ) { _, promoId, _ ->
        viewAction = if (promoId is ResponseState.Success && promoId.item.url?.isNotBlank() == true) {
          LaunchScreenAction.OpenPromoScreen(promoId.item.url)
        } else {
          LaunchScreenAction.OpenPromoScreen("64a1b6f834c1cc10c83d8b8e")
        }
      }.collect()
    }
  }

  private fun animationDelayFlow(): Flow<Unit> = flow {
    delay(ANIM_DELAY)
    emit(Unit)
  }

  private companion object {
    const val ANIM_DELAY = 1000L
  }

  override fun obtainEvent(viewEvent: LaunchScreenEvent) {}

}