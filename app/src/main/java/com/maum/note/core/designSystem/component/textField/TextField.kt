package com.maum.note.core.designSystem.component.textField

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.maum.note.core.designSystem.util.textField.TextFieldContainer
import com.maum.note.core.designSystem.util.textField.TextFieldType
import com.maum.note.core.designSystem.util.textField.getTextFieldState
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.AppTypography

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
    val state = getTextFieldState(text = value.text, isFilled = isFilled)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = AppTypography.body1.copy(
            color = MainText,
        ),
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        decorationBox = { innerTextField ->
            TextFieldContainer(
                state = state,
                placeholder = placeholder,
                innerTextField = innerTextField,
                textFieldType = textFieldType
            )
        }
    )
}

@Preview
@Composable
private fun ContentTextFieldPreview() {
    TextField(
        value = TextFieldValue(""),
        onValueChange = {},
        placeholder = "Placeholder",
        isFilled = false,
        singleLine = false,
        textFieldType = TextFieldType.Multiple(),
        minLines = 1,
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
    )
}