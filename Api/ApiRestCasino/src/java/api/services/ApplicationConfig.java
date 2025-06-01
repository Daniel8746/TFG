package api.services;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
@OpenAPIDefinition(
        info = @Info(
                title = "API Casino",
                version = "1.0",
                description = "Documentación de la API del casino"
        ),
        servers = {
            @Server(url = "http://localhost:8080/casino", description = "Servidor local de desarrollo")
        }
)
public class ApplicationConfig extends Application {

}
