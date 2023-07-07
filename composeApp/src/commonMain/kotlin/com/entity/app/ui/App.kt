package com.entity.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.Navigator
import com.entity.app.ui.screens.launch.LaunchScreen
import com.entity.app.ui.theme.EntityTheme
import io.sentry.kotlin.multiplatform.Sentry

@Composable
internal fun App() = EntityTheme(
  darkTheme = true
) {
  Navigator(LaunchScreen)
  LaunchedEffect(Unit) {
    Sentry.captureMessage("Application started")
  }
}
