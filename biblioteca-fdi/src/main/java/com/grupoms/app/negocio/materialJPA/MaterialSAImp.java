package com.grupoms.app.negocio.materialJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.assembler.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class MaterialSAImp implements MaterialSA{

	@Override
	public Integer altaMaterial(TMaterial material) {
		BOMaterial materialExistente = null;
		Integer id = -1;
		BOPintura pintura=null;
		BOLibro libro =null;
		
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			TypedQuery<BOMaterial> query = em.createNamedQuery("com.grupoms.app.negocio.materialJPA.BOMaterial.findByName", BOMaterial.class);
			query.setParameter("nombre", material.getNombre());
			try {
				materialExistente = query.getSingleResult();
			}catch(Exception e){
				//No hay material existente con ese nombre
			}
			if(materialExistente!=null) { //si ya existe
				if(!materialExistente.getActivo()) { //si no esta activo, lo activo
					materialExistente.setActivo(true);
					id=materialExistente.getID();
				}else {
					throw new IllegalStateException("El material con nombre "+ material.getNombre()+ " ya existe");
				}	
			}else {
				 if (material instanceof TPintura pinturaDTO) {

			            pintura = new BOPintura(pinturaDTO);
			            em.persist(pintura);
			            em.flush();
			            id = pintura.getID();

			        } else if (material instanceof TLibro libroDTO) {

			            libro = new BOLibro(libroDTO);
			            em.persist(libro);
			            em.flush();
			            id = libro.getID();

			        }
			}
			t.commit();
		}catch(Exception e) {
			if(t.isActive())
				t.rollback();
			throw e;
		}finally {
			em.close();
		}
		return id;
	}
	
	
	@Override
	public Integer bajaMaterial(Integer id) throws Exception {
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();
	    try {
	        t.begin();

	        BOMaterial material = em.find(BOMaterial.class, id);

	        if (material == null || !material.getActivo()) {
	            t.rollback();
	            throw new Exception("El material no existe o ya está inactivo.");
	        }

	        if (!material.getEjemplares().isEmpty()) {
	            t.rollback();
	            throw new Exception("Elimine primero los ejemplares asociados a este material.");
	        }

	        material.setActivo(false);
	        t.commit();
	        return 1; 
	    } catch (Exception e) {
	        if (t.isActive()) t.rollback();
	        throw e; 
	    } finally {
	        em.close();
	    }
	}

	@Override
	public List<TMaterial> listarMateriales() {
		//Empiezo la transaccion y creo el emf
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		//Ejecuto la query
		final TypedQuery<BOMaterial> query = em.createNamedQuery("com.grupoms.app.negocio.materialJPA.BOMaterial.findAll", BOMaterial.class);
		List<TMaterial> lista = query
                .getResultList()
                .stream()
                .map(bo -> {
                    if (bo instanceof BOLibro libro)
                        return LibroAssembler.toDTO(libro);
                    else if (bo instanceof BOPintura pintura)
                        return PinturaAssembler.toDTO(pintura);
                    else
                        return MaterialAssembler.entityToTransfer(bo); // fallback
                })
                .collect(Collectors.toList());
		
		//guardo la transaccion
		t.commit();
		
		//cierro el em
		em.close();
		return lista;
	}
	
	@Override
	public Integer modificarMaterial(TMaterial material) {
	    Integer id = -1;
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();
	    t.begin();
	    try {
	        BOMaterial m = em.find(BOMaterial.class, material.getID());

	        if (m == null) {
	            em.close();
	            throw new IllegalArgumentException("El ID del material no existe.");
	        }
	        else {
		        // Actualizar campos comunes
		        m.setNombre(material.getNombre());
		        m.setAutor(material.getAutor());
		        m.setTipoMaterial(material.getTipoMaterial());
		        m.setActivo(true);
		        // Actualizar campos específicos según tipo
		        if (material.getTipoMaterial() == 0) {        // Pintura
		            TPintura pintura = (TPintura) material;
		            BOPintura boPintura = (BOPintura) m;
		            boPintura.setFecha(pintura.getFecha());
		            boPintura.setNumero(pintura.getNumero());
	
		        } else if (material.getTipoMaterial() == 1) { // Libro
		            TLibro libro = (TLibro) material;
		            BOLibro boLibro = (BOLibro) m;
		            boLibro.setISBN(libro.getISBN());
		            boLibro.setEditorial(libro.getEditorial());
		        }
	        }
	        t.commit();
	        id = material.getID();

	    } finally {
	        em.close();
	    }

	    return id;
	}


	@Override
	public TMaterial mostrarMaterial(Integer id) {
		if(id==null ||id<0)return null;
		//Empiezo la transacccion
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		BOMaterial material = em.find(BOMaterial.class, id);
		if(material==null) {
			em.close();
			return null;
		}
		TMaterial dto;
		if (material instanceof BOLibro libro) {
		    dto = LibroAssembler.toDTO(libro);  // devuelve TLibro
		} else if (material instanceof BOPintura pintura) {
		    dto = PinturaAssembler.toDTO(pintura); // devuelve TPintura
		} else {
		    dto = MaterialAssembler.entityToTransfer(material); // solo TMaterial
		}
		return dto;
		
		//usamos optimistic para poder detectar cambios concurrentes
		
	}

}
