package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.pmdm.casino.ui.features.ruleta.ApuestasUiState
import com.pmdm.casino.ui.features.ruleta.RuletaEvent
import com.pmdm.casino.ui.features.ruleta.TipoApuestaEnum

@Composable
fun TableroRuleta(
    listaApuestaMarcado: List<ApuestasUiState>,
    listaApuestaDefinitiva: Set<ApuestasUiState>,
    onRuletaEvent: (RuletaEvent) -> Unit
) {
    val bloques = remember {
        (1..36).map { ApuestasUiState(TipoApuestaEnum.NUMERO, it.toString()) }
            .chunked(3).chunked(4)
    }

    val apuestasUiStateDocenas = remember {
        listOf(
            ApuestasUiState(TipoApuestaEnum.DOCENA1, "1ª Docena"),
            ApuestasUiState(TipoApuestaEnum.DOCENA2, "2ª Docena"),
            ApuestasUiState(TipoApuestaEnum.DOCENA3, "3ª Docena")
        )
    }

    val apuestasUiStateEspeciales = remember {
        listOf(
            listOf(
                ApuestasUiState(TipoApuestaEnum.MITAD1, "1-18"),
                ApuestasUiState(TipoApuestaEnum.PAR, "PAR")

            ),
            listOf(
                ApuestasUiState(TipoApuestaEnum.NEGRO, "NEGRO"),
                ApuestasUiState(TipoApuestaEnum.ROJO, "ROJO")
            ),
            listOf(
                ApuestasUiState(TipoApuestaEnum.IMPAR, "IMPAR"),
                ApuestasUiState(TipoApuestaEnum.MITAD2, "19-36")
            )
        )
    }

    val apuestasUiStateColumnas = remember {
        listOf(
            ApuestasUiState(TipoApuestaEnum.FILA1, "2 a 1"),
            ApuestasUiState(TipoApuestaEnum.FILA2, "2 a 1"),
            ApuestasUiState(TipoApuestaEnum.FILA3, "2 a 1")
        )
    }

    val esRojo = remember {
        setOf(
            1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
        )
    }

    val size = remember {
        40.dp
    }

    val itemPositions = remember { mutableStateMapOf<ApuestasUiState, Rect>() }
    val processedItemsDuringSwipe = remember { mutableStateListOf<ApuestasUiState>() }

    // Guardamos las coordenadas globales del layout que detecta los gestos
    // Se utiliza root en vez de window porque solo se necesita gestos en un sitio en especifico
    // en este caso es el tablero, si se quisiera gestos en toda la pantalla sería mejor window
    // en este caso funciona los dos igual, pero root es menos costoso en tema de rendimiento
    val layoutCoordinates = remember { mutableStateOf<LayoutCoordinates?>(null) }

    Column(
        modifier = Modifier
            // Guardamos las coordenadas globales del layout padre (tablero),
            // necesarias para convertir gestos (offsets locales) a coordenadas absolutas (en pantalla)
            .onGloballyPositioned { coords -> layoutCoordinates.value = coords }
            // Añadimos este listener pointerInput para manejar manualmente tanto
            // eventos de tap como de drag en el tablero de apuestas,
            // combinándolos en un único controlador para evitar conflictos.
            .pointerInput(Unit) {
                // Control manual de eventos para detectar taps y drags
                awaitPointerEventScope {
                    while (true) {
                        // Espera el primer toque (down) del usuario
                        val down = awaitFirstDown()
                        val coords = layoutCoordinates.value ?: continue

                        // Convertimos la posición local del down a posición absoluta en root
                        val startAbsolutePosition = coords.localToRoot(down.position)

                        // Limpiamos la lista de casillas que se procesaron en swipes anteriores
                        processedItemsDuringSwipe.clear()

                        // Buscamos si el toque inicial fue sobre alguna casilla del tablero
                        val startHitItem = itemPositions.entries.find {
                            it.value.contains(startAbsolutePosition)
                        }?.key

                        // Si encontró una casilla, lanzamos el evento y la añadimos a procesadas
                        startHitItem?.let {
                            onRuletaEvent(RuletaEvent.ApuestaSeleccionada(it))
                            processedItemsDuringSwipe.add(it)
                        }

                        // Variable para controlar si se detecta un arrastre (drag)
                        var dragHappened = false

                        // Esperamos hasta que el usuario mueva el dedo lo suficiente para ser considerado un drag
                        // o hasta que se cancele el gesto.
                        // awaitTouchSlopOrCancellation espera el movimiento que supere el "touch slop"
                        // Explicación touch slop:
                        // "Touch slop" es un umbral de distancia mínima que el dedo debe moverse para que se considere un arrastre (drag).
                        // Movimientos pequeños e involuntarios por debajo de ese umbral se ignoran para evitar detectar arrastres no deseados.
                        // Solo si el dedo se mueve más allá de este umbral, el sistema reconoce el gesto como un arrastre.
                        // Esto mejora la experiencia evitando que pequeños movimientos se interpreten erróneamente como drag.
                        awaitTouchSlopOrCancellation(down.id) { change, _ ->
                            dragHappened = true
                            change.consume() // Consumimos el cambio para que no interfiera con otros gestos
                        }

                        if (dragHappened) {
                            // Mientras el usuario mantenga el dedo presionado y siga arrastrando
                            do {
                                // Esperamos el siguiente evento de puntero (movimiento o levantamiento)
                                val event = awaitPointerEvent()
                                val dragPosition = event.changes.firstOrNull()?.position ?: break
                                val absoluteDragPos = coords.localToRoot(dragPosition)

                                // Buscamos la casilla actual bajo el dedo durante el arrastre
                                val hitItem = itemPositions.entries.find {
                                    it.value.contains(absoluteDragPos)
                                }?.key

                                // Si es una casilla nueva, la seleccionamos y la añadimos a la lista procesada
                                if (hitItem != null && !processedItemsDuringSwipe.contains(hitItem)) {
                                    onRuletaEvent(RuletaEvent.ApuestaSeleccionada(hitItem))
                                    processedItemsDuringSwipe.add(hitItem)
                                }

                                // Consumimos todos los cambios del evento para evitar interferencias
                                event.changes.forEach { it.consume() }

                                // Continuamos mientras el dedo esté presionado
                            } while (event.changes.any { it.pressed })
                        }
                        // Si no hubo drag, el tap ya fue procesado arriba con startHitItem
                    }
                }
            }
    ) {
        // ===================
        // Casilla "0" especial
        // ===================
        ApuestaBox(
            modifier = Modifier
                .border(
                    1.dp,
                    if (listaApuestaMarcado.contains(
                            ApuestasUiState(
                                TipoApuestaEnum.NUMERO,
                                "0"
                            )
                        )
                    ) Color.Magenta
                    else Color.Yellow
                )
                .size(width = size * 3, height = size)
                // Guardamos la posición y tamaño de este elemento cuando se dibuja
                .onGloballyPositioned { coords ->
                    val position = coords.positionInRoot() // Posición absoluta en pantalla
                    val sizePx = coords.size.toSize() // Tamaño en píxeles
                    itemPositions[ApuestasUiState(TipoApuestaEnum.NUMERO, "0")] =
                        Rect(position, sizePx) // Guardamos su área como un rectángulo
                }
                .background(Color.Green),
            valor = "0",
            color = Color.White,
            isInListaDefinitiva = ApuestasUiState(
                TipoApuestaEnum.NUMERO,
                "0"
            ) in listaApuestaDefinitiva
        )

        // =====================
        // Todos los demás números
        // =====================
        bloques.forEachIndexed { index, filas ->
            Row {
                Column {
                    filas.forEach { fila ->
                        Row {
                            fila.forEach { numero ->
                                ApuestaBox(
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            if (listaApuestaMarcado.contains(numero)) Color.Magenta
                                            else Color.Yellow
                                        )
                                        .size(size)
                                        .onGloballyPositioned { coords ->
                                            val position = coords.positionInRoot()
                                            val sizePx = coords.size.toSize()
                                            itemPositions[numero] =
                                                Rect(position, sizePx)
                                        }
                                        .background(
                                            if (esRojo.contains(numero.valor.toInt())) Color.Red
                                            else Color.Black
                                        ),
                                    valor = numero.valor,
                                    color = Color.White,
                                    isInListaDefinitiva = numero in listaApuestaDefinitiva
                                )
                            }
                        }
                    }
                }

                // ============================
                // Casillas de docenas (a la derecha)
                // ============================
                ApuestaBox(modifier = Modifier
                    .border(
                        1.dp,
                        if (listaApuestaMarcado.contains(apuestasUiStateDocenas[index])) Color.Magenta
                        else Color.Yellow
                    )
                    .size(width = size + 40.dp, height = size * 4)
                    .onGloballyPositioned { coords ->
                        val position = coords.positionInRoot()
                        val sizePx = coords.size.toSize()
                        itemPositions[apuestasUiStateDocenas[index]] =
                            Rect(position, sizePx)
                    }
                    .background(Color.LightGray),
                    valor = apuestasUiStateDocenas[index].valor,
                    color = Color.Black,
                    isInListaDefinitiva = apuestasUiStateDocenas[index] in listaApuestaDefinitiva
                )

                // =============================
                // Casillas especiales (debajo)
                // =============================
                Column {
                    apuestasUiStateEspeciales[index].forEach { especial ->
                        ApuestaBox(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    if (listaApuestaMarcado.contains(especial)) Color.Magenta
                                    else Color.Yellow
                                )
                                .size(width = size + 10.dp, height = size * 2)
                                .onGloballyPositioned { coords ->
                                    val position = coords.positionInRoot()
                                    val sizePx = coords.size.toSize()
                                    itemPositions[especial] =
                                        Rect(position, sizePx)
                                }
                                .background(Color.LightGray),
                            valor = especial.valor,
                            color = Color.Black,
                            isInListaDefinitiva = especial in listaApuestaDefinitiva
                        )
                    }
                }
            }
        }

        // =========================================
        // Apuestas especiales por columnas (abajo)
        // =========================================
        Row {
            apuestasUiStateColumnas.forEach { apuestaColumna ->
                ApuestaBox(
                    modifier = Modifier
                        .border(
                            1.dp,
                            if (listaApuestaMarcado.contains(apuestaColumna)) Color.Magenta
                            else Color.Yellow
                        )
                        .size(size)
                        .onGloballyPositioned { coords ->
                            val position = coords.positionInRoot()
                            val sizePx = coords.size.toSize()
                            itemPositions[apuestaColumna] =
                                Rect(position, sizePx)
                        }
                        .background(Color.LightGray),
                    valor = apuestaColumna.valor,
                    color = Color.White,
                    isInListaDefinitiva = apuestaColumna in listaApuestaDefinitiva
                )
            }
        }
    }
}