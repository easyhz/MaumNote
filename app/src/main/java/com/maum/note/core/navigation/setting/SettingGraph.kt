package com.maum.note.core.navigation.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.maum.note.core.navigation.setting.screen.Setting
import com.maum.note.ui.screen.setting.setting.SettingScreen
import com.maum.note.ui.screen.setting.tone.ToneSettingScreen

fun NavGraphBuilder.settingGraph(
     navController: NavController
) {
    navigation<Setting>(
        startDestination = Setting.Main,
    ) {
        composable<Setting.Main> {
            SettingScreen(
                navigateUp = navController::navigateUp,
                navigateToToneSetting = navController::navigateToSettingTone
            )
        }

        composable<Setting.Tone> {
            ToneSettingScreen(
                navigateUp = navController::navigateUp,
            )
        }
    }
}

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(route = Setting, navOptions = navOptions)
}

fun NavController.navigateToSettingTone(navOptions: NavOptions? = null) {
    navigate(route = Setting.Tone, navOptions = navOptions)
}
