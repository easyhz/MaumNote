package com.maum.note.core.designSystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.designSystem.extension.modifier.circleClickable
import com.maum.note.core.designSystem.extension.modifier.dropShadow
import com.maum.note.ui.theme.White
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.NoteBackground
import com.maum.note.ui.theme.SubText
import com.maum.note.ui.theme.AppTypography

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    content: String,
    date: String,
    color: Color = NoteBackground,
    onClick: () -> Unit,
    onClickCopy: () -> Unit,
) {

    Column(
        modifier = modifier
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                blur = 4.dp,
            )
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(color)
                .padding(8.dp)
        ) {
            Text(
                text = content,
                style = AppTypography.body1.copy(
                    color = MainText,
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier.height(28.dp).padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = date,
                style = AppTypography.body1.copy(
                    color = SubText,
                ),
            )
            Icon(
                modifier = Modifier.circleClickable { onClickCopy() },
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null,
                tint = SubText,
            )
        }
    }
}

@Preview
@Composable
private fun NoteCardPreview() {
    NoteCard(
        modifier = Modifier
            .width(180.dp)
            .height(196.dp),
        content = "안녕하세요.\n" +
                "오늘 OO이는 놀이에 집중하다 보니 화장실 신호를 놓쳐 바지에 소변 실수가 있었습니다.\n" +
                "당황한 모습이 보였지만 교사와 함께 차분히 옷을 갈아입고 안정된 모습으로 활동을 이어갔습니다.\n" +
                "앞으로도 아이가 제때 화장실에 갈 수 있도록 꾸준히 신호를 인지하고 표현하는 연습을 함께 도와주겠습니다.",
        date = "2025. 4. 15.",
        color = Color(0xFFE7FAED),
        onClick = { },
    ) { }
}