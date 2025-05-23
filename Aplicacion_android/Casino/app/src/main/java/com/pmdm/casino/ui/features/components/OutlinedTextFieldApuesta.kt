package com.pmdm.casino.ui.features.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldWithErrorState
import com.github.pmdmiesbalmis.components.validacion.Validacion
import java.math.BigDecimal
import java.util.Locale

@Composable
fun OutlinedTextFieldApuesta(
    modifier: Modifier = Modifier,
    label: String = "Cambiar apuesta",
    valorState: BigDecimal,
    numeroDecimales: Int = 2,
    validacionState: Validacion,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (BigDecimal) -> Unit
) {
    OutlinedTextFieldWithErrorState(
        modifier = modifier,
        label = label,
        textoState = "%.${numeroDecimales}f".format(Locale.US, valorState),
        validacionState = validacionState,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = keyboardActions,
        onValueChange = {
            if (it.isNotEmpty()) {
                if (it.matches(Regex("^[+-]?[0-9]+(\\.[0-9]+)?$")))
                    onValueChange(it.toBigDecimal())
            } else {
                onValueChange(0.toBigDecimal())
            }
        }
    )
}