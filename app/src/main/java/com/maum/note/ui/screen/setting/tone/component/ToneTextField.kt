package com.maum.note.ui.screen.setting.tone.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.component.textField.ContentTextField
import com.maum.note.core.model.note.NoteType
import com.maum.note.ui.theme.AppTypography

@Composable
fun ToneTextField(
    modifier: Modifier = Modifier,
    noteType: NoteType,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = noteType.title),
            style = AppTypography.body1_semiBold
        )

        ContentTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = stringResource(id = noteType.placeholder),
            maxCount = 100,
            caption = stringResource(id = noteType.caption),
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ToneTextField(
        modifier = Modifier,
        noteType = NoteType.LETTER_GREETING,
        value = TextFieldValue(""),
        onValueChange = {},
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
    )

}