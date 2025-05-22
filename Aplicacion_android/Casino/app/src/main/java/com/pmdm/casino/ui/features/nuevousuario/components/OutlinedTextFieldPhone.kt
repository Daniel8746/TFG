package com.pmdm.casino.ui.features.nuevousuario.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldWithErrorState
import com.github.pmdmiesbalmis.components.ui.icons.Filled
import com.github.pmdmiesbalmis.components.validacion.Validacion

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