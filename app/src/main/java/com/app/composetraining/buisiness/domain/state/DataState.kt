package com.app.composetraining.buisiness.domain.state

/**
 * State class that notifies caller about request statement at the moment
 */
sealed class DataState <out R> {

    data class Success<out T>(val data: T): DataState<T>()
    object Error: DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
