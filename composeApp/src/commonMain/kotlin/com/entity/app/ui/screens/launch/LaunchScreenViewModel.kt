package com.entity.app.ui.screens.launch

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.interacotor.FeedListInteractor
import com.entity.app.data.interacotor.LaunchInteractor
import com.entity.app.data.repository.FeedListRepository.FeedListResponseModel
import com.entity.app.ui.EntityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
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
        getPromoSceneFlow(),
        getFeedListResponseModelFlow(),
      ) { _, promoId, _ ->
        viewAction = if (promoId.isNotBlank()) {
          LaunchScreenAction.OpenPromoScreen(promoId)
        } else {
          LaunchScreenAction.OpenMainScreen
        }
      }.collect()
    }
  }

  private fun animationDelayFlow(): Flow<Unit> = flow {
    delay(ANIM_DELAY)
    emit(Unit)
  }

  private fun getPromoSceneFlow(): Flow<String> = flow {
    val id = launchInteractor.getPromoSceneId() ?: "64a1b6f834c1cc10c83d8b8e"
    emit(id)
  }

  private fun getFeedListResponseModelFlow(): Flow<FeedListResponseModel> = flow {
    emit(feedListInteractor.getFeedPostResponseModels(false))
  }

  override fun obtainEvent(viewEvent: LaunchScreenEvent) {

  }

  private companion object {
    const val ANIM_DELAY = 1000L
  }

}