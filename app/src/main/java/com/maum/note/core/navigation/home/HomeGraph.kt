package com.maum.note.core.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.maum.note.core.navigation.home.screen.Home
import com.maum.note.ui.screen.home.HomeScreen

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    composable<Home> {
        HomeScreen()
    }
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(route = Home, navOptions = navOptions)
}