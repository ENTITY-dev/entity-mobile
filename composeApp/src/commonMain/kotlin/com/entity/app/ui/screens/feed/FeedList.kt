package com.entity.app.ui.screens.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.entity.app.ui.screens.feed.PostUiModel.Companion.PLACEHOLDER_ID
import com.entity.app.ui.theme.EntityTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedListWithPost(
  feedList: List<PostUiModel>,
  canLoadMore: Boolean,
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  onLoadMore: () -> Unit,
  onAutoPlayItem: (PostUiModel) -> Unit,
  onSceneClick: (PostUiModel) -> Unit,
  onOptionsClick: (PostUiModel) -> Unit,
) {
  val listState = rememberLazyListState()

  val isScrolledToEnd = remember {
    derivedStateOf {
      listState.isScrolledToEnd()
    }
  }

  val autoplayIndex by remember {
    derivedStateOf {
      if (feedList.size - 1 == listState.firstVisibleItemIndex)
        listState.firstVisibleItemIndex
      else listState.firstVisibleItemIndex + 1
    }
  }

  val pullRefreshState = rememberPullRefreshState(
    refreshing = isRefreshing,
    onRefresh = onRefresh
  )
  Box(
    modifier = Modifier.pullRefresh(pullRefreshState)
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      state = listState
    ) {
      items(feedList, key = { item -> item.sceneId }) { model ->
        val modifier = remember {
          Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
          )
        }
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
    PullRefreshIndicator(
      refreshing = isRefreshing,
      pullRefreshState,
      Modifier.align(Alignment.TopCenter),
      backgroundColor = EntityTheme.colors().bgMain,
      contentColor = EntityTheme.colors().mainText
    )
  }

  if (isScrolledToEnd.value && canLoadMore) {
    onLoadMore.invoke()
  }
  LaunchedEffect(autoplayIndex) {
    onAutoPlayItem.invoke(feedList[autoplayIndex])
  }
}

private fun LazyListState.isScrolledToEnd(): Boolean {
  val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
  val total = layoutInfo.totalItemsCount
  return lastVisible in total - 1..total
}


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