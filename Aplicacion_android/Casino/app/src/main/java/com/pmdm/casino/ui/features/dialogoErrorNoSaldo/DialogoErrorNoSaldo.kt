package com.pmdm.casino.ui.features.dialogoErrorNoSaldo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.pmdm.casino.ui.features.components.ButtonWithLottie

@Composable
fun DialogoErrorNoSaldo(mensaje: String, onCerrarDialogo: () -> Unit) {
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
                    text = mensaje,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))

                ButtonWithLottie(
                    modifier = Modifier
                        .size(width = 150.dp, height = 50.dp),
                    text = "Confirmar",
                    onClick = onCerrarDialogo
                )
            }
        }
    }
}