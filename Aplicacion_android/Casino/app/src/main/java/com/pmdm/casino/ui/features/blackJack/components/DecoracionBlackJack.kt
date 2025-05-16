package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun DecoracionBlackJack() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            ImagenDesdeAssets(
                nombreCarpeta = "gatos_rojos",
                nombreArchivo = "joker1"
            )

            ImagenDesdeAssets(
                nombreCarpeta = "gatos_azules",
                nombreArchivo = "joker1"
            )
        }

        Row {
            ImagenDesdeAssets(
                nombreCarpeta = "gatos_rojos",
                nombreArchivo = "reversa"
            )

            ImagenDesdeAssets(
                nombreCarpeta = "gatos_azules",
                nombreArchivo = "reversa"
            )
        }

        Column {
            ImagenDesdeAssets(
                nombreCarpeta = "gatos_azules",
                nombreArchivo = "joker2"
            )

            ImagenDesdeAssets(
                nombreCarpeta = "gatos_rojos",
                nombreArchivo = "joker2"
            )
        }
    }
}