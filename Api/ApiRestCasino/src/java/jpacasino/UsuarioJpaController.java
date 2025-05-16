/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacasino;

import utils.UsuarioUtils;
import classRecord.UsuarioRecord;
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
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jpacasino.exceptions.IllegalOrphanException;
import jpacasino.exceptions.NonexistentEntityException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author danie
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getApuestaCollection() == null) {
            usuario.setApuestaCollection(new ArrayList<Apuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Apuesta> attachedApuestaCollection = new ArrayList<Apuesta>();
            for (Apuesta apuestaCollectionApuestaToAttach : usuario.getApuestaCollection()) {
                apuestaCollectionApuestaToAttach = em.getReference(apuestaCollectionApuestaToAttach.getClass(), apuestaCollectionApuestaToAttach.getId());
                attachedApuestaCollection.add(apuestaCollectionApuestaToAttach);
            }
            usuario.setApuestaCollection(attachedApuestaCollection);
            em.persist(usuario);
            for (Apuesta apuestaCollectionApuesta : usuario.getApuestaCollection()) {
                Usuario oldUsuarioIdOfApuestaCollectionApuesta = apuestaCollectionApuesta.getUsuarioId();
                apuestaCollectionApuesta.setUsuarioId(usuario);
                apuestaCollectionApuesta = em.merge(apuestaCollectionApuesta);
                if (oldUsuarioIdOfApuestaCollectionApuesta != null) {
                    oldUsuarioIdOfApuestaCollectionApuesta.getApuestaCollection().remove(apuestaCollectionApuesta);
                    oldUsuarioIdOfApuestaCollectionApuesta = em.merge(oldUsuarioIdOfApuestaCollectionApuesta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Collection<Apuesta> apuestaCollectionOld = persistentUsuario.getApuestaCollection();
            Collection<Apuesta> apuestaCollectionNew = usuario.getApuestaCollection();
            List<String> illegalOrphanMessages = null;
            for (Apuesta apuestaCollectionOldApuesta : apuestaCollectionOld) {
                if (!apuestaCollectionNew.contains(apuestaCollectionOldApuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Apuesta " + apuestaCollectionOldApuesta + " since its usuarioId field is not nullable.");
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
            usuario.setApuestaCollection(apuestaCollectionNew);
            usuario = em.merge(usuario);
            for (Apuesta apuestaCollectionNewApuesta : apuestaCollectionNew) {
                if (!apuestaCollectionOld.contains(apuestaCollectionNewApuesta)) {
                    Usuario oldUsuarioIdOfApuestaCollectionNewApuesta = apuestaCollectionNewApuesta.getUsuarioId();
                    apuestaCollectionNewApuesta.setUsuarioId(usuario);
                    apuestaCollectionNewApuesta = em.merge(apuestaCollectionNewApuesta);
                    if (oldUsuarioIdOfApuestaCollectionNewApuesta != null && !oldUsuarioIdOfApuestaCollectionNewApuesta.equals(usuario)) {
                        oldUsuarioIdOfApuestaCollectionNewApuesta.getApuestaCollection().remove(apuestaCollectionNewApuesta);
                        oldUsuarioIdOfApuestaCollectionNewApuesta = em.merge(oldUsuarioIdOfApuestaCollectionNewApuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Apuesta> apuestaCollectionOrphanCheck = usuario.getApuestaCollection();
            for (Apuesta apuestaCollectionOrphanCheckApuesta : apuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Apuesta " + apuestaCollectionOrphanCheckApuesta + " in its apuestaCollection field has a non-nullable usuarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public UsuarioRecord findUsuario(UsuarioRecord usuarioRecord) {
        try (EntityManager em = getEntityManager()) {
            UsuarioRecord usuarioEncontrado = consultaUsuario(em, usuarioRecord.correo());

            return usuarioEncontrado != null && comprobarContrasenyaUsuarioExistente(
                    usuarioEncontrado.contrasenya(),
                    usuarioRecord.contrasenya()
            ) ? usuarioEncontrado : null;
        }
    }

    public Usuario findUsuarioEliminar(UsuarioRecord usuarioRecord) {
        try (EntityManager em = getEntityManager()) {
            Usuario usuarioEncontrado = consultaUsuario(em, usuarioRecord.correo(), true);

            return usuarioEncontrado != null && comprobarContrasenyaUsuarioExistente(
                    usuarioEncontrado.getContrasenya(),
                    usuarioRecord.contrasenya()
            ) ? usuarioEncontrado : null;
        }
    }

    public boolean comprobarContrasenyaUsuarioExistente(String contrasenyaEncriptada, String contrasenyaPlano) {
        return BCrypt.checkpw(
                contrasenyaPlano, contrasenyaEncriptada
        );
    }

    public <T> T consultaUsuario(EntityManager em, String correo) {
        return consultaUsuario(em, correo, false);
    }

    public <T> T consultaUsuario(EntityManager em, String correo, Boolean isEliminar) {
        try {
            TypedQuery<Usuario> consulta = em.createNamedQuery(
                    "Usuario.findByCorreo", Usuario.class
            );

            consulta.setParameter("correo", correo);
            
            Usuario encontrado = consulta.getSingleResult();

            if (isEliminar) {
                return (T) encontrado;
            }

            return (T) UsuarioUtils.toUsuarioRecord(encontrado);
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UsuarioRecord findUsuario(String correo) {
        try (EntityManager em = getEntityManager()) {
            return consultaUsuario(em, correo);
        }
    }
}
