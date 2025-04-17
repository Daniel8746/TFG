package com.pmdm.casino.ui.features.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pmdm.casino.ui.features.UsuarioCasinoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(usuarioUiState: UsuarioCasinoUiState) {
    TopAppBar(
        title = {},
        actions = {
            // Nombre del usuario
            Text(
                text = usuarioUiState.correo,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Saldo del usuario
            Text(
                text = "Saldo: ${usuarioUiState.saldo}",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer // Fondo suave
        )
    )
}