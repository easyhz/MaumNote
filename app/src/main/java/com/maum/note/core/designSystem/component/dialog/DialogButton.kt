package com.maum.note.core.designSystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.extension.modifier.singleClickable
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.ButtonShapeColor
import com.maum.note.ui.theme.MainText


@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    dialogButton: BasicDialogButton,
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(dialogButton.backgroundColor)
            .singleClickable { dialogButton.onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dialogButton.text,
            style = dialogButton.style
        )
    }
}

@Preview
@Composable
private fun DialogButtonPreview() {
    DialogButton(
        dialogButton = BasicDialogButton(
            text = "확인",
            backgroundColor = ButtonShapeColor,
            style = AppTypography.heading5_semiBold.copy(color = MainText),
            onClick = {}
        )
    )
}