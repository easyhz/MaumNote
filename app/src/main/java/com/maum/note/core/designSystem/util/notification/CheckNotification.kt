package com.maum.note.core.designSystem.util.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat


@Composable
fun CheckNotification(
    showNotificationPermission: Boolean = true,
    action: ((Boolean) -> Unit)? = null,
) {
    val context = LocalContext.current

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            action?.invoke(isGranted)
        }

    LaunchedEffect(Unit) {
        if (hasNotificationPermission(context)) return@LaunchedEffect
        if (!showNotificationPermission) return@LaunchedEffect
        checkNotificationPermission(
            context = context,
            launcher = requestPermissionLauncher
        ) {
            val enabled = NotificationManagerCompat.from(context).areNotificationsEnabled()
            action?.invoke(enabled)
        }
    }
}

private fun hasNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

private fun checkNotificationPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    action: () -> Unit,
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) -> {
            action()
        }
        else -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}