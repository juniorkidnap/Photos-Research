package com.app.composetraining.framework.presentation.ui.search

sealed class SearchScreenEvents {

    data class ShowSnackbar(val message: String) : SearchScreenEvents()
}
