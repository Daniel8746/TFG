/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import classRecord.CartaRecord;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danie
 */
@Path("black-jack")
@Tag(name = "Blackjack", description = "Operaciones del juego Blackjack")
public class ServiceRestBlackJack {

    private final static String[] palos = {"Corazon", "Diamante", "Trebol", "Pica"};
    private final static String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    private final static List<CartaRecord> mazo1 = new ArrayList<>();
    private final static List<CartaRecord> mazo2 = new ArrayList<>();

    private final SecureRandom random = new SecureRandom();

    static {
        inicializarCartas();
    }

    @GET
    @Path("carta")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Obtener una carta aleatoria",
            description = "Devuelve una carta aleatoria del mazo.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Carta obtenida con éxito",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = CartaRecord.class),
                                examples = @ExampleObject(value = "{\"palo\":\"Pica\",\"valor\":\"K\"}")
                        )
                )
            }
    )
    public Response getCarta() {
        CartaRecord cartaAzar;
        int posicion;

        if (random.nextBoolean()) {
            posicion = random.nextInt(0, mazo1.size());
            cartaAzar = mazo1.get(posicion);
            mazo1.remove(posicion);
        } else {
            posicion = random.nextInt(0, mazo2.size());
            cartaAzar = mazo2.get(posicion);
            mazo2.remove(posicion);
        }

        return Response
                .status(Status.OK)
                .entity(new Gson().toJson(cartaAzar))
                .build();
    }

    @GET
    @Path("iniciarJuego")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Iniciar una nueva partida",
            description = "Devuelve dos cartas aleatorias como mano inicial del jugador.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Mano inicial obtenida con éxito",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = CartaRecord.class)),
                                examples = @ExampleObject(value = "[{\"palo\":\"Trebol\",\"valor\":\"A\"},{\"palo\":\"Diamante\",\"valor\":\"10\"}]")
                        )
                )
            }
    )
    public Response getCartas() {
        List<CartaRecord> cartaAzar = new ArrayList<>();
        int posicion;

        for (int i = 0; i <= 1; ++i) {
            if (random.nextBoolean()) {
                posicion = random.nextInt(0, mazo1.size());
                cartaAzar.add(mazo1.get(posicion));
                mazo1.remove(posicion);
            } else {
                posicion = random.nextInt(0, mazo2.size());
                cartaAzar.add(mazo2.get(posicion));
                mazo2.remove(posicion);
            }
        }

        return Response
                .status(Status.OK)
                .entity(new Gson().toJson(cartaAzar))
                .build();
    }

    @POST
    @Path("reiniciar-cartas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Reiniciar mazos de cartas",
            description = "Reinicia el mazo de cartas para comenzar una nueva ronda.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Mazos reiniciados con éxito")
            }
    )
    public Response finalizar() {
        inicializarCartas();

        return Response
                .status(Status.OK)
                .build();
    }

    private static void inicializarCartas() {
        mazo1.clear();

        for (String palo : palos) {
            for (String valor : valores) {
                mazo1.add(new CartaRecord(palo, valor));
            }
        }

        mazo2.clear();
        mazo2.addAll(mazo1);
    }
}
