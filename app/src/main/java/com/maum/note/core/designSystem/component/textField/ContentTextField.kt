package com.maum.note.core.designSystem.component.textField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.util.textField.TextFieldType
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.SubText

@Composable
fun ContentTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    maxCount: Int? = null,
    caption: String? = null,
    placeholder: String,
    isFilled: Boolean = false,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    textFieldType: TextFieldType = TextFieldType.Multiple(),
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextField(
            modifier = textFieldModifier,
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            isFilled = isFilled,
            readOnly = readOnly,
            singleLine = singleLine,
            textFieldType = textFieldType,
            minLines = minLines,
            onTextLayout = onTextLayout,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            caption?.let {
                Text(
                    text = it,
                    style = AppTypography.body2_regular,
                    color = SubText
                )
            }
            Box(modifier =  Modifier.weight(1f))
            maxCount?.let {
                Text(
                    text = "${value.text.length}/$it",
                    style = AppTypography.body2_regular,
                    color = SubText
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ContentTextField(
        value = TextFieldValue(""),
        maxCount = 100,
        caption = "Caption",
        onValueChange = {},
        placeholder = "Placeholder",
    )
}