package com.grupoms.app.negocio.salaJPA;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.assembler.ClaseAssembler;
import com.grupoms.app.negocio.assembler.SalaAssembler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException; // Importante importar esto
import jakarta.persistence.TypedQuery;

public class SalaSAImp implements SalaSA {

    @Override
    public Integer altaSala(TSala sala) {
        BOSala salaExistente = null;
        Integer id = -1;
        
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        
        try {
            t.begin();
            // CORRECCIÓN 1: Typo arreglado "com.grupoms..." (antes ponía grpoms)
            TypedQuery<BOSala> query = em.createNamedQuery("com.grupoms.app.negocio.salaJPA.BOSala.findByName", BOSala.class);
            query.setParameter("nombre", sala.getNombre());
            
            try {
                salaExistente = query.getSingleResult();
            } catch(NoResultException e) {
                salaExistente = null; // Controlamos explícitamente que no existe
            }
            
            if(salaExistente != null) {
                if(!salaExistente.getActivo()) {
                    salaExistente.setActivo(true);
                    salaExistente.setCapacidad(sala.getCapacidad()); 
                    id = salaExistente.getId();
                } else {
                    // CORRECCIÓN 2: No lanzamos excepción, hacemos rollback y devolvemos -1
                    // para que el Command genere el evento ALTA_SALA_KO
                    t.rollback();
                    return -1; 
                }
            } else {
                BOSala nuevaSala = new BOSala();
                nuevaSala.setNombre(sala.getNombre());
                nuevaSala.setCapacidad(sala.getCapacidad());
                nuevaSala.setActivo(true);
                
                em.persist(nuevaSala);
                // em.flush(); // No es necesario si hacemos commit
                t.commit(); // El commit genera el ID
                id = nuevaSala.getId();
            }
            
            // Si la transacción sigue abierta (caso de reactivación), confirmamos
            if(t.isActive()) t.commit();
            
        } catch(Exception e) {
            if(t.isActive()) t.rollback();
            e.printStackTrace();
            return -1; // Devolvemos error controlado
        } finally {
            em.close();
        }
        return id;
    }

    @Override
    public Integer bajaSala(Integer id) {
        int res = -1;
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            
            BOSala sala = em.find(BOSala.class, id);
            
            if(sala != null && sala.getActivo()) {
                // Comprobamos si tiene clases activas antes de borrar
                // Usamos una query dinámica simple, es correcto aquí
                String jpql = "SELECT COUNT(c) FROM BOClase c WHERE c.sala.id = :idSala AND c.activo = true";
                Long numClases = em.createQuery(jpql, Long.class)
                                   .setParameter("idSala", id)
                                   .getSingleResult();

                if(numClases > 0) {
                    res = -2; // Código de error: "Tiene clases asignadas"
                    t.rollback(); // Cancelamos
                } else {
                    sala.setActivo(false); // Baja lógica
                    t.commit();
                    res = 1; // Éxito
                }
            } else {
                t.rollback(); // No existe o ya está inactiva
            }
        } catch(Exception e) {
            if(t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
        return res;
    }

    @Override
    public Integer modificarSala(TSala sala) {
        Integer id = -1;
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        
        try {
            t.begin();
            BOSala s = em.find(BOSala.class, sala.getId());
             
            // CORRECCIÓN 3: Validación correcta y evitar cerrar el EM manualmente aquí
            if(s == null || !s.getActivo()) {
                t.rollback();
                return -1; // Sala no encontrada o inactiva
            } else {
                // Modificamos
                s.setNombre(sala.getNombre());
                s.setCapacidad(sala.getCapacidad());
                
                // Si permites reactivar desde modificar:
                if (sala.getActivo() != null) { 
                    s.setActivo(sala.getActivo());
                }
                
                t.commit();
                id = sala.getId();
            }
        } catch (Exception e) {
            if(t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
        return id;
    }

    @Override
    public TSala mostrarSala(Integer id) {
        if(id == null || id < 0) return null;
        
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        try {
            BOSala sala = em.find(BOSala.class, id);
            
            if(sala == null || !sala.getActivo()) return null; // Filtrar inactivos si es necesario
            
            return SalaAssembler.entityToTransfer(sala);
        } finally {
            em.close();
        }
    }

    @Override
    public List<TSala> listarSala() {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        // Nota: Para listar no es estrictamente obligatorio abrir transacción, 
        // pero no hace daño. Lo dejo sin transacción para lectura simple.
        
        List<TSala> lista = Collections.emptyList();
        try {
            // CORRECCIÓN 4: Typo arreglado "com.grupoms..." (antes ponía grpoms)
            TypedQuery<BOSala> query = em.createNamedQuery("com.grupoms.app.negocio.salaJPA.BOSala.findAll", BOSala.class);
            
            lista = query.getResultList().stream()
                    .map(SalaAssembler::entityToTransfer)
                    .collect(Collectors.toList());
                    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return lista;
    }
    
    @Override
    public List<TClase> mostrarClasesPorSala(Integer idSala) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        List<TClase> lista = Collections.emptyList();
        
        try {
            // Usamos la NamedQuery que definiste en BOClase
            final TypedQuery<BOClase> query = em.createNamedQuery("com.grupoms.app.negocio.claseJPA.BOClase.findBySala", BOClase.class);
            query.setParameter("idSala", idSala);
            
            lista = query.getResultList()
                    .stream()
                    .map(ClaseAssembler::entityToTransfer)
                    .collect(Collectors.toList());
                    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return lista;
    }
}