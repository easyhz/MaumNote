package com.maum.note.core.designSystem.component.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.SubText


@Composable
fun OnboardingInformation(
    modifier: Modifier = Modifier,
    informationType: OnboardingInformationType,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = informationType.iconId),
            contentDescription = informationType.iconId.toString(),
            tint = Primary
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(informationType.titleId),
                style = AppTypography.body1,
                color = MainText
            )
            Text(
                text = stringResource(informationType.contentId),
                style = AppTypography.body2_regular,
                color = SubText
            )
        }
    }
}

enum class OnboardingInformationType(
    @StringRes val titleId: Int,
    @StringRes val contentId: Int,
    @DrawableRes val iconId: Int
) {
    NOTICE(
        titleId = R.string.onboarding_start_information_notice_title,
        contentId = R.string.onboarding_start_information_notice_content,
        iconId = R.drawable.ic_pen
    ),
    TONE(
        titleId = R.string.onboarding_start_information_tone_title,
        contentId = R.string.onboarding_start_information_tone_content,
        iconId = R.drawable.ic_full_comment
    ),
    STUDENT(
        titleId = R.string.onboarding_start_information_student_title,
        contentId = R.string.onboarding_start_information_student_content,
        iconId = R.drawable.ic_people
    )
}