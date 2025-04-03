package com.pmdm.casino.ui.features.blackJack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.components.TopBar

@Composable
fun BlackJackScreen(usuarioUiState: UsuarioCasinoUiState) {
    Box {
        TopBar(usuarioUiState)

       Row {
           TextButton(
               onClick = {}
           ) {
               Text(
                   text = "Pedir carta"
               )
           }

           TextButton(
               onClick = {}
           ) {
               Text(
                   text = "Plantarse"
               )
           }
       }
    }
}