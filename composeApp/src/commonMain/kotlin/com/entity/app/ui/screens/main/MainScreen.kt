package com.entity.app.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.entity.app.ui.screens.main.MainScreenViewState.Normal
import com.entity.app.ui.tabs.feed.FeedTab
import com.entity.app.ui.tabs.user.UserTab
import com.entity.app.ui.theme.EntityTheme
import io.github.aakira.napier.Napier


internal object MainScreen : Screen {

  @OptIn(ExperimentalVoyagerApi::class)
  @Composable
  override fun Content() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = rememberScreenModel { MainScreenViewModel() }
    val viewState by screenModel.viewStates().collectAsState()

    when (val state = viewState) {
      is Normal -> {
        TabNavigator(
          tab = FeedTab,
          tabDisposable = {
            TabDisposable(
              navigator = it,
              tabs = listOf(FeedTab, UserTab)
            )
          }
        ) { tabNavigator ->
          LaunchedEffect(tabNavigator.current) {
            screenModel.obtainEvent(MainScreenViewEvent.TabChange(tabNavigator.current))
          }
          Scaffold(content = {
            Box(modifier = Modifier.padding(it)) {
              CurrentTab()
            }
          }, bottomBar = {
            AnimatedVisibility(
              state.bottomBarVisible,
              enter = slideInVertically(
                animationSpec = spring(
                  stiffness = 500f,
                  visibilityThreshold = IntOffset.VisibilityThreshold
                ),
                initialOffsetY = { it }
              ),
              exit = slideOutVertically(
                targetOffsetY = { 0 }
              )
            ) {
              BottomNavigation(
                backgroundColor = EntityTheme.colors().bgMain, contentColor = EntityTheme.colors().mainText
              ) {
                TabNavigationItem(FeedTab)
                TabNavigationItem(UserTab)
              }
            }
          },
            backgroundColor = EntityTheme.colors().bgMain
          )
        }
      }
    }

  }
}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
  val tabNavigator = LocalTabNavigator.current
  val icon = tab.options.icon
  if (icon == null) {
    Napier.e("Didn't provide icon for tab")
    return
  }
  BottomNavigationItem(
    selected = tabNavigator.current == tab,
    onClick = { tabNavigator.current = tab },
    icon = { Icon(painter = icon, contentDescription = tab.options.title) }
  )
}