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
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldReal
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.evaluarResultado
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoEvent
import java.math.BigDecimal

@Composable
fun FinDePartidaPanel(
    puntosUsuario: Int,
    puntosMaquina: Int,
    apuestaUsuario: BigDecimal,
    onFinalizarBlackJack: () -> Unit,
    volverAtras: () -> Unit,
    reiniciarPartida: () -> Unit,
    onUsuarioEvent: (UsuarioCasinoEvent) -> Unit,
    onValueApuestaChanged: (Double) -> Unit,
    setEstadoPartida: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            when (evaluarResultado(puntosUsuario = puntosUsuario, puntosMaquina = puntosMaquina)) {
                "Empate" -> {
                    onUsuarioEvent(UsuarioCasinoEvent.AumentarSaldo(apuestaUsuario))
                    "Empate"
                }

                "Perdido" -> "Has perdido"

                "Ganado" -> {
                    onUsuarioEvent(UsuarioCasinoEvent.AumentarSaldo(apuestaUsuario * BigDecimal(2)))
                    "Has ganado"
                }

                else -> ""
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

        OutlinedTextFieldReal(
            label = "",
            valorState = apuestaUsuario.toDouble(),
            validacionState = object : Validacion {},
            onValueChange = onValueApuestaChanged
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            ButtonWithLottie(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .padding(5.dp),
                text = "Salir",
                onClick = {
                    setEstadoPartida()
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