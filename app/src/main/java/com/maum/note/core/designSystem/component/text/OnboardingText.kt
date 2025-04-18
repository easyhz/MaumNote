package com.maum.note.core.designSystem.component.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.SubText
import com.maum.note.ui.theme.AppTypography

@Composable
fun OnboardingText(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = subTitle,
            style = AppTypography.body2,
            color = SubText
        )
        Text(
            text = title,
            style = AppTypography.semiBold20,
            color = MainText
        )
    }
}

@Preview
@Composable
private fun OnboardingTextPreview() {
    OnboardingText(
        title = "Onboarding Title",
        subTitle = "Onboarding Description",
    )
}