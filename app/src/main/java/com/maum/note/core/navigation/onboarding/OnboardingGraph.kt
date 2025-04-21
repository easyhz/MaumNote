package com.maum.note.core.navigation.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.maum.note.core.navigation.onboarding.screen.Onboarding
import com.maum.note.ui.screen.onboarding.age.OnboardingAgeScreen
import com.maum.note.ui.screen.onboarding.start.OnboardingStartScreen

fun NavGraphBuilder.onboardingGraph(
    navController: NavController
) {
    navigation<Onboarding>(
        startDestination = Onboarding.Start,
    ) {
        composable<Onboarding.Start> {
            OnboardingStartScreen()
        }

        composable<Onboarding.Age> {
            OnboardingAgeScreen()
        }

        composable<Onboarding.Tone> {
//             OnboardingToneScreen(
//                 navigateToHome = navController::navigateToHome
//             )
        }
    }
}

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    navigate(route = Onboarding, navOptions = navOptions)
}

fun NavController.navigateToOnboardingAge(navOptions: NavOptions? = null) {
    navigate(route = Onboarding.Age, navOptions = navOptions)
}

fun NavController.navigateToOnboardingTone(navOptions: NavOptions? = null) {
    navigate(route = Onboarding.Tone, navOptions = navOptions)
}