package com.pmdm.casino.ui.features.nuevousuario

import TextLogin
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.components.AbrirDialogoNoConexion
import com.pmdm.casino.ui.features.nuevousuario.components.NuevoUsuarioCreacion

@Composable
fun NuevoUsuarioScreen(
    nuevoUsuarioUiState: NuevoUsuarioUiState,
    validacionNuevoUsuarioUiState: ValidacionNuevoUsuarioUiState,
    nuevoUsuarioError: Boolean,
    isLoading: Boolean,
    reintentarConexion: Boolean,
    onNuevoUsuarioEvent: (NuevoUsuarioEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    reiniciar: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.imagenfondousuario),
            contentDescription = "Imagen fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            if (!nuevoUsuarioError) {
                Text(
                    text = """No se ha podido crear una cuenta con ese correo. Ese correo ya está registrado."""
                        .trimIndent(),
                    color = Color(0xFFFF6B6B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            NuevoUsuarioCreacion(modifier = Modifier.fillMaxWidth(),
                nombreState = nuevoUsuarioUiState.nombre,
                validacionNombre = validacionNuevoUsuarioUiState.validacionNombre,
                apellidosState = nuevoUsuarioUiState.apellidos,
                validacionApellidos = validacionNuevoUsuarioUiState.validacionApellidos,
                emailState = nuevoUsuarioUiState.correo,
                validacionEmail = validacionNuevoUsuarioUiState.validacionCorreo,
                passwordState = nuevoUsuarioUiState.password,
                validacionPassword = validacionNuevoUsuarioUiState.validacionPassword,
                telefonoState = nuevoUsuarioUiState.telefono,
                validacionTelefono = validacionNuevoUsuarioUiState.validacionTelefono,
                isLoading = isLoading,
                onValueChangeNombre = { onNuevoUsuarioEvent(NuevoUsuarioEvent.NombreChanged(it)) },
                onValueChangeApellidos = { onNuevoUsuarioEvent(NuevoUsuarioEvent.ApellidosChanged(it)) },
                onValueChangeEmail = { onNuevoUsuarioEvent(NuevoUsuarioEvent.CorreoChanged(it)) },
                onValueChangePassword = { onNuevoUsuarioEvent(NuevoUsuarioEvent.PasswordChanged(it)) },
                onValueChangeTelefono = { onNuevoUsuarioEvent(NuevoUsuarioEvent.TelefonoChanged(it)) },
                onClickNuevaCuenta = {
                    onNuevoUsuarioEvent(
                        NuevoUsuarioEvent.OnClickNuevoUsuario(
                            onNavigateToLogin
                        )
                    )
                })

            Spacer(Modifier.height(24.dp))

            TextLogin(onClick = onNavigateToLogin, color = Color.Yellow)
        }

        if (reintentarConexion) {
            AbrirDialogoNoConexion {
                reiniciar()
            }
        }
    }
}