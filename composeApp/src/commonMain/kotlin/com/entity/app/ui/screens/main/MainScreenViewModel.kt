package com.entity.app.ui.screens.main

import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.navigator.tab.Tab
import com.entity.app.data.repository.BottomBarNavigationStateRepository
import com.entity.app.ui.EntityViewModel
import com.entity.app.ui.screens.main.MainScreenViewEvent.TabChange
import com.entity.app.ui.tabs.feed.FeedTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MainScreenViewModel : EntityViewModel<MainScreenViewState, MainScreenViewEvent, MainScreenViewAction>(
  MainScreenViewState.Normal(bottomBarVisible = true)
) {

  private val bottomBarNavigationStateRepository: BottomBarNavigationStateRepository by inject()
  private val currentTabStateFlow = MutableStateFlow<Tab>(FeedTab)

  init {
    coroutineScope.launch {
      bottomBarNavigationStateRepository.isVisibleFlow.combine(
        currentTabStateFlow
      ) { isVisible, tab ->
        val isFeedTab = tab == FeedTab
        val bottomBarVisible = (isVisible && isFeedTab) || !isFeedTab
        viewState = MainScreenViewState.Normal(bottomBarVisible = bottomBarVisible)
      }.collect()
    }
  }

  override fun obtainEvent(viewEvent: MainScreenViewEvent) {
    when (viewEvent) {
      is TabChange -> {
        currentTabStateFlow.value = viewEvent.current
      }
    }
  }
}