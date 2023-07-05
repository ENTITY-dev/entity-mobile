package com.entity.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.entity.app.ui.tabs.feed.FeedTab
import com.entity.app.ui.tabs.user.UserTab
import com.entity.app.ui.theme.EntityTheme
import io.github.aakira.napier.Napier
import io.sentry.kotlin.multiplatform.Sentry

@OptIn(ExperimentalVoyagerApi::class)
@Composable
internal fun App() = EntityTheme(
  darkTheme = true
) {
  var bottomBarIsVisible by remember {
    mutableStateOf(false)
  }
  val changeVisibleBottomBarCallback = remember {
    { visible: Boolean ->
      bottomBarIsVisible = visible
    }
  }
  val feedTab = remember {
    FeedTab(changeVisibleBottomBarCallback)
  }

  TabNavigator(
    tab = feedTab,
    tabDisposable = {
      TabDisposable(
        navigator = it,
        tabs = listOf(feedTab, UserTab)
      )
    }
  ) {
    Scaffold(content = {
      Box(modifier = Modifier.padding(it)) {
        CurrentTab()
      }
    }, bottomBar = {
      AnimatedVisibility(
        bottomBarIsVisible,
        enter = slideInVertically(
          animationSpec = spring(
            stiffness = 500f,
            visibilityThreshold = IntOffset.VisibilityThreshold
          ),
          initialOffsetY = { it }
        )
      ) {
        BottomNavigation(
          backgroundColor = EntityTheme.colors().bgMain, contentColor = EntityTheme.colors().mainText
        ) {
          TabNavigationItem(feedTab)
          TabNavigationItem(UserTab)
        }
      }
    }, backgroundColor = EntityTheme.colors().bgMain
    )
  }
  LaunchedEffect(Unit) {
    Sentry.captureMessage("Application started")
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