package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.OutlinedTextFieldApuesta
import com.pmdm.casino.ui.features.ruleta.RuletaEvent
import java.math.BigDecimal

@Composable
fun VistaJugadorRuleta(
    apuestaUsuario: BigDecimal,
    saldo: BigDecimal,
    enabled: Boolean,
    onRuletaEvent: (RuletaEvent) -> Unit,
    onAumentarSaldoUsuario: () -> Unit,
    onBajarSaldoUsuario: () -> Unit
) {
    val validar = remember {
        object : Validacion {
            override val hayError: Boolean
                get() = apuestaUsuario > saldo
            override val mensajeError: String
                get() = "La apuesta no puede superar tu saldo"
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconoInteractivo(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                descripcion = "Flecha bajar precio",
                onEvent = { onRuletaEvent(RuletaEvent.FlechaBajarPulsado) }
            )

            OutlinedTextFieldApuesta(
                modifier = Modifier.background(
                    color = if (!isSystemInDarkTheme()) Color(
                        0xFFF5F5F5
                    ) else Color.Transparent
                ),
                valorState = apuestaUsuario,
                validacionState = validar,
                onValueChange = { onRuletaEvent(RuletaEvent.ApuestaChanged(it)) }
            )

            IconoInteractivo(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                descripcion = "Flecha subir precio",
                onEvent = { onRuletaEvent(RuletaEvent.FlechaSubirPulsado) },
            )
        }

        Row {
            ButtonWithLottie(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .padding(5.dp),
                text = "Apostar",
                onClick = {
                    onRuletaEvent(RuletaEvent.Apostar(apuestaUsuario))
                    onBajarSaldoUsuario()
                },
                enabled = enabled,
            )

            ButtonWithLottie(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .padding(5.dp),
                text = "Quitar apuesta",
                onClick = {
                    onAumentarSaldoUsuario()
                    onRuletaEvent(RuletaEvent.QuitarApuesta)
                },
                enabled = enabled,
            )
        }
    }
}