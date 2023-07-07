package com.entity.app.ui.screens.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.entity.app.ui.EntityButtonComponent
import com.entity.app.ui.screens.feed.FeedScreenAction.OpenWebViewer
import com.entity.app.ui.screens.feed.FeedScreenEvent.LoadNewPage
import com.entity.app.ui.screens.feed.FeedScreenEvent.OptionsClick
import com.entity.app.ui.screens.feed.FeedScreenEvent.RefreshFeedListScreen
import com.entity.app.ui.screens.feed.FeedScreenEvent.SceneClick
import com.entity.app.ui.screens.feed.FeedScreenState.EMPTY
import com.entity.app.ui.screens.feed.FeedScreenState.Error
import com.entity.app.ui.screens.feed.FeedScreenState.LOADING
import com.entity.app.ui.screens.feed.FeedScreenState.Result
import com.entity.app.ui.screens.scene.SceneViewerScreen
import com.entity.app.ui.theme.EntityTheme
import kotlinx.coroutines.launch

object FeedScreen : Screen {

  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { FeedScreenViewModel() }
    val viewState by screenModel.viewStates().collectAsState()

    when (val state = viewState) {
      EMPTY -> {
        SafeScreen("Your feed is empty.\n Please refresh your list.", isError = false) {
          screenModel.obtainEvent(
            RefreshFeedListScreen
          )
        }
      }

      LOADING -> {
        FeedListWithPlaceholders(count = 3)
      }

      is Result -> {
        FeedListWithPost(
          feedList = state.models,
          canLoadMore = state.canLoadMore,
          loadMore = { screenModel.obtainEvent(LoadNewPage) },
          onSceneClick = { screenModel.obtainEvent(SceneClick(it)) },
          onOptionsClick = { screenModel.obtainEvent(OptionsClick(it)) }
        )
      }

      is Error -> {
        SafeScreen(state.errorText, isError = true) { screenModel.obtainEvent(RefreshFeedListScreen) }
      }
    }

    LaunchedEffect(Unit) {
      launch {
        screenModel.viewActions().collect { action ->
          when (action) {
            is OpenWebViewer -> {
              navigator.push(SceneViewerScreen(action.sceneId))
            }

            else -> {}
          }
        }
      }
    }
  }
}

@Composable
private fun SafeScreen(
  text: String,
  isError: Boolean,
  onRefresh: () -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("ENTITY", color = EntityTheme.colors().mainText, fontSize = 48.sp)
    Text(
      text,
      color = if (isError) EntityTheme.colors().errorColor else EntityTheme.colors().mainText,
      style = EntityTheme.typography().title,
      modifier = Modifier.padding(top = 16.dp),
      textAlign = TextAlign.Center
    )
    EntityButtonComponent(
      modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp),
      text = "Refresh",
      enabled = true,
      onRefresh
    )
  }
}