package com.entity.app.ui.screens.feed//package com.entity.app.ui.components.feed

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.entity.app.ui.theme.EntityTheme
import com.entity.app.ui.theme.shimmerColorShades

@Composable
fun PlaceholderPostComponent(modifier: Modifier) {
  /*
  Create InfiniteTransition
  which holds child animation like [Transition]
  animations start running as soon as they enter
  the composition and do not stop unless they are removed
  */
  val transition = rememberInfiniteTransition()
  val translateAnim by transition.animateFloat(
    /*
    Specify animation positions,
    initial Values 0F means it starts from 0 position
    */
    initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
      /*
       Tween Animates between values over specified [durationMillis]
      */
      tween(durationMillis = 1200, easing = FastOutSlowInEasing), Reverse
    )
  )

  /*
    Create a gradient using the list of colors
    Use Linear Gradient for animating in any direction according to requirement
    start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
    end= Animate the end position to give the shimmer effect using the transition created above
  */
  val brush = Brush.linearGradient(
    colors = shimmerColorShades,
    start = Offset(10f, 10f),
    end = Offset(translateAnim, translateAnim)
  )

  ShimmerItem(brush = brush, modifier = modifier)
}


@Composable
fun ShimmerItem(
  brush: Brush,
  modifier: Modifier = Modifier,
) {
  Spacer(
    modifier = modifier
      .fillMaxWidth()
      .height(180.dp)
      .background(brush = brush, shape = EntityTheme.shapes().medium),
  )
}