package com.entity.app.ui.screens.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.entity.app.ui.screens.feed.PostUiModel
import com.entity.app.ui.screens.feed.PostUiModel.Companion.PLACEHOLDER_ID

@Composable
fun FeedListWithPost(
  feedList: List<PostUiModel>,
  canLoadMore: Boolean,
  loadMore: () -> Unit,
  onSceneClick: (PostUiModel) -> Unit,
  onOptionsClick: (PostUiModel) -> Unit,
) {
  val listState = rememberLazyListState()

  val endOfListReached = remember {
    derivedStateOf {
      listState.isScrolledToEnd() && canLoadMore
    }
  }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    state = listState
  ) {
    itemsIndexed(feedList, key = { _, item -> item.sceneId }) { _, model ->
      val modifier = Modifier.padding(
        horizontal = 16.dp,
        vertical = 8.dp
      )
      if (model.sceneId == PLACEHOLDER_ID) {
        PlaceholderPostComponent(
          modifier = modifier
        )
      } else {
        PostComponent(
          modifier = modifier,
          model = model,
          onSceneClick = onSceneClick,
          onOptionsClick = onOptionsClick
        )
      }
    }
  }
  LaunchedEffect(endOfListReached) {
    loadMore.invoke()
  }
}

private fun LazyListState.isScrolledToEnd() =
  layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1


@Composable
fun FeedListWithPlaceholders(
  count: Int,
) {
  val modifier = Modifier.padding(
    horizontal = 16.dp,
    vertical = 8.dp
  )
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(count) {
      PlaceholderPostComponent(
        modifier = modifier
      )
    }
  }
}