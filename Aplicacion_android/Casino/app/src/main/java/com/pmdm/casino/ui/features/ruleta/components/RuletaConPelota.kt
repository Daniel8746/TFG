package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.ui.features.ruleta.ApuestasUiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun RuletaConPelota(
    items: List<ApuestasUiState>,
    numeroGanador: Int,
    size: Dp = 350.dp,
    onAnimacionAcabada: () -> Unit
) {
    val degreesPerItem = remember { 360f / items.size.toFloat() }
    val offsetRuleta = remember { Animatable(0f) }
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val screenHeightDp = with(density) { windowInfo.containerSize.height.toDp() }
    val radiusFromCenter = with(density) { (size.toPx() / 2) * 0.7f }
    val offsetPelotaY = remember { Animatable(-screenHeightDp, Dp.VectorConverter) }
    val offsetPelotaRotation = remember { Animatable(0f) }
    var mostrarNumeroGanador by remember { mutableStateOf(false) }

    LaunchedEffect(numeroGanador) {
        if (numeroGanador != -1) {
            val indexGanador = items.indexOfFirst { it.valor == numeroGanador.toString() }
            val anguloGanador = indexGanador * degreesPerItem
            val vueltas = 3 * 360f
            val anguloInicialPelota = Random.nextFloat() * 360f

            offsetRuleta.snapTo(0f)
            offsetPelotaRotation.snapTo(anguloInicialPelota)

            // 1. Pelota cae y ruleta da 1 vuelta rápida (en paralelo)
            coroutineScope {
                launch {
                    offsetPelotaY.animateTo(
                        targetValue = 0.dp,
                        animationSpec = tween(800, easing = EaseInOutBounce)
                    )
                }
                launch {
                    offsetRuleta.animateTo(
                        targetValue = 360f,
                        animationSpec = tween(800, easing = LinearEasing)
                    )
                }
            }

            val rotacionFinalRuleta = vueltas - anguloGanador
            val rotacionFinalPelota = vueltas - anguloInicialPelota - anguloGanador

            // 2. Ruleta gira lento hasta rotacionFinal y pelota sincronizada
            coroutineScope {
                launch {
                    offsetRuleta.animateTo(
                        targetValue = rotacionFinalRuleta,
                        animationSpec = tween(4000, easing = EaseOutQuad)
                    )
                }
                launch {
                    offsetPelotaRotation.animateTo(
                        targetValue = rotacionFinalPelota,
                        animationSpec = tween(4000, easing = EaseOutQuad)
                    )
                }
            }

            mostrarNumeroGanador = true
            delay(2000)

            // 3. Pelota sube (desaparece)
            offsetPelotaY.animateTo(
                targetValue = -screenHeightDp,
                animationSpec = tween(600, easing = CubicBezierEasing(0.3f, 0f, 0.7f, 1f))
            )

            mostrarNumeroGanador = false
            onAnimacionAcabada()
        }
    }

    Box(
        modifier = Modifier.size(size), contentAlignment = Alignment.Center
    ) {
        RuletaCompleta(
            items = items,
            degreesPerItem = degreesPerItem,
            rotacionValue = offsetRuleta.value,
            size = size
        )

        // Dibuja el hub central de la ruleta si está habilitado
        Box(
            modifier = Modifier
                .size(size / 6)
                .background(
                    color = Color(0xFF555555),
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .shadow(elevation = 4.dp, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (mostrarNumeroGanador) {
                Text(
                    text = numeroGanador.toString(),
                    fontSize = 25.sp,
                    color = Color.Black,
                    modifier = Modifier.background(
                        color = Color.Yellow,
                        shape = CircleShape
                    ),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Pelota(
            offsetY = offsetPelotaY.value,
            rotacionValue = offsetPelotaRotation.value,
            size = 10.dp,
            radiusFromCenter = radiusFromCenter
        )
    }
}

@Composable
fun RuletaCompleta(
    items: List<ApuestasUiState>,
    degreesPerItem: Float,
    rotacionValue: Float,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .rotate(rotacionValue)
    ) {
        items.forEachIndexed { index, item ->
            Casilla(
                modifier = Modifier.rotate(degrees = degreesPerItem * index),
                size = size,
                brush = SolidColor(item.color),
                degree = degreesPerItem
            ) {
                Text(
                    text = item.valor,
                    color = if (item.color == Color.Black) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
fun Casilla(
    modifier: Modifier = Modifier,
    size: Dp,
    brush: Brush,
    degree: Float,
    content: @Composable () -> Unit
) {
    // Dibuja el sector de la ruleta (arco de color correspondiente al número)
    Box(
        modifier = modifier
            .size(size)
            .drawBehind {
                drawArc(
                    brush = brush,
                    startAngle = -90f - (degree / 2),
                    sweepAngle = degree,
                    useCenter = true
                )
            }
    ) {
        // Muestra el número centrado en la parte superior del sector
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        ) {
            content()
        }
    }
}

@Composable
fun Pelota(
    offsetY: Dp,
    rotacionValue: Float,
    size: Dp,
    radiusFromCenter: Float = 150f // distancia desde el centro para que no esté encima del hub
) {
    Box(
        modifier = Modifier
            .graphicsLayer {
                rotationZ = rotacionValue // Gira la pelota alrededor del centro
            }
            .offset(y = offsetY) // Anima caída / subida
            .size((radiusFromCenter * 2).dp), // Define el "círculo" donde orbita la pelota
        contentAlignment = Alignment.TopCenter // Pelota al borde superior del círculo
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(Color.White, CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
                .shadow(2.dp, CircleShape)
        )
    }
}