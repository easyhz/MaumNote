package com.maum.note.core.designSystem.component.loading

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.maum.note.R
import com.maum.note.ui.theme.Primary

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    width: Dp = (LocalConfiguration.current.screenWidthDp / 4).dp,
    height: Dp = (LocalConfiguration.current.screenWidthDp / 4).dp,
) {
    val focusManager = LocalFocusManager.current

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val  dynamicProperties  = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                Primary.hashCode(),
                BlendModeCompat.SRC_ATOP
            ),
            keyPath = arrayOf( "**" )
        )
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isLoading
    )

    LaunchedEffect(isLoading) {
        focusManager.clearFocus()
    }
    LottieAnimation(
        modifier = modifier
            .width(width)
            .height(height),
        composition = composition,
        progress = { progress },
        dynamicProperties = dynamicProperties,
        contentScale = ContentScale.FillWidth
    )
}


@Preview
@Composable
private fun LoadingIndicatorPreview() {
    LoadingIndicator(isLoading = true, width = 420.dp, height = 120.dp)
}