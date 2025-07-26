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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maum.note.R
import com.maum.note.core.designSystem.component.board.AuthorWithTime
import com.maum.note.core.designSystem.extension.modifier.noRippleClickable
import com.maum.note.core.model.board.Comment
import com.maum.note.ui.theme.AppTypography
import com.maum.note.ui.theme.MainText
import com.maum.note.ui.theme.Placeholder
import com.maum.note.ui.theme.White
import java.time.LocalDateTime

@Composable
fun CommentSection(
    modifier: Modifier = Modifier,
    comment: Comment,
    onClickMore: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .padding(bottom = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AuthorWithTime(
                author = comment.userNickname,
                isAnonymous = comment.isAnonymous,
                createdAt = comment.createdAt,
            )

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .noRippleClickable(onClick = onClickMore),
                painter = painterResource(R.drawable.ic_more_small_trailing),
                contentDescription = null,
                tint = Placeholder
            )
        }

        Text(
            modifier = Modifier.padding(end = 20.dp),
            text = comment.content,
            style = AppTypography.body1_regular.copy(
                color = MainText
            )
        )
    }
}

@Preview
@Composable
private fun CommentSectionPreview() {
    CommentSection(
        comment = Comment(
            id = "123",
            postId = "123",
            parentId = null,
            content = "사적으로 아무이유 없이 만나자고 할 수 있는 친구 몇 몇명임? 학교쪽이고 남자예요",
            userNickname = "춤추는",
            userId = "213",
            isAnonymous = true,
            createdAt = LocalDateTime.now(),
        ),
        onClickMore = { }
    )
}