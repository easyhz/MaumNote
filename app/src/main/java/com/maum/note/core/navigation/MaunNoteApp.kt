package com.maum.note.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.maum.note.core.designSystem.util.transition.SlideDirection
import com.maum.note.core.designSystem.util.transition.enterSlide
import com.maum.note.core.designSystem.util.transition.exitSlide
import com.maum.note.core.navigation.board.boardGraph
import com.maum.note.core.navigation.home.homeGraph
import com.maum.note.core.navigation.note.creation.noteCreationGraph
import com.maum.note.core.navigation.note.detail.noteDetailGraph
import com.maum.note.core.navigation.onboarding.onboardingGraph
import com.maum.note.core.navigation.report.reportGraph
import com.maum.note.core.navigation.setting.settingGraph
import com.maum.note.core.navigation.splash.screen.Splash
import com.maum.note.core.navigation.splash.splashGraph
import com.maum.note.core.navigation.util.BottomBar

@Composable
fun MaumNoteApp(
    modifier: Modifier = Modifier,
    appNavController: AppNavController = rememberAppNavController()
) {
    val navController = appNavController.navController
    val isVisibleBottomBar = appNavController.isInBottomTabs()
    val currentTab = appNavController.mapRouteToTab()
    Scaffold(
        modifier = modifier.systemBarsPadding(),
        bottomBar = {
            if (isVisibleBottomBar) {
                BottomBar(
                    current = currentTab,
                    onSelected = appNavController::navigate
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Splash,
            enterTransition = { enterSlide(SlideDirection.Start) },
            exitTransition = { exitSlide(SlideDirection.Start) },
            popEnterTransition = { enterSlide(SlideDirection.End) },
            popExitTransition = { exitSlide(SlideDirection.End) }
        ) {
            splashGraph(navController = navController)
            onboardingGraph(navController = navController)
            homeGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
            settingGraph(navController = navController)
            noteCreationGraph(navController = navController)
            noteDetailGraph(navController = navController)
            boardGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
            reportGraph(
                navController = navController
            )
        }
    }
}