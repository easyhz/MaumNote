package com.maum.note.core.designSystem.component.dialog


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.maum.note.core.designSystem.util.dialog.BasicDialogButton
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.AppTypography.body1
import com.maum.note.ui.theme.AppTypography.heading4_semiBold
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.SubText

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    title: String,
    content: String?,
    positiveButton: BasicDialogButton?,
    negativeButton: BasicDialogButton?,
    onDismissRequest: () -> Unit = negativeButton?.onClick ?: positiveButton?.onClick ?: { }
) {
    val innerPadding = 12.dp
    val buttonMinWidth = 132.dp
    val buttonSpacing = 12.dp
    val columSpace = if(content == null) 24.dp else 12.dp

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = modifier
                .widthIn(max = innerPadding * 2 + buttonMinWidth * 2 + buttonSpacing + 32.dp)
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MainBackground)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(columSpace)
        ) {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = title,
                textAlign = TextAlign.Center,
                style = heading4_semiBold,
                color = MainText
            )
            content?.let {
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = it,
                    textAlign = TextAlign.Center,
                    style = body1,
                    color = SubText
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                negativeButton?.let {
                    DialogButton(
                        modifier = Modifier.widthIn(min = buttonMinWidth).weight(1f),
                        dialogButton = it
                    )
                }
                positiveButton?.let {
                    DialogButton(
                        modifier = Modifier.widthIn(min = buttonMinWidth).weight(1f),
                        dialogButton = it
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
//    device = "spec:shape=Normal,width=240,height=640, unit=dp, dpi= 480"
)
@Composable
fun BasicDialogPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BasicDialog(
            title = "오류",
            content = "네트워크 오류가 발생했습니다.",
            positiveButton =  BasicDialogButton(
                text = "확인",
                backgroundColor = Primary,
                style = AppTypography.heading5_semiBold.copy(color = Color.White),
                onClick = {}
            ),
            negativeButton = null
        )
    }
}