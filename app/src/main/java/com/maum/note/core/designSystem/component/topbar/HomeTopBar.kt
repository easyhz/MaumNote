package com.maum.note.core.designSystem.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.designSystem.extension.circleClickable
import com.maum.note.ui.theme.Typography


@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onClickSetting: () -> Unit,
) {
    Row(
        modifier = modifier.height(52.dp).fillMaxWidth().padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.widthIn(max = 200.dp),
                text = stringResource(R.string.app_name),
                style = Typography.h2_semiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Icon(
            modifier = Modifier.size(32.dp).circleClickable(
                onClick = onClickSetting
            ),
            painter = painterResource(R.drawable.ic_setting),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    HomeTopBar(

    ) {

    }
}