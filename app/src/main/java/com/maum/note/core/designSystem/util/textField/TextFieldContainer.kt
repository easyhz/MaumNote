package com.maum.note.core.designSystem.util.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.ui.theme.Placeholder
import com.maum.note.ui.theme.Typography
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
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = textFieldType.verticalAlignment
    ) {
        Box(modifier = Modifier.weight(1f)) {
            innerTextField()
            if (state == TextFieldState.Default) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 2.dp),
                    text = placeholder,
                    style = Typography.body2,
                    color = Placeholder,
                )
            }
        }
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