package com.entity.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun AnimationBox(
  enter: EnterTransition = fadeIn(),
  exit: ExitTransition = fadeOut(),
  content: @Composable () -> Unit,
) {
  val state = remember {
    MutableTransitionState(false).apply {
      targetState = true
    }
  }

  AnimatedVisibility(
    visibleState = state,
    enter = enter,
    exit = exit
  ) { content() }
}