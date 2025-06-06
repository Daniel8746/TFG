/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.utils;

import api.classRecord.JuegosRecord;
import api.jpacasino.Juego;

/**
 *
 * @author danie
 */
public class JuegosUtils {
    public static JuegosRecord toJuegosRecord(Juego juego) {
        return new JuegosRecord(
                juego.getNombre(),
                juego.getTipo(),
                juego.getReglas()
        );
    }
}
