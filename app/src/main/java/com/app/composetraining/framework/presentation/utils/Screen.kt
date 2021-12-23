package com.app.composetraining.framework.presentation.utils

/**
 * App Screens with routes to them
 */
sealed class Screen(val route: String) {

    object SearchScreen : Screen(route = "search_screen")
    object DetailScreen : Screen(route = "detail_screen")
}
