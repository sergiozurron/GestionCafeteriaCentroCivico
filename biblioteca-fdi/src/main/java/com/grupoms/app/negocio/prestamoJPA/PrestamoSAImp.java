package com.grupoms.app.negocio.prestamoJPA;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PrestamoSAImp implements PrestamoSA {
    @Override
    public Integer altaPrestamo(TPrestamo prestamo) {
        BOPrestamo prestamoExistente=null;
        Integer id=-1;

        EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            TypedQuery<BOPrestamo> query=em.createNamedQuery("com.grupoms.app.negocio.prestamoJPA.BOPrestamo.findByIDs",BOPrestamo.class);
            query.setParameter("socio",prestamo.);

            try{
                prestamoExistente=query.getSingleResult();
            } catch (Exception e) {
                //
            }
            if(prestamoExistente!=null){
                if(!prestamoExistente.getActivo()) { //si no esta activo, lo activo
                    prestamoExistente.setActivo(true);
                    id=prestamoExistente.getId();
                }else {
                    throw new IllegalStateException("El socio con nombre y apellido "+ socio.getNombreYapellido()+ " ya existe");
                }
            }
            t.commit();
        } catch (Exception e) {
            if(t.isActive())
                t.rollback();
            throw e;
        }finally {
            em.close();
        }
        return id;
    }

    @Override
    public Integer bajaPrestamo(Integer idPrestamo) {
        return 0;
    }

    @Override
    public Integer modificarPrestamo(TPrestamo prestamo) {
        return 0;
    }

    @Override
    public TPrestamo mostrarPrestamo(Integer idPrestamo) {
        return null;
    }

    @Override
    public List<TPrestamo> listarPrestamo() {
        return List.of();
    }
}
