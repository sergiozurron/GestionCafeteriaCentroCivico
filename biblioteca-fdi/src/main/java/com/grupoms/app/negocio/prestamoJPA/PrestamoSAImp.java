package com.grupoms.app.negocio.prestamoJPA;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.assembler.PrestamoAssembler;
import com.grupoms.app.negocio.socioJPA.BOSocio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PrestamoSAImp implements PrestamoSA {

    @Override
    public Integer altaPrestamo(TPrestamo prestamo) {
        Integer id = -1;
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            BOSocio socio = em.find(BOSocio.class, prestamo.getIdSocio());
            if (socio == null || !socio.getActivo()) {
                t.rollback();
                return -1;
            }

            BOEjemplar ejemplar = em.find(BOEjemplar.class, prestamo.getIdEjemplar());
            if (ejemplar == null || !ejemplar.getActivo()) {
                t.rollback();
                return -1; 
            }

            if (!"DISPONIBLE".equalsIgnoreCase(ejemplar.getEstado())) {
                t.rollback();
                return -1; 
            }

            BOPrestamo boPrestamo = new BOPrestamo();
            boPrestamo.setSocio(socio);
            boPrestamo.setEjemplar(ejemplar);
            boPrestamo.setFechaInicial(prestamo.getFechaInicial()); // Usar la del transfer o new Date()
            boPrestamo.setFechaMaxima(prestamo.getFechaMaxima());
            boPrestamo.setActivo(true);
            boPrestamo.setPrecioMulta(0.0);
            
            ejemplar.setEstado("PRESTADO");

            em.persist(boPrestamo);
            
            socio.getPrestamos().add(boPrestamo);
            ejemplar.getPrestamos().add(boPrestamo);

            t.commit();
            id = boPrestamo.getId();

        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
        return id;
    }

    @Override
    public Integer bajaPrestamo(Integer idPrestamo) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            
            BOPrestamo bo = em.find(BOPrestamo.class, idPrestamo);

            if (bo == null || !bo.getActivo()) {
                t.rollback();
                return -1; 
            }

            bo.setActivo(false);

            if (bo.getFechaDevuelto() == null && bo.getEjemplar() != null) {
                bo.getEjemplar().setEstado("DISPONIBLE");
            }

            t.commit();
            return bo.getId(); 

        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
    }

    @Override
    public Integer modificarPrestamo(TPrestamo prestamo) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();

            BOPrestamo bo = em.find(BOPrestamo.class, prestamo.getId());

            if (bo == null || !bo.getActivo()) {
                t.rollback();
                return -1;
            }

            bo.setFechaInicial(prestamo.getFechaInicial());
            bo.setFechaMaxima(prestamo.getFechaMaxima());
            bo.setFechaDevuelto(prestamo.getFechaDevuelto());
            bo.setPrecioMulta(prestamo.getPrecioMulta());
            
            if (prestamo.getFechaDevuelto() != null && bo.getEjemplar() != null) {
                 if ("PRESTADO".equalsIgnoreCase(bo.getEjemplar().getEstado())) {
                     bo.getEjemplar().setEstado("DISPONIBLE");
                 }
            }

            t.commit();
            return bo.getId();

        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
    }

    @Override
    public TPrestamo mostrarPrestamo(Integer idPrestamo) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        try {
            BOPrestamo bo = em.find(BOPrestamo.class, idPrestamo);
            if (bo == null || !bo.getActivo()) {
                return null;
            }
            return PrestamoAssembler.toDTO(bo);
        } finally {
            em.close();
        }
    }

    @Override
    public List<TPrestamo> listarPrestamo() {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        try {
            TypedQuery<BOPrestamo> query = em.createQuery("SELECT p FROM BOPrestamo p WHERE p.activo = true", BOPrestamo.class);
            
            return query.getResultList().stream()
                    .map(PrestamoAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}