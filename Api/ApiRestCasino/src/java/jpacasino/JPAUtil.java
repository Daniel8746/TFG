/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacasino;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author alumno
 */
public class JPAUtil {

    public static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("ApiRestCasinoPU");
            }
        } catch (Throwable t) {
            System.out.println("Error al iniciar el Entity Manager factory");
            t.printStackTrace();
            throw new ExceptionInInitializerError();
        }
        return emf;
    }

}
