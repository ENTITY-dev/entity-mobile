package com.entity.app.ui.tabs.feed

import cafe.adriel.voyager.core.screen.Screen

sealed interface FeedTabEvent {
  class ScreenChange(val lastItem: Screen) : FeedTabEvent
}