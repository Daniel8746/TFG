package com.pmdm.casino.ui.features.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldEmail
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldPassword
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie

@Composable
fun DialogoGestionCuenta(
    modifier: Modifier = Modifier,
    titulo: String,
    loginState: String,
    validacionLogin: Validacion,
    passwordState: String,
    validacionPassword: Validacion,
    onValueChangeLogin: (String) -> Unit,
    onValueChangePassword: (String) -> Unit,
    onCerrarDialogo: () -> Unit,
    onConfirmar: () -> Unit
) {
    Dialog(
        onDismissRequest = onCerrarDialogo
    ) {
        ElevatedCard(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titulo,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

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

                Spacer(modifier = Modifier.height(40.dp))

                Row {
                    ButtonWithLottie(
                        modifier = Modifier
                            .size(width = 150.dp, height = 50.dp),
                        text = "Confirmar",
                        onClick = onConfirmar
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    ButtonWithLottie(
                        modifier = Modifier
                            .size(width = 150.dp, height = 50.dp),
                        text = "Cerrar",
                        onClick = onCerrarDialogo
                    )
                }
            }
        }
    }
}