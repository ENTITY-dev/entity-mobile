package com.entity.app.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.entity.app.ui.screens.feed.FeedScreen
import com.entity.app.ui.screens.login.LoginScreen
import com.entity.app.ui.theme.EntityTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Airplay
import compose.icons.feathericons.List

@Composable
internal fun App() = EntityTheme(
  darkTheme = true
) {
  TabNavigator(FeedScreen) {
    Scaffold(
      content = {
        CurrentTab()
      },
      bottomBar = {
        BottomNavigation {
          TabNavigationItem(FeedScreen)
//          TabNavigationItem(FavoritesTab)
//          TabNavigationItem(ProfileTab)
        }
      }
    )
  }

}

object MainScreen: Screen {

  @Composable
  override fun Content() {

  }

}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
  val tabNavigator = LocalTabNavigator.current

  val icon = rememberVectorPainter(
    when(tab) {
      is FeedScreen -> FeatherIcons.List
      else -> FeatherIcons.Airplay
    }
  )

  val title = when(tab) {
    is FeedScreen -> "Feed"
    else -> "Unknown"
  }

  BottomNavigationItem(
    selected = tabNavigator.current == tab,
    onClick = { tabNavigator.current = tab },
    icon = { Icon(painter = icon, contentDescription = title) }
  )
}