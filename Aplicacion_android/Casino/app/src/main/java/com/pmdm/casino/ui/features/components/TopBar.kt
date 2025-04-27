package com.pmdm.casino.ui.features.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.ui.features.UsuarioCasinoUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    usuarioUiState: UsuarioCasinoUiState,
    volverAtras: (() -> Unit)? = null,
    onFinalizar: (() -> Unit)? = null
) {
    TopAppBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = {},
        navigationIcon = {
            if (volverAtras != null && onFinalizar != null) {
                IconButton(
                    onClick = {
                        volverAtras()
                        onFinalizar()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Flecha volver atrás"
                    )
                }
            }
        },
        actions = {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Nombre del usuario
                Text(
                    text = usuarioUiState.correo,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp
                )

                // Saldo del usuario
                Text(
                    text = "Saldo: ${usuarioUiState.saldo}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    fontSize = 16.sp
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer // Fondo suave
        ),
        expandedHeight = 80.dp
    )
}