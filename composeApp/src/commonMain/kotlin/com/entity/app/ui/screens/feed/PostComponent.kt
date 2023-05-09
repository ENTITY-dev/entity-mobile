package com.entity.app.ui.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.entity.app.ui.RippleTransparentButton
import com.entity.app.ui.components.AnimationBox
import com.entity.app.ui.theme.EntityTheme
import com.seiko.imageloader.AsyncImagePainter
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.ImageRequestState.Success
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberAsyncImagePainter
import compose.icons.FeatherIcons
import compose.icons.feathericons.MoreVertical
import io.github.skeptick.libres.compose.painterResource

@Composable
fun PostComponent(
  modifier: Modifier,
  model: PostUiModel,
  onSceneClick: (PostUiModel) -> Unit,
  onOptionsClick: (PostUiModel) -> Unit,
) {

  val scenePainter = rememberAsyncImagePainter(model.scenePreviewUrl ?: "")
  val authorPainter = rememberAsyncImagePainter(model.authorImageUrl ?: "")
  val more = rememberVectorPainter(FeatherIcons.MoreVertical)

  RippleTransparentButton(
    onClick = { onSceneClick.invoke(model) },
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
  ) {
    Column(modifier = modifier.fillMaxWidth()) {
      if (scenePainter.requestState == Success) {
        Image(
          scenePainter,
          null,
          modifier = Modifier
            .fillMaxWidth()
            .height(238.dp),
          contentScale = ContentScale.FillHeight
        )

      } else {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(238.dp)
            .background(EntityTheme.colors().bgMinor)
        )
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 12.dp)
      ) {
        if (authorPainter.requestState == Success) {
          Image(
            authorPainter,
            null,
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
          )
        } else {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(EntityTheme.colors().bgMinor)
          )
        }

        Column(modifier = Modifier.padding(start = 12.dp)) {
          Text(
            modifier = Modifier.width(200.dp),
            text = model.sceneTitle,
            style = EntityTheme.typography().title,
            color = EntityTheme.colors().mainText
          )
          Text(
            modifier = Modifier
              .padding(top = 2.dp)
              .width(200.dp),
            text = model.sceneSubtitle,
            style = EntityTheme.typography().subtitle,
            color = EntityTheme.colors().minorText
          )
        }
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.End
        ) {
          RippleTransparentButton(
            onClick = { onOptionsClick.invoke(model) },
            modifier = Modifier.size(24.dp)
          ) {
            Image(
              painter = more,
              contentDescription = "options",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize()
            )
          }
        }
      }
    }
  }

//  RippleTransparentButton(
//    onClick = { onSceneClick.invoke(model) },
//    modifier = Modifier
//      .fillMaxWidth()
//      .wrapContentHeight()
//  ) {
//      authorPainter
//    }
//  }
}