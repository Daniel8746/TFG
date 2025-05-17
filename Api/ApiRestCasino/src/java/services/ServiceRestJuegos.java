/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import jakarta.ws.rs.Path;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import utils.JPAUtil;
import jpacasino.Juego;
import jpacasino.JuegoJpaController;

/**
 *
 * @author danie
 */
// Servicio REST para gestionar operaciones de usuario
@Path("juegos")
public class ServiceRestJuegos {

    private static final EntityManagerFactory emf;
    private static final JuegoJpaController dao;

    static {
        emf = JPAUtil.getEntityManagerFactory();
        dao = new JuegoJpaController(emf);
    }

    @GET
    @Path("todos-juegos")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<Juego> listaJuegos = dao.findJuegoEntities();

        if (listaJuegos == null || listaJuegos.isEmpty()) {
            return Response
                    .status(Status.BAD_REQUEST)
                    .build();
        }

        return Response
                .status(Status.OK)
                .entity(new Gson().toJson(listaJuegos))
                .build();
    }
}
