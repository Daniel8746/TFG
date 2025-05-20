/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classRecord;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author danie
 */
@Schema(name = "CartaRecord", description = "Representa una carta del mazo de Blackjack")
public record CartaRecord(
        @Schema(description = "Palo de la carta", example = "Pica")
        String palo,
        @Schema(description = "Valor de la carta", example = "K")
        String valor) {

}
