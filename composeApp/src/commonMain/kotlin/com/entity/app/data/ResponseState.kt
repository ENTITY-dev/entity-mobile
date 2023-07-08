package com.entity.app.data

sealed interface ResponseState<out T> {

  object Loading : ResponseState<Nothing>

  data class Error(val throwable: Throwable) : ResponseState<Nothing>

  data class Success<T>(val item: T) : ResponseState<T>

}