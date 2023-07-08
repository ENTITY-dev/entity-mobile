package com.entity.app.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BottomBarNavigationStateRepository {

  private val _isVisibleFlow = MutableStateFlow(false)
  val isVisibleFlow = _isVisibleFlow.asStateFlow()

  fun setVisible(visible: Boolean) {
    _isVisibleFlow.value = visible
  }

}