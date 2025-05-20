/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classRecord;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 *
 * @author danie
 */
@Schema(name = "ApuestaRecord", description = "Datos para crear o finalizar una apuesta")
public record ApuestaRecord(
        @Schema(description = "Correo electrónico del usuario que realiza la apuesta", example = "usuario@ejemplo.com")
        String correoUsuario,
        @Schema(description = "Nombre del juego en el que se realiza la apuesta", example = "Blackjack")
        String nombreJuego,
        @Schema(description = "Monto apostado por el usuario", example = "10.50")
        BigDecimal montoApostado,
        @Schema(description = "Resultado de la apuesta (ejemplo: ganador, perdedor, en curso, etc.)", example = "ganador")
        String resultado,
        @Schema(description = "Detalles adicionales sobre el resultado de la apuesta", example = "Apostado en 13, salido 18")
        String detallesResultado) {

}
