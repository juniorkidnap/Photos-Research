package com.app.composetraining.framework.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.composetraining.framework.presentation.ui.detail.DetailScreen
import com.app.composetraining.framework.presentation.ui.search.SearchScreen
import com.app.composetraining.framework.presentation.utils.DEFAULT_NAVIGATION_ARGUMENT_VALUE
import com.app.composetraining.framework.presentation.utils.DETAIL_KEY
import com.app.composetraining.framework.presentation.utils.Screen

/**
 * Navigation component to navigate between [SearchScreen] and [DetailScreen]
 */
@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SearchScreen.route
    ) {

        composable(Screen.SearchScreen.route) {
            SearchScreen(
                onClickSeeDetail = { photoId ->
                    navigateToSinglePhoto(
                        navController = navController,
                        photoId = photoId
                    )
                }
            )
        }

        composable(
            route = "${Screen.DetailScreen.route}/{$DETAIL_KEY}",
            arguments = listOf(
                navArgument(DETAIL_KEY) {
                    type = NavType.IntType
                    defaultValue = DEFAULT_NAVIGATION_ARGUMENT_VALUE
                }
            )
        ) {
            DetailScreen(
                onClickSeeAllPhotos = { navController.popBackStack() }
            )
        }
    }
}

private fun navigateToSinglePhoto(
    navController: NavHostController,
    photoId: Int?
) {
    navController.navigate("${Screen.DetailScreen.route}/$photoId")
}
