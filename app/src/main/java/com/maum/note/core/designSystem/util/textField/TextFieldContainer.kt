package com.maum.note.core.designSystem.util.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maum.note.ui.theme.Placeholder
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.White

@Composable
internal fun TextFieldContainer(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType,
    placeholder: String,
    state: TextFieldState,
    innerTextField: @Composable () -> Unit
) {
    TextFieldContainerContent(
        modifier = modifier
            .heightIn(min = textFieldType.minHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .padding(textFieldType.paddingValues),
        textFieldType = textFieldType,
        state = state,
        placeholder = placeholder,
        innerTextField = innerTextField,
    )
}

@Composable
private fun TextFieldContainerContent(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType,
    state: TextFieldState,
    placeholder: String,
    innerTextField: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = textFieldType.alignment
    ) {
        if (state == TextFieldState.Default) {
            Text(
                modifier = Modifier.offset(x = 2.dp, y = (-1.5).dp),
                text = placeholder,
                style = AppTypography.body1.copy(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    )
                ),
                color = Placeholder,
            )
        }
        innerTextField()
    }
}

@Preview
@Composable
private fun Preview() {
    TextFieldContainer(
        modifier = Modifier
            .widthIn(max = 400.dp)
            .sizeIn(maxHeight = 200.dp),
        textFieldType = TextFieldType.Multiple(),
        state = TextFieldState.Default,
        placeholder = "작성하셨던 알림장을 복사해 붙여주세요",
        innerTextField = { }
    )
}