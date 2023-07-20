package com.entity.app.ui.screens.feed

import cafe.adriel.voyager.core.model.coroutineScope
import com.entity.app.data.ResponseState.Error
import com.entity.app.data.ResponseState.Loading
import com.entity.app.data.ResponseState.Success
import com.entity.app.data.interacotor.FeedListInteractor
import com.entity.app.data.model.PostResponseModel
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.feed.FeedScreenAction.OpenWebViewer
import com.entity.app.ui.screens.feed.FeedScreenEvent.AutoPlayItem
import com.entity.app.ui.screens.feed.FeedScreenEvent.LoadNewPage
import com.entity.app.ui.screens.feed.FeedScreenEvent.OptionsClick
import com.entity.app.ui.screens.feed.FeedScreenEvent.RefreshFeedListScreen
import com.entity.app.ui.screens.feed.FeedScreenEvent.SceneClick
import com.entity.app.ui.screens.feed.FeedScreenEvent.ViewAppear
import com.entity.app.ui.screens.feed.FeedScreenState.Result
import com.entity.app.utils.DateTimeKtx
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject


class FeedScreenViewModel :
  EntityViewModel<FeedScreenState, FeedScreenEvent, FeedScreenAction>(initialState = FeedScreenState.Empty) {

  private val feedListInteractor: FeedListInteractor by inject()

  private val timeFormatter = DateTimeKtx()

  private var updateFeedJob: Job? = null

  init {
    loadFeedList()
  }

  override fun obtainEvent(viewEvent: FeedScreenEvent) {
    when (viewEvent) {
      RefreshFeedListScreen -> {
        loadFeedList()
      }

      LoadNewPage -> {
        increaseFeedList()
      }

      ViewAppear -> {
      }

      is SceneClick -> {
        onSceneClick(viewEvent.model)
      }

      is OptionsClick -> {
        onOptionsClick(viewEvent.model)
      }

      is AutoPlayItem -> {
        val state = viewState as? Result ?: return
        viewState = state.copy(models = state.models.map {
          it.copy(isAutoPlying = viewEvent.model.sceneId == it.sceneId)
        })
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
      scenePreviewUrl = model.scenePreviewUrl,
      isAutoPlying = false
    )
  }

  private fun onOptionsClick(model: PostUiModel) {

  }

  private fun onSceneClick(model: PostUiModel) {
    viewAction = OpenWebViewer(model.sceneId)
  }

  private fun loadFeedList() {
    updateFeedJob?.cancel()
    val prevStateIsError = viewState as? FeedScreenState.Error
    updateFeedJob = coroutineScope.launch {
      feedListInteractor.getFeedPostResponseModelsFlow(loadMore = false).collectLatest { state ->
        viewState = when (state) {
          is Error -> FeedScreenState.Error(state.message ?: "", false)

          Loading ->  {
            prevStateIsError?.copy(isRefreshing = true) ?: FeedScreenState.LoadingWithPlaceholders
          }

          is Success -> {
            val uiModels = state.item.models.map {
              mapResponseToUiModels(it)
            }
            Result(
              models = uiModels,
              isRefreshing = false,
              showPlaceHolder = false,
              canLoadMore = state.item.canLoadMore
            )
          }
        }
      }
    }
  }

  private fun increaseFeedList() {
    if (updateFeedJob?.isActive == true) {
      return
    }
    val state = viewState as? Result ?: return
    updateFeedJob = coroutineScope.launch {
      feedListInteractor.getFeedPostResponseModelsFlow(loadMore = true).collectLatest { flowState ->
        viewState = when (flowState) {
          is Error -> {
            Napier.e("Exception in FeedScreenViewModel", flowState.throwable)
            Result(
              models = state.models.filter { !it.isPlaceholder() },
              isRefreshing = false,
              showPlaceHolder = false,
              canLoadMore = true
            )
          }
          Loading -> {
            val uiModelsWithPlaceholder = state.models.toMutableList()
            uiModelsWithPlaceholder.add(PostUiModel.Empty)
            Result(
              models = uiModelsWithPlaceholder,
              isRefreshing = true,
              showPlaceHolder = false,
              canLoadMore = false
            )
          }

          is Success -> {
            val uiModels = flowState.item.models.map {
              mapResponseToUiModels(it)
            }
            Result(
              models = uiModels,
              isRefreshing = false,
              showPlaceHolder = false,
              canLoadMore = flowState.item.canLoadMore
            )
          }
        }
      }
    }
  }
}