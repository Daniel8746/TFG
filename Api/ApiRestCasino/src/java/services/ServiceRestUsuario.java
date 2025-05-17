/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import classRecord.UsuarioRecord;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response.Status;
import java.lang.System.Logger;
import java.util.HashMap;
import utils.JPAUtil;
import jpacasino.Usuario;
import jpacasino.UsuarioJpaController;
import jpacasino.exceptions.IllegalOrphanException;
import jpacasino.exceptions.NonexistentEntityException;
import provider.jwt.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author danie
 */
// Servicio REST para gestionar operaciones de usuario
@Path("usuario")
public class ServiceRestUsuario {

    private static final EntityManagerFactory emf;
    private static final UsuarioJpaController dao;

    private static final Logger LOG = System.getLogger(ServiceRestUsuario.class.getName());

    static {
        emf = JPAUtil.getEntityManagerFactory();
        dao = new UsuarioJpaController(emf);
    }

    // Método para loguearse: recibe el correo y la contraseña, y si son correctos devuelve los datos del usuario
    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOne(UsuarioRecord usuario, @Context ContainerRequestContext requestContext) {
        HashMap<String, String> mensaje = new HashMap<>();

        // Obtener el correo desde el contexto
        String correo = (String) requestContext.getProperty("usuarioCorreo");

        // Si no hay correo en el contexto, significa que no se envió un token o el token es inválido
        if ("sin-token".equals(correo)) {
            // Se busca el usuario en la base de datos usando el correo y la contraseña
            UsuarioRecord usuarioEncontrado = dao.findUsuario(usuario);

            if (usuarioEncontrado == null) {
                // Si no se encuentra el usuario, se devuelve un booleano para avisar a la aplicación
                return Response
                        .status(Status.UNAUTHORIZED)
                        .build();
            }

            // Si el usuario existe, generamos un token para este usuario
            // Generar un token para JWT
            String token = JwtUtil.generarToken(usuarioEncontrado.correo());

            return rellenarCamposMensaje(
                    mensaje,
                    token,
                    usuarioEncontrado.saldo().toPlainString()
            );
        }

        // Si el correo es válido, ya tenemos un usuario autenticado gracias al token
        // Buscar el usuario con el correo ya validado
        UsuarioRecord usuarioEncontrado = dao.findUsuario(correo);

        if (usuarioEncontrado == null) {
            return Response
                    .status(Status.UNAUTHORIZED)
                    .build();
        }

        // Si el usuario existe, devolvemos su saldo y el token
        return rellenarCamposMensaje(
                mensaje,
                usuarioEncontrado.correo(),
                usuarioEncontrado.saldo().toPlainString()
        );
    }

    private Response rellenarCamposMensaje(HashMap<String, String> mensaje, String token, String saldo) {
        mensaje.put("saldo",
                saldo != null && !saldo.isBlank() ? saldo : "0"
        );
        mensaje.put("token", token);

        return Response
                .status(Status.OK)
                .entity(mensaje)
                .build();
    }

    // Método para eliminar un usuario: recibe el usuario y lo elimina de la base de datos
    @DELETE
    @Path("eliminar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response eliminarUsuario(UsuarioRecord usuario) {
        try {
            // Se busca al usuario por correo
            Usuario usuarioEncontrado = dao.findUsuarioEliminar(usuario);

            if (usuarioEncontrado == null) {
                // Si no se encuentra el usuario, se devuelve un mensaje de error
                return Response
                        .status(Status.NOT_FOUND)
                        .build();
            }

            // Si el usuario existe, se elimina de la base de datos
            dao.destroy(usuarioEncontrado.getId());
            return Response
                    .status(Status.OK)
                    .build();
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            // Si el usuario no existe, se maneja el error
            LOG.log(Logger.Level.ERROR, ex.getLocalizedMessage());
            return Response
                    .status(Status.BAD_REQUEST)
                    .build();
        }
    }

    // Método para crear un nuevo usuario: recibe los datos del usuario, lo registra y devuelve un mensaje
    @POST
    @Path("crear-usuario")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response crearUsuario(Usuario usuario) {
        // Se verifica si el correo ya está registrado
        UsuarioRecord usuarioEncontrado = dao.findUsuario(
                new UsuarioRecord(
                        usuario.getCorreo(), usuario.getContrasenya(), null
                )
        );

        if (usuarioEncontrado == null) {
            // Si el usuario no existe, se encripta la contraseña y se registra el nuevo usuario
            usuario.setContrasenya(
                    BCrypt.hashpw(
                            usuario.getContrasenya(),
                            BCrypt.gensalt()
                    )
            );

            dao.create(usuario);
            return Response
                    .status(Status.CREATED)
                    .build();
        }
        
        // Si el usuario ya existe, se devuelve un error
        return Response
                .status(Status.BAD_REQUEST)
                .build();
    }
}
