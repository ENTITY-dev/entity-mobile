package com.entity.app.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.entity.app.ui.EntityButtonComponent
import com.entity.app.ui.screens.profile.ProfileScreenState.Normal
import kotlinx.coroutines.launch

object ProfileScreen : Screen {

  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { ProfileScreenViewModel() }
    val viewState by screenModel.viewStates().collectAsState()

    when (val state = viewState) {
      is Normal -> {
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(modifier = Modifier.fillMaxSize()) {
            EntityButtonComponent(
              modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center),
              text = "Logout"
            ) {
              screenModel.obtainEvent(ProfileScreenEvent.Logout)
            }
          }
        }
      }

      else -> {}
    }

    LaunchedEffect(Unit) {
      launch {
        screenModel.viewActions().collect { action ->
          when (action) {
            else -> {}
          }
        }
      }
    }
  }
}