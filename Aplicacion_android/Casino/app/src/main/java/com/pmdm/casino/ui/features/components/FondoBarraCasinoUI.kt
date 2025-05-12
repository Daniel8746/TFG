package com.pmdm.casino.ui.features.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.UsuarioCasinoUiState

@Composable
fun FondoBarraCasinoUI(
    usuarioUiState: UsuarioCasinoUiState,
    reintentarConexion: Boolean,
    errorApi: Boolean,
    reiniciar: () -> Unit,
    volverAtras: (() -> Unit)? = null,
    onFinalizarJuego: (() -> Unit)? = null,
    composable: @Composable() (BoxScope.() -> Unit)
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopBar(
                usuarioUiState = usuarioUiState,
                volverAtras = volverAtras,
                onFinalizar = onFinalizarJuego
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Image(
                    painter = painterResource(R.drawable.imagenfondojuegos),
                    contentDescription = "Fondo juegos",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                if (errorApi) {
                    AbrirDialogoNoApiRest()
                } else if (reintentarConexion) {
                    AbrirDialogoNoConexion {
                        reiniciar()
                    }
                }

                composable()
            }
        }
    )
}