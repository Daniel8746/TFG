package com.pmdm.casino.ui.features.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldEmail
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldPassword
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.R
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
            label = "Recordarme",
            checkedState = recordarmeState,
            onStateChange = onCheckedChanged
        )

        Button(
            onClick = onClickLogearse,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isLoading) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fichas_carga))
                val progress by animateLottieCompositionAsState(composition)
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(36.dp)
                )
            } else {
                Text("Iniciar sesión", fontSize = 16.sp)
            }
        }
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
