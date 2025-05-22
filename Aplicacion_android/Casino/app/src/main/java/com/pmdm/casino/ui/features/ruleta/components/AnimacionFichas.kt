package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pmdm.casino.R
import kotlin.math.roundToInt

@Composable
fun FallingFicha() {
    val windowInfo = LocalWindowInfo.current

    val screenHeightPx = remember(windowInfo) {
        windowInfo.containerSize.height.toFloat()
    }

    val offsetY = remember { Animatable(-screenHeightPx) } // empieza fuera de pantalla

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseInOutBounce // rebote + aceleración
            )
        )
    }

    Image(
        painter = painterResource(R.drawable.fichasruleta),
        contentDescription = "Ficha cayendo con rebote",
        modifier = Modifier
            .offset { IntOffset(0, offsetY.value.roundToInt()) }
            .size(40.dp)
    )
}

@Composable
fun UpingFicha(onOcultarFicha: () -> Unit) {
    val windowInfo = LocalWindowInfo.current

    val screenHeightPx = remember(windowInfo) {
        windowInfo.containerSize.height.toFloat()
    }

    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = -screenHeightPx,
            animationSpec = tween(
                durationMillis = 600,
                easing = CubicBezierEasing(0.3f, 0f, 0.7f, 1f)
            )
        )

        onOcultarFicha()
    }

    Image(
        painter = painterResource(R.drawable.fichasruleta),
        contentDescription = "Ficha cayendo con rebote",
        modifier = Modifier
            .offset { IntOffset(0, offsetY.value.roundToInt()) }
            .size(40.dp)
    )
}