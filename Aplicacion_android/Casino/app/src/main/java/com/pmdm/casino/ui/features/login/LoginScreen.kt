package com.pmdm.casino.ui.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.casino.ui.features.login.components.CircularImageFromResource
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.login.components.TextNewAccount
import com.pmdm.casino.ui.theme.CasinoTheme
import com.pmdm.casino.ui.theme.Purple40
import com.pmdm.casino.ui.features.login.components.UsuarioPassword
import java.math.BigDecimal


@Composable
fun LoginScreen(
    usuarioUiState: LoginUiState,
    validacionLoginUiState: ValidacionLoginUiState,
    loginErroneo: Boolean,
    isLoading: Boolean,
    onLoginEvent: (LoginEvent) -> Unit,
    onNavigateToCasino: ((correo: String, saldo: BigDecimal) -> Unit)? = null,
    onNavigateToNuevaCuenta: () -> Unit
) {
    var recordarmeState by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    ) {
        CircularImageFromResource(
            idImageResource = R.drawable.login, contentDescription = "Imagen Login"
        )

        Text(
            text = if (loginErroneo) {
                """
                    No hemos podido encontrar una cuenta con esos datos. 
                    Verifica tu correo electrónico o contraseña e inténtalo nuevamente.
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

        UsuarioPassword(modifier = Modifier.fillMaxWidth(),
            loginState = usuarioUiState.login,
            passwordState = usuarioUiState.password,
            validacionLogin = validacionLoginUiState.validacionLogin,
            validacionPassword = validacionLoginUiState.validacionPassword,
            recordarmeState = recordarmeState,
            isLoading = isLoading,
            onValueChangeLogin = {
                onLoginEvent(LoginEvent.LoginChanged(it))
            },
            onValueChangePassword = {
                onLoginEvent(LoginEvent.PasswordChanged(it))
            },
            onCheckedChanged = { recordarmeState = it },
            onClickLogearse = {
                onLoginEvent(LoginEvent.OnClickLogearse(onNavigateToCasino))
            }
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Text(
            "Olvidaste Password?",
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic,
            color = Purple40
        )
        Text("ó")
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook",
                alignment = Alignment.Center,
                modifier = Modifier.size(35.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.gmail),
                contentDescription = "Gmail",
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .padding(3.dp)

            )
        }
        TextNewAccount(onClick = onNavigateToNuevaCuenta)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val loginViewModel: LoginViewModel = viewModel()
    CasinoTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {

            LoginScreen(usuarioUiState = loginViewModel.usuarioUiState,
                validacionLoginUiState = loginViewModel.validacionLoginUiState,
                loginErroneo = false,
                isLoading = false,
                onLoginEvent = loginViewModel::onLoginEvent,
                onNavigateToNuevaCuenta = {})
        }
    }
}