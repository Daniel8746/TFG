/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.classRecord;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author danie
 */
@Schema(name = "JuegosRecord", description = "Representa los datos de los juegos")
public record JuegosRecord(
        @Schema(description = "Nombre del juego", example = "BlackJack")
        String nombre,
        @Schema(description = "Tipo del juego", example = "Cartas")
        String tipo,
        @Schema(description = "Reglas del juego", example = "La suma de las cartas no debe superar 21")
        String reglas) {

}
