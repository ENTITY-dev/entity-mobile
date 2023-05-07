package com.entity.app.ui.screens.feed

import com.adeo.kviewmodel.BaseSharedViewModel
import com.entity.app.utils.DateTimeKtx
import com.entity.app.data.FeedListRepository
import com.entity.app.data.PostResponseModel
import com.entity.app.ui.screens.feed.FeedScreenAction.OpenWebViewer
import com.entity.app.ui.screens.feed.FeedScreenEvent.ViewAppear
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class FeedScreenViewModel :
  BaseSharedViewModel<FeedScreenViewState, FeedScreenAction, FeedScreenEvent>(initialState = FeedScreenViewState.PLACEHOLDERS),
  KoinComponent {

  private val feedListRepository: FeedListRepository by inject()

  private val timeFormatter = DateTimeKtx()

  override fun obtainEvent(viewEvent: FeedScreenEvent) {
    when (viewEvent) {
      FeedScreenEvent.RefreshFeedListScreen -> {
        updateFeed(clearCache = true, loadMore = false)
      }
      FeedScreenEvent.LoadNewPage -> {
        if (viewState.isRefreshing) {
          return
        }
        updateFeed(clearCache = false, loadMore = true)
      }
      ViewAppear -> {
        updateFeed(clearCache = false, loadMore = false)
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

  private var updateFeedJob: Job? = null

  private fun updateFeed(clearCache: Boolean, loadMore: Boolean) {
    updateFeedJob?.cancel()
    updateFeedJob = viewModelScope.launch {
      if (clearCache || viewState.models.isEmpty()) {
        viewState = FeedScreenViewState.PLACEHOLDERS
      }
      if (loadMore) {
        val uiModels = viewState.models.toMutableList()
        uiModels.add(PostUiModel.Empty)
        viewState = FeedScreenViewState(models = uiModels, isRefreshing = true, showPlaceHolder = false, canLoadMore = false)
      }
      do {
        viewState = try {
          val response = feedListRepository.getFeedPostResponseModels(clearCache = clearCache, loadMore = loadMore)
          val uiModels = response.models.map {
            mapResponseToUiModels(it)
          }
          FeedScreenViewState(models = uiModels, isRefreshing = false, showPlaceHolder = false, canLoadMore = response.canLoadMore)
        } catch (e: Exception) {
          if (clearCache) {
            FeedScreenViewState.PLACEHOLDERS
          } else {
            viewState
          }
        }
        delay(5000L)
      } while (viewState == FeedScreenViewState.PLACEHOLDERS)
    }
  }
}