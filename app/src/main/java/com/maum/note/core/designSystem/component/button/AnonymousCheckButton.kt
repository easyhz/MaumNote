package com.maum.note.core.designSystem.component.button

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.designSystem.extension.modifier.circleClickable
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText

@Composable
fun AnonymousCheckButton(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    textColor: Color = MainText,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier.noRippleClickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Crossfade(
            isChecked
        ) {
            Image(
                modifier = Modifier.size(24.dp).circleClickable(onClick = onClick),
                painter = painterResource(if(it) R.drawable.ic_checkbox_check else R.drawable.ic_checkbox_uncheck),
                contentDescription = null
            )
        }

        Text(
            text = stringResource(R.string.board_anonymous),
            style = AppTypography.body1.copy(
                color = textColor
            )
        )
    }

}

@Preview
@Composable
private fun AnonymousCheckButtonPreview() {
    AnonymousCheckButton(
        modifier = Modifier,
        isChecked = true,
        onClick = {}
    )
}