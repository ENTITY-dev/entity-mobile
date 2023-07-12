package com.entity.app.ui.screens.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.entity.app.ui.components.EntityCircularProgressIndicator
import com.entity.app.ui.screens.launch.LaunchScreenAction.OpenMainScreen
import com.entity.app.ui.screens.launch.LaunchScreenAction.OpenPromoScreen
import com.entity.app.ui.screens.launch.LaunchScreenViewState.Loading
import com.entity.app.ui.screens.main.MainScreen
import com.entity.app.ui.screens.scene.SceneScreenParam
import com.entity.app.ui.screens.scene.SceneViewerScreen
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
      screenModel.viewActions().collectLatest { action ->
        when (action) {
          OpenMainScreen -> {
            navigator.replace(MainScreen)
          }

          is OpenPromoScreen -> {
            navigator.push(SceneViewerScreen(SceneScreenParam(action.sceneId, true)))
          }

          else -> {

          }
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
    Text("ENTITY", color = EntityTheme.colors().mainText, fontSize = 48.sp)
    Spacer(modifier = Modifier.height(36.dp))
    EntityCircularProgressIndicator(modifier = Modifier.size(68.dp))
  }
}