package com.pmdm.casino.ui.features.nuevousuario.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldEmail
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldName
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldPassword
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldWithErrorState
import com.github.pmdmiesbalmis.components.ui.icons.Filled
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie

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
    isLoading: Boolean,
    onValueChangeNombre: (String) -> Unit,
    onValueChangeApellidos: (String) -> Unit,
    onValueChangeEmail: (String) -> Unit,
    onValueChangePassword: (String) -> Unit,
    onValueChangeTelefono: (String) -> Unit,
    onClickNuevaCuenta: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
            label = "Correo electrónico",
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
        
        ButtonWithLottie(
            text = "Crear cuenta",
            isLoading = isLoading,
            onClick = onClickNuevaCuenta
        )
    }
}

@Composable
fun OutlinedTextFieldPhone(
    modifier: Modifier = Modifier,
    label: String = "Teléfono",
    telefonoState: String,
    validacionState: Validacion,
    onValueChange: (String) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier,
        label = label,
        textoState = telefonoState,
        textoPista = "999999999",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        leadingIcon = {
            Icon(
                painter = Filled.getPhoneEnabledIcon(),
                contentDescription = "Teléfono"
            )
        },
        validacionState = validacionState,
        onValueChange = onValueChange
    )
}