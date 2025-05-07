package com.maum.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.maum.note.core.common.util.date.AppDateTimeFormatter
import com.maum.note.core.navigation.MaumNoteApp
import com.maum.note.ui.theme.MaumNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appDateTimeFormatter: AppDateTimeFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MaumNoteTheme(
                appDateTimeFormatter = appDateTimeFormatter
            ) {
                MaumNoteApp()
            }
        }
    }
}