package com.entity.app.ui.screens.feed

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.utils.DateTimeKtx
import com.entity.app.data.FeedListRepository
import com.entity.app.data.PostResponseModel
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.feed.FeedScreenAction.OpenWebViewer
import com.entity.app.ui.screens.feed.FeedScreenEvent.ViewAppear
import com.entity.app.ui.screens.feed.FeedScreenState.Result
import com.entity.app.ui.screens.feed.PostUiModel.Companion.PLACEHOLDER_ID
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject


class FeedScreenViewModel :
  EntityViewModel<FeedScreenState, FeedScreenEvent, FeedScreenAction>(initialState = FeedScreenState.LOADING) {

  private val feedListRepository: FeedListRepository by inject()

  private val timeFormatter = DateTimeKtx()

  private var updateFeedJob: Job? = null

  override fun obtainEvent(viewEvent: FeedScreenEvent) {
    when (viewEvent) {
      FeedScreenEvent.RefreshFeedListScreen -> {
        feedListRepository.clearCache()
        loadFeed()
      }

      FeedScreenEvent.LoadNewPage -> {
        increaseFeed()
      }

      ViewAppear -> {
        loadFeed()
      }

      is FeedScreenEvent.SceneClick -> {
        onSceneClick(viewEvent.model)
      }

      is FeedScreenEvent.OptionsClick -> {
        onOptionsClick(viewEvent.model)
      }
    }
  }

  private fun mapResponseToUiModels(model: PostResponseModel): PostUiModel {
    val publishDate = timeFormatter.getFormattedDate(model.createdAt)
    return PostUiModel(
      sceneId = model.id,
      sceneTitle = model.name,
      sceneSubtitle = "${model.author.name} - ${model.viewsCount} - $publishDate",
      authorImageUrl = model.author.authorImageUrl,
      scenePreviewUrl = model.scenePreviewUrl
    )
  }

  private fun onOptionsClick(model: PostUiModel) {

  }

  private fun onSceneClick(model: PostUiModel) {
    viewAction = OpenWebViewer(model.sceneId)
  }

  private fun loadFeed() {
    updateFeedJob?.cancel()
    viewState = FeedScreenState.LOADING
    updateFeedJob = coroutineScope.launch {
      do {
        viewState = try {
          val response = feedListRepository.getFeedPostResponseModels(loadMore = true)
          val uiModels = response.models.map {
            mapResponseToUiModels(it)
          }
          Result(
            models = uiModels,
            isRefreshing = false,
            showPlaceHolder = false,
            canLoadMore = response.canLoadMore
          )
        } catch (e: Exception) {
          Napier.e("Exception in FeedScreenViewModel", e)
          FeedScreenState.LOADING
        }
        delay(5000L)
      } while (viewState == FeedScreenState.LOADING)
    }
  }

  private fun increaseFeed() {
    if (updateFeedJob?.isActive == true) {
      return
    }
    val state = viewState as? Result ?: return
    val uiModelsWithPlaceholder = state.models.toMutableList()
    uiModelsWithPlaceholder.add(PostUiModel.Empty)
    viewState =
      Result(models = uiModelsWithPlaceholder, isRefreshing = true, showPlaceHolder = false, canLoadMore = false)
    coroutineScope.launch {
      do {
        viewState = try {
          val response = feedListRepository.getFeedPostResponseModels(loadMore = true)
          val uiModels = response.models.map {
            mapResponseToUiModels(it)
          }
          Result(
            models = uiModels,
            isRefreshing = false,
            showPlaceHolder = false,
            canLoadMore = response.canLoadMore
          )
        } catch (e: Exception) {
          viewState
        }
        delay(5000L)
      } while (shouldIncreaseFeed())
    }
  }

  private fun shouldIncreaseFeed(): Boolean {
    val state = viewState as? Result ?: return false
    return state.models.find { it.sceneId == PLACEHOLDER_ID } != null
  }

}