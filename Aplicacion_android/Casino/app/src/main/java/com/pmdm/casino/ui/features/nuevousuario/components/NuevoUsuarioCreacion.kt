package com.pmdm.casino.ui.features.nuevousuario.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldEmail
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldName
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldPassword
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldPhone
import com.github.pmdmiesbalmis.components.validacion.Validacion

@Composable
fun NuevoUsuarioCreacion(
    modifier: Modifier,
    nombreState: String,
    validacionNombre: Validacion,
    apellidosState: String,
    validacionApellidos: Validacion,
    emailState: String,
    validacionEmail: Validacion,
    passwordState: String,
    validacionPassword: Validacion,
    telefonoState: String,
    validacionTelefono: Validacion,
    onValueChangeNombre: (String) -> Unit,
    onValueChangeApellidos: (String) -> Unit,
    onValueChangeEmail: (String) -> Unit,
    onValueChangePassword: (String) -> Unit,
    onValueChangeTelefono: (String) -> Unit,
    onClickNuevaCuenta: () -> Unit
) {
    OutlinedTextFieldName(
        modifier = modifier,
        nameState = nombreState,
        validacionState = validacionNombre,
        onValueChange = onValueChangeNombre
    )

    OutlinedTextFieldName(
        modifier = modifier,
        label = "Apellidos",
        nameState = apellidosState,
        validacionState = validacionApellidos,
        onValueChange = onValueChangeApellidos
    )

    OutlinedTextFieldEmail(
        modifier = modifier,
        emailState = emailState,
        validacionState = validacionEmail,
        onValueChange = onValueChangeEmail
    )

    OutlinedTextFieldPassword(
        modifier = modifier,
        label = "Contraseña",
        passwordState = passwordState,
        validacionState = validacionPassword,
        onValueChange = onValueChangePassword
    )

    OutlinedTextFieldPhone(
        modifier = modifier,
        telefonoState = telefonoState,
        validacionState = validacionTelefono,
        onValueChange = onValueChangeTelefono
    )

    Button(
        onClick = onClickNuevaCuenta,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Crear cuenta")
    }
}