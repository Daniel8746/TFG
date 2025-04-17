package com.pmdm.casino.ui.features.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldEmail
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldPassword
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.theme.CasinoTheme


@Composable
fun UsuarioPassword(
    modifier: Modifier,
    loginState: String,
    validacionLogin: Validacion,
    passwordState: String,
    validacionPassword: Validacion,
    recordarmeState: Boolean,
    isLoading: Boolean,
    onValueChangeLogin: (String) -> Unit,
    onValueChangePassword: (String) -> Unit,
    onCheckedChanged: (Boolean) -> Unit,
    onClickLogearse: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextFieldEmail(
            modifier = modifier,
            label = "Correo electrónico",
            emailState = loginState,
            validacionState = validacionLogin,
            onValueChange = onValueChangeLogin
        )

        OutlinedTextFieldPassword(
            modifier = modifier,
            label = "Contraseña",
            passwordState = passwordState,
            validacionState = validacionPassword,
            onValueChange = onValueChangePassword
        )

        CheckboxWithLabel(
            label = "Recuérdame",
            checkedState = recordarmeState,
            onStateChange = onCheckedChanged
        )

        ButtonWithLottie(
            text = "Iniciar sesión",
            isLoading = isLoading,
            onClick = onClickLogearse
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UsuarioPasswordTest() {

    var loginState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var recordarme by remember { mutableStateOf(false) }

    CasinoTheme {
        UsuarioPassword(
            modifier = Modifier.fillMaxWidth(),
            loginState = loginState,
            validacionLogin = object : Validacion {},
            passwordState = passwordState,
            validacionPassword = object : Validacion {},
            recordarmeState = recordarme,
            true,
            onValueChangeLogin = { loginState = it },
            onValueChangePassword = { passwordState = it },
            onCheckedChanged = { recordarme = !it },
            onClickLogearse = {}
        )
    }
}
