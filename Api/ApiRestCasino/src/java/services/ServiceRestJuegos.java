/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Path;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
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
@Tag(name = "Juegos", description = "Operaciones relacionadas con los juegos disponibles")
public class ServiceRestJuegos {

    private static final EntityManagerFactory emf;
    private static final JuegoJpaController dao;

    static {
        emf = JPAUtil.getEntityManagerFactory();
        dao = new JuegoJpaController(emf);
    }

    @GET
    @Path("todos-juegos")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Obtener todos los juegos",
            description = "Devuelve una lista con todos los juegos disponibles en la base de datos.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lista de juegos obtenida exitosamente",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Juego.class)),
                                examples = @ExampleObject(value = """
                                [
                                  {
                                    "id": 1,
                                    "nombre": "Blackjack",
                                    "tipo": "Juego de cartas",
                                    "reglas": "En el Blackjack Europeo, el objetivo es obtener una mano cuya suma se acerque a 21 sin pasarse. Las figuras (Rey, Reina, Jota) valen 10 puntos y el As puede valer 1 o 11. Los jugadores reciben dos cartas y pueden pedir más cartas (\"hit\") o plantarse (\"stand\"). El crupier recibe una carta descubierta y una tapada. En la versión europea, el crupier no recibe la segunda carta hasta que los jugadores hayan tomado sus decisiones. Si la suma de las cartas del jugador es mayor que 21, pierde. El jugador gana si tiene una mano de 21 puntos o si tiene una mano más cercana a 21 que el crupier sin pasarse."
                                  }
                                ]
                                """)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "No se encontraron juegos en la base de datos"
                )
            }
    )
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
