package com.entity.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.entity.app.ui.screens.feed.FeedScreen
import com.entity.app.ui.screens.login.LoginScreen
import com.entity.app.ui.theme.EntityTheme

@Composable
internal fun App() = EntityTheme(
  darkTheme = true
) {
  Navigator(LoginScreen()) { navigator ->
    Scaffold(
      backgroundColor = EntityTheme.colors().bgMain,
      topBar = { /* ... */ },
      content = { CurrentScreen() },
      bottomBar = { /* ... */ },
      modifier = Modifier.fillMaxSize()
    )
  }
}