package com.entity.app.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.entity.app.ui.screens.launch.LaunchScreen
import com.entity.app.ui.screens.main.MainScreen
import com.entity.app.ui.screens.scene.SceneViewerScreen
import com.entity.app.ui.theme.EntityTheme
import io.sentry.kotlin.multiplatform.Sentry

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun App() = EntityTheme(
  darkTheme = true
) {
  var navigateToMain by mutableStateOf(false)
  Box(modifier = Modifier.fillMaxSize().background(EntityTheme.colors().bgMain)) {
    Navigator(
      LaunchScreen,
      onBackPressed = { screen ->
        navigateToMain = screen is SceneViewerScreen
        screen !is SceneViewerScreen
      }
    ) { navigator ->
      FadeTransition(
        navigator,
        animationSpec = tween(300)
      )
      LaunchedEffect(navigateToMain) {
        if (navigateToMain) {
          navigator.popUntilRoot()
          navigator.replace(MainScreen)
        }
      }
    }
  }
  LaunchedEffect(Unit) {
    Sentry.captureMessage("Application started")
  }
}
