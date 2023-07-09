package com.entity.app.ui.screens.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.entity.app.ui.screens.feed.PostUiModel.Companion.PLACEHOLDER_ID

@Composable
fun FeedListWithPost(
  feedList: List<PostUiModel>,
  canLoadMore: Boolean,
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