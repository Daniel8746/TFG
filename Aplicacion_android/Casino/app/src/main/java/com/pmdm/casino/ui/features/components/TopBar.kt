package com.pmdm.casino.ui.features.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pmdm.casino.ui.features.UsuarioCasinoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(usuarioUiState: UsuarioCasinoUiState) {
    TopAppBar(title = {}, actions = {
        // Nombre del usuario
        Text(
            text = usuarioUiState.correo,
            modifier = Modifier.align(Alignment.CenterVertically),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.width(16.dp)) // Espaciado entre los elementos

        // Saldo del usuario
        Text(
            text = "Saldo: ${usuarioUiState.saldo}",
            modifier = Modifier.align(Alignment.CenterVertically),
            textAlign = TextAlign.End // Alineado a la derecha
        )
    })
}