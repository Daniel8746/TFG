package com.pmdm.casino.ui.features.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.github.pmdmiesbalmis.components.ui.composables.CheckboxWithLabel
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
    Column {
        OutlinedTextFieldEmail(
            modifier = modifier,
            label = "Login",
            emailState = loginState,
            validacionState = validacionLogin,
            onValueChange = onValueChangeLogin
        )

        OutlinedTextFieldPassword(
            modifier = modifier,
            label = "Password",
            passwordState = passwordState,
            validacionState = validacionPassword,
            onValueChange = onValueChangePassword
        )

        CheckboxWithLabel(
            label = "Recordarme",
            checkedState = recordarmeState,
            onStateChange = onCheckedChanged
        )

        Row {
            Button(
                onClick = onClickLogearse,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Login")

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                if (isLoading) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fichas_carga))
                    val progress = animateLottieCompositionAsState(composition = composition)

                    LottieAnimation(
                        composition = composition,
                        progress = { progress.progress },
                        modifier = Modifier.size(50.dp) // Ajusta el tamaño según lo necesario
                    )
                }
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
