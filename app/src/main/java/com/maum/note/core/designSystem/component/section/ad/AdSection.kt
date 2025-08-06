package com.maum.note.core.designSystem.component.section.ad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.maum.note.core.model.setting.AdContent
import com.maum.note.ui.theme.MainBackground

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AdSection(
    modifier: Modifier = Modifier,
    adContents: List<AdContent>,
    placeholderColor: Color = MainBackground,
    onClick: (AdContent) -> Unit,
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        pageCount = { pageCount }
    )

    if (adContents.isEmpty()) return
    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
        ) { pageIndex ->
            val actualIndex = pageIndex % adContents.size
            val content = adContents.getOrNull(actualIndex) ?: adContents.first()

            GlideImage(
                model = content.imageUrl,
                contentDescription = null,
                loading = placeholder(ColorPainter(placeholderColor)),
                failure = placeholder(ColorPainter(placeholderColor)),
                contentScale = ContentScale.FillWidth,
                transition = CrossFade,
                modifier = Modifier.fillMaxWidth().clickable { onClick(content) },
            )
        }
    }
}

@Preview
@Composable
private fun AdSectionPreview() {
    AdSection(
        modifier = Modifier,
        adContents = listOf(),
        onClick = {}
    )
}