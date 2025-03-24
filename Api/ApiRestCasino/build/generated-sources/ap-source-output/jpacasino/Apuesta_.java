package jpacasino;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import jpacasino.Juego;
import jpacasino.Usuario;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-12T23:47:08", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Apuesta.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Apuesta_ { 

    public static volatile SingularAttribute<Apuesta, BigDecimal> montoApostado;
    public static volatile SingularAttribute<Apuesta, LocalDateTime> fecha;
    public static volatile SingularAttribute<Apuesta, String> resultado;
    public static volatile SingularAttribute<Apuesta, Juego> juegoId;
    public static volatile SingularAttribute<Apuesta, Integer> id;
    public static volatile SingularAttribute<Apuesta, Usuario> usuarioId;

}