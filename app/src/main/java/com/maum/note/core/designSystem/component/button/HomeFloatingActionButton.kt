package com.maum.note.core.designSystem.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Primary


@Composable
fun HomeFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(56.dp),
        shape = CircleShape,
        containerColor = Primary,
        contentColor = MainBackground
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_add_small), contentDescription = "")
    }
}

@Preview
@Composable
private fun HomeFloatingActionButtonPreview() {
    HomeFloatingActionButton() {

    }
}