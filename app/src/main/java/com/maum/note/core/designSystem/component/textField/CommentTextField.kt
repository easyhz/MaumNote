package com.maum.note.core.designSystem.component.textField

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.designSystem.component.button.CommentAnonymousCheckButton
import com.maum.note.core.designSystem.extension.modifier.singleClickable
import com.maum.note.core.designSystem.util.textField.TextFieldState
import com.maum.note.core.designSystem.util.textField.getTextFieldState
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Placeholder
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.SubBackground
import com.maum.note.ui.theme.SubText
import com.maum.note.ui.theme.White

enum class AlignBias(
    val value: Float,
) {
    CENTER(
        value = 0f
    ), BOTTOM(
        value = 1f
    )
}

@Composable
fun CommentTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isAnonymous: Boolean,
    onClickAnonymous: () -> Unit,
    onClickSend: () -> Unit
) {
    val textColor by animateColorAsState(targetValue = if (isAnonymous) Primary else SubText)
    val state = getTextFieldState(text = value.text, isFilled = false)
    var shouldAlignCenter by remember { mutableStateOf(true) }
    var targetBias = if (shouldAlignCenter) AlignBias.CENTER.value else AlignBias.BOTTOM.value

    val animatedBias by animateFloatAsState(
        targetValue = targetBias,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing),
    )

    Box(
        modifier = modifier
            .background(White)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SubBackground)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = BiasAlignment.Vertical(animatedBias)
        ) {
            CommentAnonymousCheckButton(
                modifier = Modifier,
                isChecked = isAnonymous,
                textColor = textColor,
                onClick = onClickAnonymous
            )

            BasicTextField(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = onValueChange,
                textStyle = AppTypography.body1_regular.copy(
                    color = MainText,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    )
                ),
                onTextLayout = { result ->
                    val newLineCount = result.lineCount
                    shouldAlignCenter = newLineCount <= 1
                },
                readOnly = false,
                singleLine = false,
                maxLines = 4,
                minLines = 1,
                decorationBox = { innerTextField ->
                    TextFieldContainerContent(
                        state = state,
                        placeholder = stringResource(R.string.board_detail_comment_placeholder),
                        innerTextField = innerTextField,
                    )
                }
            )

            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Primary)
                    .singleClickable(onClick = onClickSend)
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_up),
                    contentDescription = null,
                    tint = Color.White
                )
            }


        }
    }
}

@Composable
private fun TextFieldContainerContent(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String,
    innerTextField: @Composable () -> Unit
) {
    Box(
        modifier = modifier.heightIn(max = 100.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (state == TextFieldState.Default) {
            Text(
                modifier = Modifier.offset(x = 2.dp),
                text = placeholder,
                style = AppTypography.body2_regular.copy(
                    color = Placeholder,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    )
                ),
            )
        }
        innerTextField()
    }
}

@Preview
@Composable
private fun CommentTextFieldPreview() {

    CommentTextField(
        value = TextFieldValue(""),
        onValueChange = { },
        isAnonymous = false,
        onClickAnonymous = { },
        onClickSend = { }
    )
}