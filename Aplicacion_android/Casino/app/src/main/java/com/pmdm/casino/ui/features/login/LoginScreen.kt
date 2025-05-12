package com.pmdm.casino.ui.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.components.AbrirDialogoNoConexion
import com.pmdm.casino.ui.features.login.components.CircularImageFromResource
import com.pmdm.casino.ui.features.login.components.TextNewAccount
import com.pmdm.casino.ui.features.login.components.UsuarioPassword
import com.pmdm.casino.ui.theme.CasinoTheme
import java.math.BigDecimal


@Composable
fun LoginScreen(
    usuarioUiState: LoginUiState,
    validacionLoginUiState: ValidacionLoginUiState,
    loginErroneo: Boolean,
    isLoading: Boolean,
    recordarmeState: Boolean,
    reintentarConexion: Boolean,
    onLoginEvent: (LoginEvent) -> Unit,
    onNavigateToCasino: ((correo: String, saldo: BigDecimal) -> Unit)? = null,
    onNavigateToNuevaCuenta: () -> Unit,
    onRecordarmeState: ((Boolean) -> Unit)? = null,
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
            CircularImageFromResource(
                idImageResource = R.drawable.imagenlogin,
                contentDescription = "Imagen Login"
            )

            if (!loginErroneo) {
                Text(
                    text = """
                        No hemos podido encontrar una cuenta con esos datos.
                        Verifica tu correo o contraseña e inténtalo de nuevo.
                    """.trimIndent(),
                    color = Color(0xFFFF6B6B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            UsuarioPassword(
                modifier = Modifier.fillMaxWidth(),
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
                onCheckedChanged = onRecordarmeState!!,
                onClickLogearse = {
                    onLoginEvent(LoginEvent.OnClickLogearse(onNavigateToCasino))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿Olvidaste tu contraseña?",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White.copy(alpha = 0.8f)
            )

            Text(
                text = "ó",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier.size(36.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.gmail),
                    contentDescription = "Gmail",
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextNewAccount(onClick = onNavigateToNuevaCuenta, color = Color(0xFFFFD700))
        }

        if (reintentarConexion) {
            AbrirDialogoNoConexion {
                reiniciar()
            }
        }
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
                recordarmeState = false,
                onLoginEvent = loginViewModel::onLoginEvent,
                onNavigateToNuevaCuenta = {},
                reintentarConexion = false,
                reiniciar = {})
        }
    }
}