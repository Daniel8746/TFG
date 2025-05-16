package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.ui.features.components.ButtonWithLottie

@Composable
fun FinDePartidaPanel(
    puntosUsuario: Int,
    puntosMaquina: Int,
    onFinalizarBlackJack: () -> Unit,
    volverAtras: () -> Unit,
    reiniciarPartida: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            if (puntosUsuario > 21 || (puntosMaquina in puntosUsuario..21)) {
                "Has perdido"
            } else {
                "Has ganado"
            },
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Text(
            "Puntos Jugador: $puntosUsuario",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Text(
            "Puntos Cruppier: $puntosMaquina",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            ButtonWithLottie(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .padding(5.dp),
                text = "Salir",
                onClick = {
                    onFinalizarBlackJack()
                    volverAtras()
                }
            )

            ButtonWithLottie(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .padding(5.dp),
                text = "Reiniciar",
                onClick = {
                    onFinalizarBlackJack()
                    reiniciarPartida()
                }
            )
        }
    }
}