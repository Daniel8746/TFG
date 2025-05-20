/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package provider.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

/**
 *
 * @author danie
 */
public class JwtUtil {

    // Clave secreta para firmar el JWT
    private static final SecretKey SECRET_KEY = generateSecretKey();

    // Generar la clave secreta HMAC-SHA256
    private static SecretKey generateSecretKey() {
        try {
            // Crear un generador de claves para HMAC-SHA256
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256); // Tamaño de la clave
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando la clave secreta", e);
        }
    }

    // Generar el token JWT
    public static String generarToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    // Verificar y obtener el subject del token (en este caso, el username)
    public static String getCorreoFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();  // Obtener el correo del token
        } catch (JwtException ex) {
            return null;
        }
    }
}
