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
@Schema(name = "UsuarioRecord", description = "Datos básicos de un usuario")
public record UsuarioRecord(
        @Schema(description = "Correo electrónico del usuario", example = "usuario@ejemplo.com")
        String correo,
        @Schema(description = "Contraseña del usuario", example = "miPassword123")
        String contrasenya,
        @Schema(description = "Saldo actual del usuario", example = "150.00")
        BigDecimal saldo) {

}
