package com.grupoms.app.negocio.ClaseJPA;

import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.negocio.assembler.ClaseAssembler;
import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ClaseSAImp implements ClaseSA {

    // ---- 1) Alta Clase ----

    @Override
    public Integer altaClase(TClase clase) {
        BOClase claseExistente = null;
        Integer id = -1;

        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            TypedQuery<BOClase> query = em.createNamedQuery(
                    "com.grupoms.app.negocio.claseJPA.BOClase.findByInstance",
                    BOClase.class
            );
            query.setParameter("tipo", clase.getTipo());
            query.setParameter("fechaInicio", clase.getFechaInicio());

            try {
                claseExistente = query.getSingleResult();
            } catch (Exception e) {
            }

            if (claseExistente != null) {
                if (!claseExistente.getActivo()) {
                    claseExistente.setActivo(true);
                    id = claseExistente.getId();
                } else {
                    throw new IllegalStateException(
                            "La clase de tipo " + clase.getTipo() +
                            " con fecha de inicio " + clase.getFechaInicio() +
                            " ya existe y está activa"
                    );
                }
            } else {
                BOClase nuevaClase = new BOClase(clase);
                em.persist(nuevaClase);
                em.flush();   
                id = nuevaClase.getId();
            }

            t.commit();
        } catch (Exception e) {
            if (t.isActive())
                t.rollback();
        } finally {
            em.close();
        }

        return id;
    }

    // ---- 2) Baja Clase ----

    @Override
    public Integer bajaClase(Integer id) {
        int res = -1;

        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            BOClase clase = em.find(BOClase.class, id);
            if (clase != null && Boolean.TRUE.equals(clase.getActivo())) {
                clase.setActivo(false);
                res = 1; 
            } else {
                res = 0; 
            }

            t.commit();
        } catch (Exception e) {
            if (t.isActive())
                t.rollback();
            
        } finally {
            em.close();
        }

        return res;
    }


    @Override
    public Integer modificarClase(TClase clase) {
        Integer id = -1;

        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            BOClase claseExistente = em.find(BOClase.class, clase.getId());
            if (claseExistente != null) {

                claseExistente.setTipo(clase.getTipo());
                claseExistente.setFechaInicio(clase.getFechaInicio());
                claseExistente.setDuracion(clase.getDuracion());


                em.merge(claseExistente);
                id = claseExistente.getId();
            } else {
                throw new IllegalStateException(
                        "La clase con ID " + clase.getId() + " no existe"
                );
            }

            t.commit();
        } catch (Exception e) {
            if (t.isActive())
                t.rollback();
            throw e; 
        } finally {
            em.close();
        }

        return id;
    }

    // ---- 4) Mostrar Clase ----

    @Override
    public TClase mostrarClase(Integer id) {
        if (id == null || id < 0) return null;

        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        BOClase clase = em.find(BOClase.class, id);

        if (clase == null) {
            em.close();
            return null;
        }

        TClase dto = ClaseAssembler.entityToTransfer(clase);
        em.close();
        return dto;
    }

    // ---- 5) Listar Clases ----

    @Override
    public List<TClase> listarClase() {
        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        t.begin();
       
        final TypedQuery<BOClase> query = em.createNamedQuery(
                "com.grupoms.app.negocio.claseJPA.BOClase.findAll",
                BOClase.class
        );

        List<TClase> lista = query.getResultList()
                .stream()
                .map(ClaseAssembler::entityToTransfer)
                .collect(Collectors.toList());

        t.commit();
        em.close();

        return lista;
    }

    // ---- 6) Vincular Ejemplar a Clase ----

    @Override
    public Integer vincularEjemplarAClase(Integer idClase, Integer idEjemplar) {
        int res = -1;

        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            BOClase clase = em.find(BOClase.class, idClase);
            BOEjemplar ejemplar = em.find(BOEjemplar.class, idEjemplar);

            if (clase == null || ejemplar == null) {
                res = 0; // Not found
            } else {
          
                if (ejemplar.getClase() != null &&
                    ejemplar.getClase().getId().equals(idClase)) {
                    res = 0; 
                } else {
                    ejemplar.setClase(clase);
                    em.merge(ejemplar);
                    res = 1;
                }
            }

            t.commit();
        } catch (Exception e) {
            if (t.isActive())
                t.rollback();
        } finally {
            em.close();
        }

        return res;
    }

    // ---- 7) Desvincular Ejemplar de Clase ----

    @Override
    public Integer desvincularEjemplarDeClase(Integer idClase, Integer idEjemplar) {
        int res = -1;

        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            BOEjemplar ejemplar = em.find(BOEjemplar.class, idEjemplar);

            if (ejemplar == null || ejemplar.getClase() == null ||
                !ejemplar.getClase().getId().equals(idClase)) {
                res = 0; // Not found or not linked to this class
            } else {
                // Remove link
                ejemplar.setClase(null);
                em.merge(ejemplar);
                res = 1; // Unlinked successfully
            }

            t.commit();
        } catch (Exception e) {
            if (t.isActive())
                t.rollback();
            // Optionally log
        } finally {
            em.close();
        }

        return res;
    }
}
