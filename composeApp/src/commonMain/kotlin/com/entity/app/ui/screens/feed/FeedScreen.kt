package com.entity.app.ui.screens.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.entity.app.ui.Navigation
import com.entity.app.ui.screens.feed.FeedScreenAction.OpenWebViewer

import com.entity.app.ui.screens.feed.FeedScreenEvent.ViewAppear
import com.entity.app.ui.screens.feed.FeedScreenState.EMPTY
import com.entity.app.ui.screens.feed.FeedScreenState.LOADING
import com.entity.app.ui.screens.feed.FeedScreenState.Result
import kotlinx.coroutines.flow.collect

class FeedScreen : Screen {

  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { FeedScreenViewModel() }
    val viewState by screenModel.viewStates().collectAsState()
    val viewAction by screenModel.viewActions().collectAsState(null)

    when (viewState) {
      EMPTY -> {

      }
      LOADING -> {

      }
      is Result -> {

      }
    }

//    if (!viewState.showPlaceHolder) {
//      FeedListWithPost(
//        feedList = viewState.models,
//        canLoadMore = viewState.canLoadMore,
//        loadMore = { viewModel.obtainEvent(LoadNewPage) },
//        onSceneClick = { viewModel.obtainEvent(SceneClick(it)) },
//        onOptionsClick = { viewModel.obtainEvent(OptionsClick(it)) }
//      )
//    } else {
//      FeedListWithPlaceholders(count = viewState.models.size)
//    }

    when (viewAction) {
//      is OpenWebViewer -> navigator.push()
      else -> {}
    }

    LaunchedEffect(key1 = Unit) {
      screenModel.obtainEvent(ViewAppear)
    }
  }
}