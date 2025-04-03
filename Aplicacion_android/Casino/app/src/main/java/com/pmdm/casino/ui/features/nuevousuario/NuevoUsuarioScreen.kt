package com.pmdm.casino.ui.features.nuevousuario

import TextLogin
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.ui.features.nuevousuario.components.NuevoUsuarioCreacion

@Composable
fun NuevoUsuarioScreen(
    nuevoUsuarioUiState: NuevoUsuarioUiState,
    validacionNuevoUsuarioUiState: ValidacionNuevoUsuarioUiState,
    nuevoUsuarioError: Boolean,
    isLoading: Boolean,
    onNuevoUsuarioEvent: (NuevoUsuarioEvent) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text = if (!nuevoUsuarioError) {
                """
                No se ha podido crear una cuenta con ese correo.
                Ese correo ya está registrado.
            """.trimIndent()
            } else {
                ""
            },
            color = Color.Red,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        NuevoUsuarioCreacion(
            modifier = Modifier.fillMaxWidth(),
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
            }
        )

        TextLogin(onClick = onNavigateToLogin)
    }
}