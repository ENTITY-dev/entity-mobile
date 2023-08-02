package com.entity.app.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.entity.app.ui.theme.EntityTheme
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(images: List<String?>, modifier: Modifier, isAutoPlay: Boolean) {
  val pagerState = rememberPagerState()
  val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
  if (isDragged.not()) {
    with(pagerState) {
      var currentPageKey by remember { mutableStateOf(0) }
      LaunchedEffect(isAutoPlay) {
        launch {
          while (isAutoPlay) {
            delay(timeMillis = 3000)
            val nextPage = (currentPage + 1).mod(images.size)
            animateScrollToPage(page = nextPage)
            currentPageKey = nextPage
          }
        }
      }
    }
  }
  Box(modifier = modifier) {
    HorizontalPager(
      pageCount = images.size,
      state = pagerState,
      modifier = Modifier.fillMaxSize(),
    ) { page ->
      val url = images[page]
      val painterAction by rememberImageAction(url ?: "")
      val painter = rememberImageActionPainter(
        painterAction,
        placeholderPainter = {
          ColorPainter(EntityTheme.colors().bgMinor)
        },
        errorPainter = {
          ColorPainter(EntityTheme.colors().bgMinor)
        }
      )
      Image(
        painter,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
      )
    }
    if (images.size != 1) {
      DotIndicators(
        modifier = Modifier.height(14.dp).align(Alignment.BottomEnd).padding(end = 4.dp, bottom = 4.dp),
        pagerState,
        images.size
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DotIndicators(
  modifier: Modifier,
  pagerState: PagerState,
  pageCount: Int,
) {
  Row(modifier = modifier) {
    repeat(pageCount) { iteration ->
      val color = if (pagerState.currentPage == iteration) Color.White else Color.Gray
      Box(
        modifier = Modifier
          .size(8.dp)
          .clip(CircleShape)
          .background(color)
      )
      if (iteration != pageCount - 1) {
        Spacer(Modifier.width(3.dp))
      }
    }
  }
}