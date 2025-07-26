package com.maum.note.core.designSystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Primary
import com.maum.note.ui.theme.White


@Composable
fun BoardFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        containerColor = Primary,
        contentColor = MainBackground
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_add_small), contentDescription = "")
            Text(
                text = stringResource(R.string.add_board),
                style = AppTypography.body1.copy(
                    color = White
                )
            )
        }
    }
}

@Preview
@Composable
private fun BoardFloatingActionButtonPreview() {
    BoardFloatingActionButton() {

    }
}