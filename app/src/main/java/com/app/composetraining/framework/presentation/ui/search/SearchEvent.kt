package com.app.composetraining.framework.presentation.ui.search

sealed class SearchEvent {

    object NextPageEvent: SearchEvent()
    data class NewSearchEvent(
        val searchQuery: String
    ) : SearchEvent()
}
