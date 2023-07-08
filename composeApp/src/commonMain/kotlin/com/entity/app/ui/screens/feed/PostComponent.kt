package com.entity.app.ui.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.entity.app.ui.RippleTransparentButton
import com.entity.app.ui.components.ImageSlider
import com.entity.app.ui.theme.EntityTheme
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import compose.icons.FeatherIcons
import compose.icons.feathericons.MoreVertical

@Composable
fun PostComponent(
  modifier: Modifier,
  model: PostUiModel,
  onSceneClick: (PostUiModel) -> Unit,
  onOptionsClick: (PostUiModel) -> Unit,
) {
  val scenePainterAction by rememberImageAction(model.scenePreviewUrl ?: "")
  val authorPainterAction by rememberImageAction(model.authorImageUrl ?: "")

  val scenePainter = rememberImageActionPainter(
    scenePainterAction,
    placeholderPainter = {
      ColorPainter(EntityTheme.colors().bgMinor)
    },
    errorPainter = {
      ColorPainter(EntityTheme.colors().bgMinor)
    }
  )
  val authorPainter = rememberImageActionPainter(
    authorPainterAction,
    placeholderPainter = {
      ColorPainter(EntityTheme.colors().bgMinor)
    },
    errorPainter = {
      ColorPainter(EntityTheme.colors().bgMinor)
    }
  )
  val more = rememberVectorPainter(FeatherIcons.MoreVertical)

  RippleTransparentButton(
    onClick = { onSceneClick.invoke(model) },
    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
  ) {
    Column(modifier = modifier.fillMaxWidth()) {
      // scene image
      ImageSlider(
        listOf(model.scenePreviewUrl, model.scenePreviewUrl, model.scenePreviewUrl),
        modifier = Modifier
          .fillMaxWidth()
          .height(238.dp),
      )

      Spacer(modifier = Modifier.fillMaxWidth().height(12.dp))
      //bottom item
      Row(modifier = Modifier.fillMaxWidth()) {
        //author image
        Image(
          authorPainter,
          contentDescription = null,
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape),
          contentScale = ContentScale.FillBounds
        )
        //texts container
        Column(modifier = Modifier.padding(start = 12.dp)) {
          Text(
            modifier = Modifier.width(200.dp),
            text = model.sceneTitle,
            style = EntityTheme.typography().title,
            color = EntityTheme.colors().mainText
          )
          Text(
            modifier = Modifier.padding(top = 2.dp).width(200.dp),
            text = model.sceneSubtitle,
            style = EntityTheme.typography().subtitle,
            color = EntityTheme.colors().minorText
          )
        }

        // trail container
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.End
        ) {
          RippleTransparentButton(
            onClick = { onOptionsClick.invoke(model) },
            modifier = Modifier.size(24.dp),
          ) {
            Image(
              painter = more,
              contentDescription = "options",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize(),
              colorFilter = ColorFilter.tint(Color.White)
            )
          }
        }
      }
    }
  }
}