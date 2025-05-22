package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.OutlinedTextFieldApuesta
import com.pmdm.casino.ui.features.evaluarResultado
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoEvent
import java.math.BigDecimal

@Composable
fun FinDePartidaPanel(
    saldoUsuario: BigDecimal,
    puntosUsuario: Int,
    puntosMaquina: Int,
    apuestaUsuario: BigDecimal,
    onFinalizarBlackJack: () -> Unit,
    volverAtras: () -> Unit,
    reiniciarPartida: () -> Unit,
    onUsuarioEvent: (UsuarioCasinoEvent) -> Unit,
    onValueApuestaChanged: (BigDecimal) -> Unit,
    setEstadoPartida: () -> Unit
) {
    var aumentarSaldoPrimeraVez by remember { mutableStateOf(true) }

    val validar = remember {
        object : Validacion {
            override val hayError: Boolean
                get() = apuestaUsuario > saldoUsuario
            override val mensajeError: String
                get() = "La apuesta no puede superar tu saldo"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            when (evaluarResultado(puntosUsuario = puntosUsuario, puntosMaquina = puntosMaquina)) {
                "Empate" -> {
                    if (aumentarSaldoPrimeraVez) {
                        onUsuarioEvent(UsuarioCasinoEvent.AumentarSaldo(apuestaUsuario))
                        aumentarSaldoPrimeraVez = false
                    }

                    "Empate"
                }

                "Perdido" -> "Has perdido"

                "Ganado" -> {
                    if (aumentarSaldoPrimeraVez) {
                        onUsuarioEvent(
                            UsuarioCasinoEvent.AumentarSaldo(
                                apuestaUsuario * 2.toBigDecimal()
                            )
                        )
                        aumentarSaldoPrimeraVez = false
                    }

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

        OutlinedTextFieldApuesta(
            modifier = Modifier
                .background(color = if (!isSystemInDarkTheme()) Color(0xFFF5F5F5) else Color.Transparent),
            valorState = apuestaUsuario,
            validacionState = validar,
            onValueChange = onValueApuestaChanged,
            numeroDecimales = 2
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
                },
                enabled = !validar.hayError
            )
        }
    }
}