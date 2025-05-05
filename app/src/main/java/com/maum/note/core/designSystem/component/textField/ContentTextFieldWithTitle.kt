package com.maum.note.core.designSystem.component.textField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.SubText

@Composable
fun ContentTextFieldWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    maxCount: Int? = null,
    caption: String? = null,
    hint: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = AppTypography.body1_semiBold
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ContentTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder,
                maxCount = maxCount,
                caption = caption,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
            )

            hint?.let {
                Text(
                    text = it,
                    style = AppTypography.body2_regular.copy(
                        color = SubText,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ContentTextFieldWithTitle(
        modifier = Modifier,
        title = "정보 입력",
        value = TextFieldValue(""),
        onValueChange = {},
        placeholder = "내용을 입력하세요.",
        maxCount = 100,
        caption = "100자 이내로 입력해주세요.",
        hint = "- 작성 시기 (예: 5월)\n" +
                "- 계절감 (예: 따뜻한 햇살, 꽃이 피는 봄)\n" +
                "- 건강/안전 관련 이야기 (예: 감기 주의, 손 씻기)\n" +
                "- 부모교육 주제 (예: 친구 관계, 감정 표현)\n" +
                "- 예정된 행사 (예: 부모 상담, 현장학습)\n" +
                "- 사회 이슈나 계기 (예: 올림픽, 세계 책의 날)\n" +
                "- 기념일 (예: 어버이날, 부부의 날)",
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
    )

}