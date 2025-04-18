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
import com.maum.note.ui.theme.Typography

@Composable
fun ContentTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    isFilled: Boolean,
    readOnly: Boolean = false,
    singleLine: Boolean,
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
        textStyle = Typography.body2.copy(
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
    ContentTextField(
        value = TextFieldValue("안녕하세요.\uD83D\uDE0A\n" +
                "\n" +
                "'산에 피어도 꽃이고 들에 피어도 꽃이고 길가에 피어도 꽃이고 모두 다 꽃이야~\uD83C\uDFB5' 동요 [모두 다 꽃이야] 노래를 아시나요?\n" +
                "꽃들은 색깔도, 모양도, 크기도 모두 저마다 조금씩 다릅니다. 이름 없이 피어도, 아무 데나 피어도, 생긴 대로 피어도 모두 한 송이의 꽃이지요.\uD83C\uDF39\n" +
                "다솜이들도 마찬가지입니다. 모두가 자신만의 색깔로, 서로 다르더라도 함께 살아갈 때 아이들은 함께 사는 삶의 가치를 배우게 될 것입니다.\uD83D\uDE0E\n" +
                "\n" +
                "다솜반 교실에는 어느덧 다솜이들의 웃음소리가 가득합니다. 가만히 귀를 기울이면, 작고 소중한 세상들이 서로 만나 더 큰 세상을 만들어 나가고 있지요.\uD83E\uDD70 다솜이들 사이에 다툼이 없을 수는 없습니다. 양보가 어렵고, 마음을 말로 예쁘게 표현하기는 더 어려우니까요. 다솜이들이 친구를 대하기 어려워한다면, 어떻게 표현하면 좋을지 함께 여러 상황을 두고 연습해 보세요. 부모님께서는 다솜이들의 친구가 될 기회가 되고 다솜이들에게는 중요한 연습이 될 것입니다.\uD83D\uDC4F"),
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