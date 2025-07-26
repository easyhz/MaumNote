package com.maum.note.core.designSystem.component.section.ad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.maum.note.core.model.setting.BoardAdContent
import com.maum.note.ui.theme.MainBackground

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BoardAdSection(
    modifier: Modifier = Modifier,
    boardAdContents: List<BoardAdContent>,
    onClick: (BoardAdContent) -> Unit,
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        pageCount = { pageCount }
    )

    if (boardAdContents.isEmpty()) return
    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
        ) { pageIndex ->
            val actualIndex = pageIndex % boardAdContents.size
            val content = boardAdContents.getOrNull(actualIndex) ?: boardAdContents.first()

            GlideImage(
                model = content.imageUrl,
                contentDescription = null,
                loading = placeholder(ColorPainter(MainBackground)),
                failure = placeholder(ColorPainter(MainBackground)),
                contentScale = ContentScale.FillWidth,
                transition = CrossFade,
                modifier = Modifier.fillMaxWidth().clickable { onClick(content) }
            )
        }
    }
}

@Preview
@Composable
private fun BoardAdSectionPreview() {
    BoardAdSection(
        modifier = Modifier,
        boardAdContents = listOf(),
        onClick = {}
    )
}