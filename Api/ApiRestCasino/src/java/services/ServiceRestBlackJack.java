/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import classRecord.CartaRecord;
import com.google.gson.Gson;
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
    @Consumes({MediaType.APPLICATION_JSON})
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
    @Consumes({MediaType.APPLICATION_JSON})
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
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
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
