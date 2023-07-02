package com.entity.app.ui.tabs.user

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.entity.app.ui.EntityButtonComponent
import com.entity.app.ui.screens.login.LoginScreen
import com.entity.app.ui.tabs.user.UserTabViewState.Auth
import com.entity.app.ui.tabs.user.UserTabViewState.NotAuth
import compose.icons.FeatherIcons
import compose.icons.feathericons.User


internal object UserTab : Tab {

  override val options: TabOptions
    @Composable
    get() {
      val title = "User"
      val icon = rememberVectorPainter(FeatherIcons.User)
      return remember {
        TabOptions(
          index = 1u,
          title = title,
          icon = icon
        )
      }
    }

  @Composable
  override fun Content() {
    val navigator = LocalTabNavigator.current
    val screenModel = rememberScreenModel { UserTabViewModel() }
    val viewState by screenModel.viewStates().collectAsState()
    val viewAction by screenModel.viewActions().collectAsState(null)

    when (val state = viewState) {
      NotAuth -> {
        LoginScreen.Content()
      }

      Auth -> {
        EntityButtonComponent(modifier = Modifier.padding(horizontal = 16.dp), "Loguut") {
          screenModel.obtainEvent(UserTabEvent.Logout)
        }
      }
    }

    when (val action = viewAction) {
      else -> {}
    }
//
//    LaunchedEffect(Unit) {
//      screenModel.obtainEvent(ViewAppear)
//    }
  }

}