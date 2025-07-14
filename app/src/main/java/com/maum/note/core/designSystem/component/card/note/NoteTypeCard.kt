package com.maum.note.core.designSystem.component.card.note

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.core.designSystem.extension.modifier.dropShadow
import com.maum.note.core.designSystem.extension.modifier.singleClickable
import com.maum.note.core.model.note.NoteType
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.PrimaryBackground
import com.maum.note.ui.theme.White

@Composable
fun NoteTypeCard(
    modifier: Modifier = Modifier,
    noteType: NoteType,
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor by getBackgroundColor(isChecked)
    val contentColor by getContentColor(isChecked)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .dropShadow(
                shape = RoundedCornerShape(12.dp),
                blur = 4.dp,
                color = Color(0xFF232323).copy(alpha = 0.15f),
            )
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .singleClickable { onClick() }
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = stringResource(noteType.title),
            style = AppTypography.heading5_semiBold,
            color = contentColor,
        )
    }
}

@Composable
private fun getBackgroundColor(
    isChecked: Boolean,
): State<Color> {
    return animateColorAsState(
        targetValue = if (isChecked) {
            PrimaryBackground
        } else {
            White
        }, label = ""
    )
}

@Composable
private fun getContentColor(
    isChecked: Boolean,
): State<Color> {
    return animateColorAsState(
        targetValue = if (isChecked) {
            Primary
        } else {
            MainText
        }, label = ""
    )
}


@Preview
@Composable
private fun Preview() {
    Column(
        modifier =  Modifier.background(MainBackground).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NoteTypeCard(
            noteType = NoteType.LETTER_GREETING,
            isChecked = true,
            onClick = { }
        )
        NoteTypeCard(
            noteType = NoteType.PLAY_CONTEXT,
            isChecked = false,
            onClick = {  }
        )
    }
}