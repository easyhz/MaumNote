package com.maum.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import com.maum.note.ui.screen.home.HomeScreen
import com.maum.note.ui.theme.MaumNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaumNoteTheme {
                HomeScreen(
                    modifier = Modifier.systemBarsPadding(),
                )
            }
        }
    }
}