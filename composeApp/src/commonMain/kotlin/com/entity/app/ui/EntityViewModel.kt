package com.entity.app.ui

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.koin.core.component.KoinComponent

abstract class EntityViewModel<State, Event, Action>(initialState: State) : StateScreenModel<State>(initialState), KoinComponent {

  private val _viewStates = mutableState

  private val _viewActions = MutableSharedFlow<Action?>(extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST)

  fun viewStates() = state

  fun viewActions() = _viewActions.asSharedFlow()

  protected var viewState: State
    get() = state.value
    set(value) {
      _viewStates.value = value
    }

  protected var viewAction: Action?
    get() = _viewActions.replayCache.last()
    set(value) {
      _viewActions.tryEmit(value)
    }

  abstract fun obtainEvent(viewEvent: Event)
}