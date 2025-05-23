package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.Easing
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
    val offsetY =
        animacionDesdeArriba(isDesdeArriba = true, duration = 1000, easing = EaseInOutBounce)

    Image(
        painter = painterResource(R.drawable.fichasruleta),
        contentDescription = "Ficha cayendo con rebote",
        modifier = Modifier
            .offset { IntOffset(0, offsetY.value.roundToInt()) }
            .size(40.dp)
    )
}

@Composable
fun animacionDesdeArriba(
    isDesdeArriba: Boolean,
    duration: Int,
    easing: Easing,
    onOcultarFicha: (() -> Unit)? = null
): Animatable<Float, AnimationVector1D> {
    val windowInfo = LocalWindowInfo.current

    val screenHeightPx = remember(windowInfo) {
        windowInfo.containerSize.height.toFloat()
    }

    val offsetY =
        remember { Animatable(if (isDesdeArriba) -screenHeightPx else 0f) } // empieza fuera de pantalla

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = if (isDesdeArriba) 0f else -screenHeightPx,
            animationSpec = tween(
                durationMillis = duration,
                easing = easing
            )
        )

        if (onOcultarFicha != null) {
            onOcultarFicha()
        }
    }

    return offsetY
}

@Composable
fun UpingFicha(onOcultarFicha: () -> Unit) {
    val offsetY = animacionDesdeArriba(
        isDesdeArriba = false,
        duration = 600,
        easing = CubicBezierEasing(0.3f, 0f, 0.7f, 1f),
        onOcultarFicha = onOcultarFicha
    )

    Image(
        painter = painterResource(R.drawable.fichasruleta),
        contentDescription = "Ficha cayendo con rebote",
        modifier = Modifier
            .offset { IntOffset(0, offsetY.value.roundToInt()) }
            .size(40.dp)
    )
}