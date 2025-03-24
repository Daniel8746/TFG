/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacasino;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jpacasino.exceptions.IllegalOrphanException;
import jpacasino.exceptions.NonexistentEntityException;

/**
 *
 * @author danie
 */
public class JuegoJpaController implements Serializable {

    public JuegoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juego juego) {
        if (juego.getApuestaCollection() == null) {
            juego.setApuestaCollection(new ArrayList<Apuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Apuesta> attachedApuestaCollection = new ArrayList<Apuesta>();
            for (Apuesta apuestaCollectionApuestaToAttach : juego.getApuestaCollection()) {
                apuestaCollectionApuestaToAttach = em.getReference(apuestaCollectionApuestaToAttach.getClass(), apuestaCollectionApuestaToAttach.getId());
                attachedApuestaCollection.add(apuestaCollectionApuestaToAttach);
            }
            juego.setApuestaCollection(attachedApuestaCollection);
            em.persist(juego);
            for (Apuesta apuestaCollectionApuesta : juego.getApuestaCollection()) {
                Juego oldJuegoIdOfApuestaCollectionApuesta = apuestaCollectionApuesta.getJuegoId();
                apuestaCollectionApuesta.setJuegoId(juego);
                apuestaCollectionApuesta = em.merge(apuestaCollectionApuesta);
                if (oldJuegoIdOfApuestaCollectionApuesta != null) {
                    oldJuegoIdOfApuestaCollectionApuesta.getApuestaCollection().remove(apuestaCollectionApuesta);
                    oldJuegoIdOfApuestaCollectionApuesta = em.merge(oldJuegoIdOfApuestaCollectionApuesta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juego juego) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juego persistentJuego = em.find(Juego.class, juego.getId());
            Collection<Apuesta> apuestaCollectionOld = persistentJuego.getApuestaCollection();
            Collection<Apuesta> apuestaCollectionNew = juego.getApuestaCollection();
            List<String> illegalOrphanMessages = null;
            for (Apuesta apuestaCollectionOldApuesta : apuestaCollectionOld) {
                if (!apuestaCollectionNew.contains(apuestaCollectionOldApuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Apuesta " + apuestaCollectionOldApuesta + " since its juegoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Apuesta> attachedApuestaCollectionNew = new ArrayList<Apuesta>();
            for (Apuesta apuestaCollectionNewApuestaToAttach : apuestaCollectionNew) {
                apuestaCollectionNewApuestaToAttach = em.getReference(apuestaCollectionNewApuestaToAttach.getClass(), apuestaCollectionNewApuestaToAttach.getId());
                attachedApuestaCollectionNew.add(apuestaCollectionNewApuestaToAttach);
            }
            apuestaCollectionNew = attachedApuestaCollectionNew;
            juego.setApuestaCollection(apuestaCollectionNew);
            juego = em.merge(juego);
            for (Apuesta apuestaCollectionNewApuesta : apuestaCollectionNew) {
                if (!apuestaCollectionOld.contains(apuestaCollectionNewApuesta)) {
                    Juego oldJuegoIdOfApuestaCollectionNewApuesta = apuestaCollectionNewApuesta.getJuegoId();
                    apuestaCollectionNewApuesta.setJuegoId(juego);
                    apuestaCollectionNewApuesta = em.merge(apuestaCollectionNewApuesta);
                    if (oldJuegoIdOfApuestaCollectionNewApuesta != null && !oldJuegoIdOfApuestaCollectionNewApuesta.equals(juego)) {
                        oldJuegoIdOfApuestaCollectionNewApuesta.getApuestaCollection().remove(apuestaCollectionNewApuesta);
                        oldJuegoIdOfApuestaCollectionNewApuesta = em.merge(oldJuegoIdOfApuestaCollectionNewApuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = juego.getId();
                if (findJuego(id) == null) {
                    throw new NonexistentEntityException("The juego with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juego juego;
            try {
                juego = em.getReference(Juego.class, id);
                juego.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juego with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Apuesta> apuestaCollectionOrphanCheck = juego.getApuestaCollection();
            for (Apuesta apuestaCollectionOrphanCheckApuesta : apuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Juego (" + juego + ") cannot be destroyed since the Apuesta " + apuestaCollectionOrphanCheckApuesta + " in its apuestaCollection field has a non-nullable juegoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(juego);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juego> findJuegoEntities() {
        return findJuegoEntities(true, -1, -1);
    }

    public List<Juego> findJuegoEntities(int maxResults, int firstResult) {
        return findJuegoEntities(false, maxResults, firstResult);
    }

    private List<Juego> findJuegoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juego.class));
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

    public Juego findJuego(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juego.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuegoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juego> rt = cq.from(Juego.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
