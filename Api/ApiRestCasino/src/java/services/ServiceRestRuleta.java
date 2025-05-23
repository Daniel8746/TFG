/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author danie
 */
@Path("ruleta")
public class ServiceRestRuleta {

    private final static int SEGUNDOS_CONTADOR = 20;
    private static int contador = SEGUNDOS_CONTADOR;
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static {
        iniciarContador();
    }

    @GET
    @Path("contador")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContador() {
        return Response
                .status(Status.OK)
                .entity(new Gson().toJson(contador))
                .build();
    }
    
    @GET
    @Path("numero-aleatorio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumeroRuleta() {
        return Response
                .status(Status.OK)
                .entity(new Gson().toJson(new Random().nextInt(0, 37)))
                .build();
    }

    private static void iniciarContador() {
        scheduler.scheduleAtFixedRate(() -> {
            if (contador > 0) {
                --contador;
            } else {
                reiniciarContador();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private static void reiniciarContador() {
        contador = SEGUNDOS_CONTADOR;
    }
}
