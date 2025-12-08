package com.grupoms.app.negocio.salaJPA;

import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.assembler.ClaseAssembler;
import com.grupoms.app.negocio.assembler.SalaAssembler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
			TypedQuery<BOSala> query = em.createNamedQuery("com.grpoms.app.negocio.salaJPA.BOSala.findByName", BOSala.class);
			query.setParameter("nombre", sala.getNombre());
			
			try {
				salaExistente = query.getSingleResult();
			} catch(Exception e) {
				// No existe, perfecto.
			}
			
			if(salaExistente != null) {
				// Si existe pero está inactiva, la reactivamos
				if(!salaExistente.getActivo()) {
					salaExistente.setActivo(true);
					// Opcional: Actualizar capacidad si ha cambiado
					salaExistente.setCapacidad(sala.getCapacidad()); 
					id = salaExistente.getId();
				} else {
					throw new IllegalArgumentException("La sala con nombre " + sala.getNombre() + " ya existe");
				}
			} else {
				BOSala nuevaSala = new BOSala();
				nuevaSala.setNombre(sala.getNombre());
				nuevaSala.setCapacidad(sala.getCapacidad());
				nuevaSala.setActivo(true);
				
				em.persist(nuevaSala);
				em.flush(); // Fuerza a que se genere el ID
				id = nuevaSala.getId();
			}
			t.commit();
		} catch(Exception e) {
			if(t.isActive()) t.rollback();
			throw e; // Ojo: Es mejor lanzar excepciones controladas o RuntimeException
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
			
			if(sala != null) {
				if(sala.getActivo()) {
					String jpql = "SELECT COUNT(c) FROM BOClase c WHERE c.sala.id = :idSala AND c.activo = true";
					Long numClases = em.createQuery(jpql, Long.class)
							           .setParameter("idSala", id)
							           .getSingleResult();

					if(numClases > 0) {
						res = -2;
					} else {
						sala.setActivo(false);
						res = 1;
					}
				}
			}
			t.commit();
		} catch(Exception e) {
			if(t.isActive()) t.rollback();
			e.printStackTrace();
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
		t.begin();
		try {
			BOSala s = em.find(BOSala.class, sala.getId());
			 
			if(s == null) {
				em.close();
				throw new IllegalArgumentException("El id de la sala no existe");
			} else {
				// Solo modificamos datos propios de la Sala
				s.setNombre(sala.getNombre());
				s.setCapacidad(sala.getCapacidad());
				
				// Si reactivamos la sala manualmente en modificar:
				if (sala.getActivo() != null) { 
					s.setActivo(sala.getActivo());
				}
			}
			t.commit();
			id = sala.getId();
		} catch (Exception e) {
			if(t.isActive()) t.rollback();
			throw e;
		} finally {
			em.close();
		}
		return id;
	}

	@Override
	public TSala mostrarSala(Integer id) {
		if(id == null || id < 0) return null;
		
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		// No hace falta transacción para lecturas simples
		try {
			BOSala sala = em.find(BOSala.class, id);
			
			if(sala == null) return null;
			
			return SalaAssembler.entityToTransfer(sala);
		} finally {
			em.close();
		}
	}

	@Override
	public List<TSala> listarSala() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		// Es buena práctica abrir transacción en listar para asegurar consistencia de lectura
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		List<TSala> lista = null;
		try {
			TypedQuery<BOSala> query = em.createNamedQuery("com.grpoms.app.negocio.salaJPA.BOSala.findAll", BOSala.class);
			
			lista = query.getResultList().stream()
					.map(bo -> SalaAssembler.entityToTransfer(bo))
					.collect(Collectors.toList());
			
			t.commit();
		} finally {
			em.close();
		}
		return lista;
	}
	
	// Este método SÍ tiene sentido aquí, porque es una consulta sobre las clases
	// filtrando por una sala concreta.
	@Override
	public List<TClase> mostrarClasesPorSala(Integer idSala) {
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();
	    t.begin();
	    
	    // Usamos la NamedQuery que definiste en BOClase (o deberías haber definido allí)
	    final TypedQuery<BOClase> query = em.createNamedQuery("com.grupoms.app.negocio.claseJPA.BOClase.findBySala", BOClase.class);
	    query.setParameter("idSala", idSala);
	    
	    List<TClase> lista = query.getResultList()
	            .stream()
	            .map(bo -> ClaseAssembler.entityToTransfer(bo))
	            .collect(Collectors.toList());
	            
	    t.commit();
	    em.close();
	    return lista;
	}
}