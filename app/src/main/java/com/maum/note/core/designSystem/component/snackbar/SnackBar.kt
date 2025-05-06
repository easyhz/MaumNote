package com.maum.note.core.designSystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.PrimaryBackground

@Composable
internal fun AppSnackBar(
    snackBarData: SnackbarData
) {
    val actionLabel = snackBarData.visuals.actionLabel
    val actionComposable: (@Composable () -> Unit)? =
        if (actionLabel != null) {
            @Composable {
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(8.dp))
                        .noRippleClickable { snackBarData.performAction() }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        text = actionLabel,
                        color = Red,
                        style = AppTypography.body1
                    )
                }
            }
        } else {
            null
        }

    val dismissActionComposable: (@Composable () -> Unit)? =
        if (snackBarData.visuals.withDismissAction) {
            @Composable {
                IconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = { snackBarData.dismiss() },
                    content = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = MainBackground,
                        )
                    }
                )
            }
        } else {
            null
        }

    AppSnackBar(
        message = snackBarData.visuals.message,
        actionComposable = actionComposable,
        dismissActionComposable = dismissActionComposable
    )
}


@Composable
private fun AppSnackBar(
    message: String,
    actionComposable: (@Composable () -> Unit)? = null,
    dismissActionComposable: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = PrimaryBackground,
        shape = RoundedCornerShape(size = 16.dp),
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = message,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MainText,
                style = AppTypography.body1
            )
            actionComposable?.invoke()
            dismissActionComposable?.invoke()
        }
    }
}

@Preview
@Composable
private fun SnackBarPreview() {
    AppSnackBar(message = "skljfksljfskdljfklsdjfklsjlkfjsaklfj")
}