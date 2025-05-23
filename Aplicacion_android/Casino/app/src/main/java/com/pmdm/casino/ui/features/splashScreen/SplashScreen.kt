package com.pmdm.casino.ui.features.splashScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.components.AbrirDialogoNoApiRest
import com.pmdm.casino.ui.features.components.AbrirDialogoNoConexion

@Composable
fun SplashScreen(
    correo: String,
    errorApi: Boolean,
    reintentarConexion: Boolean,
    reiniciar: () -> Unit,
    onNavegarLogin: () -> Unit,
    onNavegarJuegos: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ruleta_girando))
            val logoAnimationState =
                animateLottieCompositionAsState(
                    composition = composition,
                    isPlaying = !reintentarConexion && !errorApi
                )
            LottieAnimation(
                composition = composition,
                progress = { logoAnimationState.progress }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { logoAnimationState.progress },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.Gray.copy(alpha = 0.3f)
            )

            if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying && !reintentarConexion && !errorApi) {
                LaunchedEffect(Unit) {
                    if (correo.isNotEmpty()) {
                        onNavegarJuegos()
                    } else {
                        onNavegarLogin()
                    }
                }
            }
        }
        
        if (reintentarConexion) {
            AbrirDialogoNoConexion {
                reiniciar()
            }
        } else if (errorApi) {
            AbrirDialogoNoApiRest()
        }
    }
}