package com.pmdm.casino.ui.features.blackJack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.blackJack.components.CartaScreen
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.TopBar

@Composable
fun BlackJackScreen(
    usuarioUiState: UsuarioCasinoUiState,
    listadoCartas: List<CartaUiState>,
    cartaNueva: CartaUiState?,
    onBlackJackEvent: (BlackJackEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            usuarioUiState = usuarioUiState
        )

        Image(
            painter = painterResource(R.drawable.imagenfondojuegos),
            contentDescription = "Fondo juegos",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Row {
            ButtonWithLottie(
                text = "Pedir carta",
                isLoading = false,
                onClick = {
                    onBlackJackEvent(BlackJackEvent.OnPedirCarta)
                }
            )

            LazyRow {
                items(listadoCartas) {
                    CartaScreen(it)
                }
            }

            if (cartaNueva != null) {
                CartaScreen(
                    cartaNueva
                )
            }

            ButtonWithLottie(
                text = "Plantarse",
                isLoading = false,
                onClick = {
                    onBlackJackEvent(BlackJackEvent.OnPlantarse)
                }
            )
        }
    }
}