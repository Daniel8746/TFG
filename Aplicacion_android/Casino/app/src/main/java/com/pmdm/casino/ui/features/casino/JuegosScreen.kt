package com.pmdm.casino.ui.features.casino

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.casino.components.AyudaScreen
import com.pmdm.casino.ui.features.components.TopBar
import java.math.BigDecimal

@Composable
fun CasinoScreen(
    juegosUiState: List<JuegosUiState>,
    usuarioUiState: UsuarioCasinoUiState,
    onCasinoEvent: (JuegosEvent) -> Unit,
    onBlackJackEvent: (correo: String, saldo: BigDecimal) -> Unit,
    onRuletaEvent: (correo: String, saldo: BigDecimal) -> Unit,
    onTragaMonedas: (correo: String, saldo: BigDecimal) -> Unit,
    onAyudaEvent: () -> Unit,
    isAyudaAbierta: Boolean
) {
    var ayudaJuego by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                usuarioUiState = usuarioUiState
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Image(
                    painter = painterResource(R.drawable.imagenfondojuegos),
                    contentDescription = "Fondo juegos",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    if (juegosUiState.isEmpty()) {
                        Text(
                            modifier = Modifier.align(
                                Alignment.CenterHorizontally,
                            ),
                            textAlign = TextAlign.Center,
                            text = "No se han podido encontrar juegos, perdone las molestias.",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Normal
                        )
                    } else {
                        LazyColumn(modifier = Modifier.padding(3.dp)) {
                            items(juegosUiState, key = { it.nombre }) {
                                ElevatedCard(
                                    onClick = {
                                        when (it.nombre.split(" ")[0]) {
                                            "Blackjack" -> {
                                                onCasinoEvent(
                                                    JuegosEvent.OnBlackJack(
                                                        onBlackJackEvent
                                                    )
                                                )
                                            }

                                            "Ruleta" -> {
                                                onCasinoEvent(JuegosEvent.OnRuleta(onRuletaEvent))
                                            }

                                            "Traga monedas" -> {
                                                onCasinoEvent(
                                                    JuegosEvent.OnTragaMonedas(
                                                        onTragaMonedas
                                                    )
                                                )
                                            }
                                        }
                                    },
                                    elevation = CardDefaults.elevatedCardElevation(
                                        defaultElevation = 4.dp,
                                        pressedElevation = 8.dp,
                                        hoveredElevation = 6.dp
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(5.dp),
                                                text = it.nombre,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                modifier = Modifier.padding(5.dp),
                                                text = it.tipo,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        Image(
                                            painter = painterResource(R.drawable.imagenlogin),
                                            contentDescription = "Descripción tarjeta",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                                .clip(RoundedCornerShape(10.dp)),
                                            contentScale = ContentScale.Crop
                                        )

                                        IconButton(
                                            onClick = {
                                                onAyudaEvent()
                                                ayudaJuego = it.reglas
                                            },
                                            modifier = Modifier.align(Alignment.End)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Info,
                                                contentDescription = "Ayuda",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    }
                }

                if (isAyudaAbierta && ayudaJuego.isNotEmpty()) {
                    AyudaScreen(ayudaJuego, onAyudaEvent)
                }
            }
        }
    )
}