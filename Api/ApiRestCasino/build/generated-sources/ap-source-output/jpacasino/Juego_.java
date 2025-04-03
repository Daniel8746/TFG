package jpacasino;

import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;
import jpacasino.Apuesta;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-04-03T20:48:00", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Juego.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Juego_ { 

    public static volatile SingularAttribute<Juego, String> tipo;
    public static volatile SingularAttribute<Juego, String> reglas;
    public static volatile SingularAttribute<Juego, Integer> id;
    public static volatile SingularAttribute<Juego, String> nombre;
    public static volatile CollectionAttribute<Juego, Apuesta> apuestaCollection;

}