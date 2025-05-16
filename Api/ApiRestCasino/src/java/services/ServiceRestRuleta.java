/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author danie
 */
@Path("ruleta")
public class ServiceRestRuleta {

    private final static int SEGUNDOS_CONTADOR = 10;
    private static int contador = SEGUNDOS_CONTADOR;
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    static {
        iniciarContador();
    }

    @GET
    @Path("contador")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getContador() {
        Response response;

        try {
            response = Response
                    .status(Response.Status.OK)
                    .entity(new Gson().toJson(contador))
                    .build();
        } catch (Exception ex) {
            response = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return response;
    }

    @GET
    @Path("reiniciar")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response reiniciarContadorUsuario() {
        Response response;

        try {
            reiniciarContador();

            response = Response
                    .status(Response.Status.OK)
                    .build();
        } catch (Exception ex) {
            response = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return response;
    }

    private static void iniciarContador() {
        scheduler.scheduleAtFixedRate(() -> {
            if (contador > 0) {
                --contador;
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private void reiniciarContador() {
        if (contador == 0) {
            contador = SEGUNDOS_CONTADOR;
        }
    }
}
