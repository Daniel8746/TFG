package com.pmdm.casino.ui.features.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pmdm.casino.R

@Composable
fun ButtonWithLottie(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isLoading) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fichas_carga))
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(36.dp)
            )
        } else {
            Text(text, fontSize = 16.sp)
        }
    }
}