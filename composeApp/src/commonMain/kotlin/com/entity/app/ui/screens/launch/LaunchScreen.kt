package com.entity.app.ui.screens.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.entity.app.ui.components.EntityCircularProgressIndicator
import com.entity.app.ui.screens.launch.LaunchScreenAction.OpenMainScreen
import com.entity.app.ui.screens.launch.LaunchScreenViewState.Loading
import com.entity.app.ui.screens.main.MainScreen
import com.entity.app.ui.theme.EntityTheme
import kotlinx.coroutines.flow.collectLatest


object LaunchScreen : Screen {

  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { LaunchScreenViewModel() }
    val viewState by screenModel.viewStates().collectAsState()
    when(val state = viewState) {
      Loading -> {
        SplashContent()
      }
    }

    LaunchedEffect(Unit) {
      screenModel.viewActions().collectLatest {
        when(val action = it) {
          OpenMainScreen -> {
            navigator.replace(MainScreen)
          }
          else -> {}
        }
      }
    }
  }

}

@Composable
private fun SplashContent() {
  Column(
    modifier = Modifier.background(EntityTheme.colors().bgMain).fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    EntityCircularProgressIndicator(modifier = Modifier.size(68.dp))
  }
}