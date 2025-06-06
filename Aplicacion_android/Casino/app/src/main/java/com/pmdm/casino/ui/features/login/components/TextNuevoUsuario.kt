package com.pmdm.casino.ui.features.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextNewAccount(onClick: () -> Unit, color: Color = MaterialTheme.colorScheme.primary) {
    Row {
        Text(
            "¿No tienes cuenta?",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp
        )
        Spacer(Modifier.width(8.dp))
        Text(
            "Crear cuenta",
            modifier = Modifier.clickable(onClick = onClick),
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}