package com.maum.note.core.designSystem.component.section.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.designSystem.component.board.AuthorWithTime
import com.maum.note.core.designSystem.extension.modifier.singleClickable
import com.maum.note.core.model.board.Post
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.FilledIconDisabled
import com.maum.note.ui.theme.MainBackground
import com.maum.note.ui.theme.Placeholder
import com.maum.note.ui.theme.White
import java.time.LocalDateTime

@Composable
fun PostSection(
    modifier: Modifier = Modifier,
    post: Post,
    onClick: (() -> Unit)?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .singleClickable(enabled = onClick != null, onClick = { onClick?.invoke() })
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = post.title,
                style = AppTypography.body1_semiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = post.content,
                style = AppTypography.body1_regular,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AuthorWithTime(
                author = post.userNickname,
                isAnonymous = post.isAnonymous,
                createdAt = post.createdAt
            )
            IconWithNumber(
                painter = painterResource(R.drawable.ic_chat),
                number = post.commentCount
            )
        }
    }

}

@Composable
private fun IconWithNumber(
    modifier: Modifier = Modifier,
    painter: Painter,
    number: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painter,
            contentDescription = null,
            tint = FilledIconDisabled
        )

        Text(
            text = (number.takeIf { it < 100 } ?: "99+").toString(),
            style = AppTypography.body2_regular.copy(
                color = Placeholder
            )
        )
    }
}

@Preview
@Composable
private fun PostSectionPreview() {
    Column(
        modifier = Modifier.background(MainBackground),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PostSection(
            post = Post(
                id = "23",
                title = "심심한데",
                content = "OO이는 식목일을 맞이해 옥상 텃밭에 상추, 고추, 방울토마토를 심었어요. 또 여러 가지 자료들을 통해 따뜻한 봄에 볼 수 있는 동식물에 대해 알아보기도 했답니다. 봄이",
                userNickname = "ㅇㅇ",
                userId = "sdf",
                isAnonymous = true,
                commentCount = 10,
                createdAt = LocalDateTime.now()
            ),
            onClick = {}
        )

        PostSection(
            post = Post(
                id = "23",
                title = "심심한데",
                content = "OO이는 식목일을 맞이해 옥상 텃밭에 상추, 고추, 방울토마토를 심었어요. 또 여러 가지 자료들을 통해 따뜻한 봄에 볼 수 있는 동식물에 대해 알아보기도 했답니다. 봄이",
                userNickname = "ㅇㅇ",
                userId = "sdf",
                isAnonymous = false,
                commentCount = 10032342,
                createdAt = LocalDateTime.now()
            ),
            onClick = {}
        )
    }
}