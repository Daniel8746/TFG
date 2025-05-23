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
    enabled: Boolean,
    listaApuestaMarcado: List<ApuestasUiState>,
    listaApuestaDefinitiva: Set<ApuestasUiState>,
    esRojo: Set<Int>,
    bloques: List<List<List<ApuestasUiState>>>,
    onRuletaEvent: (RuletaEvent) -> Unit
) {
    val cero = remember { ApuestasUiState("0", Color.Green, TipoApuestaEnum.NUMERO) }

    val apuestasUiStateDocenas = remember {
        listOf(
            ApuestasUiState("1ª Docena", Color.LightGray, TipoApuestaEnum.DOCENA1),
            ApuestasUiState("2ª Docena", Color.LightGray, TipoApuestaEnum.DOCENA2),
            ApuestasUiState("3ª Docena", Color.LightGray, TipoApuestaEnum.DOCENA3)
        )
    }

    val apuestasUiStateEspeciales = remember {
        listOf(
            listOf(
                ApuestasUiState("1-18", Color.LightGray, TipoApuestaEnum.MITAD1),
                ApuestasUiState("PAR", Color.LightGray, TipoApuestaEnum.PAR)

            ),
            listOf(
                ApuestasUiState("NEGRO", Color.LightGray, TipoApuestaEnum.NEGRO),
                ApuestasUiState("ROJO", Color.LightGray, TipoApuestaEnum.ROJO)
            ),
            listOf(
                ApuestasUiState("IMPAR", Color.LightGray, TipoApuestaEnum.IMPAR),
                ApuestasUiState("19-36", Color.LightGray, TipoApuestaEnum.MITAD2)
            )
        )
    }

    val apuestasUiStateColumnas = remember {
        listOf(
            ApuestasUiState("2 a 1", Color.LightGray, TipoApuestaEnum.COLUMNA1),
            ApuestasUiState("2 a 1", Color.LightGray, TipoApuestaEnum.COLUMNA2),
            ApuestasUiState("2 a 1", Color.LightGray, TipoApuestaEnum.COLUMNA3)
        )
    }

    val size = remember {
        27.dp
    }

    val itemPositions = remember { mutableStateMapOf<ApuestasUiState, Rect>() }
    val processedItemsDuringSwipe = remember { mutableStateListOf<ApuestasUiState>() }

    // Guardamos las coordenadas globales del layout que detecta los gestos
    // Se utiliza root en vez de window porque solo se necesita gestos en un sitio en especifico
    // en este caso es el tablero, si se quisiera gestos en toda la pantalla sería mejor window
    // en este caso funciona los dos igual, pero root es menos costoso en tema de rendimiento
    val layoutCoordinates = remember { mutableStateOf<LayoutCoordinates?>(null) }

    Row(
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
                    if (listaApuestaMarcado.contains(cero)) 2.dp else 1.dp,
                    if (listaApuestaMarcado.contains(cero)) Color.Magenta
                    else Color.Yellow
                )
                .size(width = size, height = size * 3)
                // Guardamos la posición y tamaño de este elemento cuando se dibuja
                .onGloballyPositioned { coords ->
                    val position = coords.positionInRoot() // Posición absoluta en pantalla
                    val sizePx = coords.size.toSize() // Tamaño en píxeles
                    itemPositions[cero] =
                        Rect(position, sizePx) // Guardamos su área como un rectángulo
                }
                .background(cero.color),
            valor = "0",
            color = Color.White,
            isInListaDefinitiva = cero in listaApuestaDefinitiva
        )

        // =====================
        // Todos los demás números
        // =====================
        bloques.forEachIndexed { index, filas ->
            Column {
                Row {
                    filas.forEach { fila ->
                        Column {
                            fila.forEach { numero ->
                                ApuestaBox(
                                    modifier = Modifier
                                        .border(
                                            if (listaApuestaMarcado.contains(numero)) 2.dp else 1.dp,
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
                        if (listaApuestaMarcado.contains(apuestasUiStateDocenas[index])) 2.dp else 1.dp,
                        if (listaApuestaMarcado.contains(apuestasUiStateDocenas[index])) Color.Magenta
                        else Color.Yellow
                    )
                    .size(width = size * 4, height = size + 40.dp)
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
                Row {
                    apuestasUiStateEspeciales[index].forEach { especial ->
                        ApuestaBox(
                            modifier = Modifier
                                .border(
                                    if (listaApuestaMarcado.contains(especial)) 2.dp else 1.dp,
                                    if (listaApuestaMarcado.contains(especial)) Color.Magenta
                                    else Color.Yellow
                                )
                                .size(width = size * 2, height = size + 10.dp)
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
        Column {
            apuestasUiStateColumnas.forEach { apuestaColumna ->
                ApuestaBox(
                    modifier = Modifier
                        .border(
                            if (listaApuestaMarcado.contains(apuestaColumna)) 2.dp else 1.dp,
                            if (listaApuestaMarcado.contains(apuestaColumna)) Color.Magenta
                            else Color.Yellow
                        )
                        .size(width = size + 15.dp, height = size)
                        .onGloballyPositioned { coords ->
                            val position = coords.positionInRoot()
                            val sizePx = coords.size.toSize()
                            itemPositions[apuestaColumna] =
                                Rect(position, sizePx)
                        }
                        .background(apuestaColumna.color),
                    valor = apuestaColumna.valor,
                    color = Color.White,
                    isInListaDefinitiva = apuestaColumna in listaApuestaDefinitiva
                )
            }
        }
    }
}