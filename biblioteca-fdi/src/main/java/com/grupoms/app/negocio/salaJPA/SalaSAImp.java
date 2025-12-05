package com.grupoms.app.negocio.salaJPA;

import java.util.ArrayList;
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

public class SalaSAImp implements SalaSA{

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
				// No hay ninguna sala con ese nombre
			}
			
			if(salaExistente != null) {
				if(!salaExistente.getActivo()) {
					salaExistente.setActivo(true);
					id = salaExistente.getId();
				} else {
					throw new IllegalArgumentException("La sala con nombre " + sala.getNombre() + " ya existe");
				}
			} else {
				em.persist(sala);
				em.flush();
				id = sala.getId();
			}
			t.commit();
		} catch(Exception e) {
			if(t.isActive())
				t.rollback();
			throw e;
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
					if(!sala.getClases().isEmpty()) 
						res = -2;
					else {
						sala.setActivo(false);
						res = 1;
					}
						
				}
			}
			t.commit();
		} catch(Exception e) {
			if(t.isActive()) 
				t.rollback();
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
				 s.setNombre(sala.getNombre());
				 s.setCapacidad(sala.getCapacidad());
				 s.setActivo(sala.getActivo());
			 }
			 t.commit();
			 id = sala.getId();
		 } finally {
			 em.close();
		 }
		return id;
	}

	@Override
	public TSala mostrarSala(Integer id) {
		if(id == null || id < 0)
			return null;
		
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		BOSala sala = em.find(BOSala.class, id);
		
		if(sala == null) {
			em.close();
			return null;
		}
		
		TSala dto = SalaAssembler.entityToTransfer(sala);
		return dto;
	}

	@Override
	public List<TSala> listarSala() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		final TypedQuery<BOSala> query = em.createNamedQuery("com.grpoms.app.negocio.salaJPA.BOSala.findAll", BOSala.class);
		List<TSala> lista = query.getResultList().stream().map(bo -> SalaAssembler.entityToTransfer(bo)).collect(Collectors.toList());
		
		return lista;
	}

	@Override
	public Boolean vincularClaseASala(Integer idSala, Integer idClase) {
	    boolean exito = false;
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();
	    
	    try {
	        t.begin();
	        
	        BOSala sala = em.find(BOSala.class, idSala);
	        BOClase clase = em.find(BOClase.class, idClase);

	        if (sala != null && sala.getActivo() && clase != null && clase.getActivo()) {
	            
	            if (!sala.getClases().contains(clase)) {
	            	sala.getClases().add(clase); 
	                exito = true;
	            }
	        }
	        
	        t.commit();
	        
	    } catch (Exception e) {
	        if (t.isActive())
	            t.rollback();
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    
	    return exito;
	}
	
	@Override
	public Boolean desvincularClaseDeSala(Integer idSala, Integer idClase) {
	    boolean exito = false;
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();
	    
	    try {
	        t.begin();
	        
	        BOSala sala = em.find(BOSala.class, idSala);
	        BOClase clase = em.find(BOClase.class, idClase);
	        
	        if (sala != null && clase != null) {
	            if (sala.getClases().contains(clase)) {
	                sala.getClases().remove(clase);
	                exito = true;
	            }
	        }
	        
	        t.commit();
	    } catch (Exception e) {
	        if (t.isActive())
	            t.rollback();
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	    
	    return exito;
	}

	@Override
	public List<TClase> mostrarClasesPorSala(Integer idSala) {
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();
	    t.begin();
	    
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
