/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jwt;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 *
 * @author danie
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Obtener el token del header Authorization
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") 
                && !authorizationHeader.equals("Bearer null")) {
            // Extraer el token
            String token = authorizationHeader.substring("Bearer".length()).trim();

            try {
                // Validar el token
                String correo = JwtUtil.getCorreoFromToken(token);

                if (correo == null) {
                    // Si el token no es válido o ha expirado
                    requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
                            .entity("Token inválido o expirado")
                            .build());
                } else {
                    // Si el token es válido, puedes agregar el usuario al contexto
                    requestContext.setProperty("usuarioCorreo", correo);
                }
            } catch (Exception e) {
                // Si ocurre un error durante la validación del token
                requestContext.abortWith(Response.status(Status.UNAUTHORIZED)
                        .entity("Token no válido")
                        .build());

            }
        } else {
            // Si no hay token, continuar la solicitud
            // Puedes permitir la creación de usuarios sin token
            requestContext.setProperty("usuarioCorreo", "sin-token");
        }
    }
}
