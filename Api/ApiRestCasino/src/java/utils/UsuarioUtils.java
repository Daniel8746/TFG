/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import classRecord.UsuarioRecord;
import jpacasino.Usuario;

/**
 *
 * @author danie
 */
public class UsuarioUtils {

    public static UsuarioRecord toUsuarioRecord(Usuario usuario) {
        return new UsuarioRecord(
                usuario.getCorreo(),
                usuario.getContrasenya(),
                usuario.getSaldo()
        );
    }
}
