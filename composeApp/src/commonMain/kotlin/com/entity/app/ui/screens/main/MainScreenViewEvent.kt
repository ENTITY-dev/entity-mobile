package com.entity.app.ui.screens.main

import cafe.adriel.voyager.navigator.tab.Tab

sealed interface MainScreenViewEvent {
  class TabChange(val current: Tab) : MainScreenViewEvent
}