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
import jakarta.ws.rs.core.Response.Status;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import java.math.BigDecimal;
import java.util.HashMap;
import jpacasino.JPAUtil;
import jpacasino.Usuario;
import jpacasino.UsuarioJpaController;
import jpacasino.exceptions.IllegalOrphanException;
import jpacasino.exceptions.NonexistentEntityException;
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

    static {
        emf = JPAUtil.getEntityManagerFactory();
        dao = new UsuarioJpaController(emf);
    }

    // Método para loguearse: recibe el correo y la contraseña, y si son correctos devuelve los datos del usuario
    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOne(UsuarioRecord usuario) {
        Response response;
        Status statusResul;

        try {
            // Se busca el usuario en la base de datos usando el correo y la contraseña
            Usuario usuarioEncontrado = dao.findUsuario( usuario);

            if (usuarioEncontrado == null) {
                // Si no se encuentra el usuario, se devuelve un booleano para avisar a la aplicación
                statusResul = Response.Status.NOT_FOUND;
                response = Response
                        .status(statusResul)
                        .build();
            } else {
                // Si el usuario existe, se devuelve un valor booleano y el saldo
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .build();
            }
        } catch (Exception ex) {
            // Manejo de errores en el proceso
            statusResul = Response.Status.BAD_REQUEST;
            response = Response
                    .status(statusResul)
                    .build();
        }

        return response;
    }

    // Método para eliminar un usuario: recibe el usuario y lo elimina de la base de datos
    @DELETE
    @Path("eliminar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response eliminarUsuario(UsuarioRecord usuario) {
        Response response;
        Status statusResul;

        try {
            // Se busca al usuario por correo
            Usuario usuarioEncontrado = dao.findUsuario(usuario);

            if (usuarioEncontrado == null) {
                // Si no se encuentra el usuario, se devuelve un mensaje de error
                statusResul = Response.Status.NOT_FOUND;
                response = Response
                        .status(statusResul)
                        .build();
            } else {
                // Si el usuario existe, se elimina de la base de datos
                dao.destroy(usuarioEncontrado.getId());
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .build();
            }
        } catch (NonexistentEntityException | IllegalOrphanException ex) {
            // Si el usuario no existe, se maneja el error
            statusResul = Response.Status.BAD_REQUEST;
            response = Response
                    .status(statusResul)
                    .build();
        }

        return response;
    }

    // Método para crear un nuevo usuario: recibe los datos del usuario, lo registra y devuelve un mensaje
    @POST
    @Path("crear-usuario")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response crearUsuario(Usuario usuario) {
        Response response;
        
        try {
            // Se verifica si el correo ya está registrado
            Usuario usuarioEncontrado = dao.findUsuario(
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
                response = Response
                        .status(Response.Status.CREATED)
                        .build();
            } else {
                // Si el usuario ya existe, se devuelve un error
                response = Response
                        .status(Response.Status.BAD_REQUEST)
                        .build();
            }
        } catch (Exception e) {
            // Si ocurre un error en el proceso de creación
            response = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return response;
    }
}
