/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacasino;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jpacasino.exceptions.NonexistentEntityException;

/**
 *
 * @author danie
 */
public class ApuestaJpaController implements Serializable {

    public ApuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Apuesta apuesta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juego juegoId = apuesta.getJuegoId();
            if (juegoId != null) {
                juegoId = em.getReference(juegoId.getClass(), juegoId.getId());
                apuesta.setJuegoId(juegoId);
            }
            Usuario usuarioId = apuesta.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                apuesta.setUsuarioId(usuarioId);
            }
            em.persist(apuesta);
            if (juegoId != null) {
                juegoId.getApuestaCollection().add(apuesta);
                juegoId = em.merge(juegoId);
            }
            if (usuarioId != null) {
                usuarioId.getApuestaCollection().add(apuesta);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Apuesta apuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apuesta persistentApuesta = em.find(Apuesta.class, apuesta.getId());
            Juego juegoIdOld = persistentApuesta.getJuegoId();
            Juego juegoIdNew = apuesta.getJuegoId();
            Usuario usuarioIdOld = persistentApuesta.getUsuarioId();
            Usuario usuarioIdNew = apuesta.getUsuarioId();
            if (juegoIdNew != null) {
                juegoIdNew = em.getReference(juegoIdNew.getClass(), juegoIdNew.getId());
                apuesta.setJuegoId(juegoIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                apuesta.setUsuarioId(usuarioIdNew);
            }
            apuesta = em.merge(apuesta);
            if (juegoIdOld != null && !juegoIdOld.equals(juegoIdNew)) {
                juegoIdOld.getApuestaCollection().remove(apuesta);
                juegoIdOld = em.merge(juegoIdOld);
            }
            if (juegoIdNew != null && !juegoIdNew.equals(juegoIdOld)) {
                juegoIdNew.getApuestaCollection().add(apuesta);
                juegoIdNew = em.merge(juegoIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getApuestaCollection().remove(apuesta);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getApuestaCollection().add(apuesta);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = apuesta.getId();
                if (findApuesta(id) == null) {
                    throw new NonexistentEntityException("The apuesta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apuesta apuesta;
            try {
                apuesta = em.getReference(Apuesta.class, id);
                apuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apuesta with id " + id + " no longer exists.", enfe);
            }
            Juego juegoId = apuesta.getJuegoId();
            if (juegoId != null) {
                juegoId.getApuestaCollection().remove(apuesta);
                juegoId = em.merge(juegoId);
            }
            Usuario usuarioId = apuesta.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getApuestaCollection().remove(apuesta);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(apuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Apuesta> findApuestaEntities() {
        return findApuestaEntities(true, -1, -1);
    }

    public List<Apuesta> findApuestaEntities(int maxResults, int firstResult) {
        return findApuestaEntities(false, maxResults, firstResult);
    }

    private List<Apuesta> findApuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apuesta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Apuesta findApuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getApuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apuesta> rt = cq.from(Apuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
