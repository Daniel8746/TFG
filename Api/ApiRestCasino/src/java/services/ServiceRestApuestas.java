/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import classRecord.ApuestaRecord;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jpacasino.Apuesta;
import jpacasino.ApuestaJpaController;
import jpacasino.JuegoJpaController;
import jpacasino.UsuarioJpaController;
import org.bson.Document;
import utils.JPAUtil;

/**
 *
 * @author danie
 */
@Path("apuestas")
@Tag(name = "Apuestas", description = "Operaciones relacionadas con las apuestas")
public class ServiceRestApuestas {

    private static final MongoCollection<Document> collection;

    private static final EntityManagerFactory emf;
    private static final UsuarioJpaController daoUsuario;
    private static final JuegoJpaController daoJuego;
    private static final ApuestaJpaController daoApuesta;

    static {
        emf = JPAUtil.getEntityManagerFactory();
        daoUsuario = new UsuarioJpaController(emf);
        daoJuego = new JuegoJpaController(emf);
        daoApuesta = new ApuestaJpaController(emf);

        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("casino");
        collection = database.getCollection("apuesta");
    }

    @POST
    @Path("finalizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Finalizar una apuesta",
            description = "Inserta la apuesta en MongoDB y luego vuelca a la base de datos relacional.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ApuestaRecord.class),
                            examples = @ExampleObject(value = """
                    {
                      "correoUsuario": "usuario@ejemplo.com",
                      "nombreJuego": "Blackjack",
                      "montoApostado": 10.50,
                      "resultado": "ganador",
                      "detallesResultado": "Blackjack natural"
                    }
                    """)
                    )
            ),
            responses = {
                @ApiResponse(responseCode = "201", description = "Apuesta finalizada correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos de apuesta inválidos")
            }
    )
    public Response finalizarJuego(ApuestaRecord apuesta, @Context ContainerRequestContext requestContext) {
        String correo = (String) requestContext.getProperty("usuarioCorreo");

        if ("sin-token".equals(correo)) {
            correo = apuesta.correoUsuario();
        }

        int idUsuario = insertarDocumento(correo, apuesta);

        collection.find(
                Filters.eq("id_usuario", idUsuario)
        ).forEach(
                doc -> {
                    Apuesta apuestaBD = new Apuesta();
                    apuestaBD.setUsuarioId(daoUsuario.findUsuario(idUsuario));
                    apuestaBD.setJuegoId(daoJuego.findJuego(doc.getInteger("id_juego")));
                    apuestaBD.setFecha(LocalDateTime.parse(doc.getString("fecha")));
                    apuestaBD.setResultado(doc.getString("resultado"));
                    apuestaBD.setDetallesResultado(doc.getString("detalles_resultado"));
                    apuestaBD.setMontoApostado(doc.get("monto_apostado", BigDecimal.class));

                    daoApuesta.create(apuestaBD);
                }
        );

        collection.deleteMany(
                Filters.eq("id_usuario", idUsuario)
        );

        return Response
                .status(Status.CREATED)
                .build();
    }

    private int insertarDocumento(String correo, ApuestaRecord apuesta) {
        int idUsuario = daoUsuario.findUsuarioApuesta(correo);
        int idJuego = daoJuego.findJuegoApuesta(apuesta.nombreJuego());

        Document doc = new Document()
                .append("id_usuario", idUsuario)
                .append("id_juego", idJuego)
                .append("monto_apostado", apuesta.montoApostado())
                .append("fecha", LocalDateTime.now())
                .append("resultado", apuesta.resultado())
                .append("detalles_resultado", apuesta.detallesResultado());

        collection.insertOne(doc);

        return idUsuario;
    }

    @POST
    @Path("apuesta-juego")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Registrar una nueva apuesta",
            description = "Guarda una nueva apuesta en MongoDB.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ApuestaRecord.class),
                            examples = @ExampleObject(value = """
                    {
                      "correoUsuario": "usuario@ejemplo.com",
                      "nombreJuego": "Blackjack",
                      "montoApostado": 5.00,
                      "resultado": "pendiente",
                      "detallesResultado": "Apuesta en curso"
                    }
                    """)
                    )
            ),
            responses = {
                @ApiResponse(responseCode = "201", description = "Apuesta registrada correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos de apuesta inválidos")
            }
    )
    public Response apuestaJuego(ApuestaRecord apuesta, @Context ContainerRequestContext requestContext) {
        String correo = (String) requestContext.getProperty("usuarioCorreo");

        if ("sin-token".equals(correo)) {
            correo = apuesta.correoUsuario();
        }

        insertarDocumento(correo, apuesta);

        return Response
                .status(Status.CREATED)
                .build();
    }
}
