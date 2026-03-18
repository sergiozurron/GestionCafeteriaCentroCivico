package com.grupoms.app.negocio.materialJPA;

import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.assembler.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class MaterialSAImp implements MaterialSA {

	@Override
	public Integer altaMaterial(TMaterial material) {
	    BOMaterial materialExistente = null;
	    Integer id = -1;

	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();

	    try {
	        t.begin();

	        // 1. Comprobación por nombre (común a todos los materiales)
	        TypedQuery<BOMaterial> queryNombre = em.createNamedQuery(
	                "com.grupoms.app.negocio.materialJPA.BOMaterial.findByName",
	                BOMaterial.class
	        );
	        queryNombre.setParameter("nombre", material.getNombre());

	        try {
	            materialExistente = queryNombre.getSingleResult();
	        } catch (Exception ignored) {}

	        // Si existe por nombre
	        if (materialExistente != null) {
	            if (!materialExistente.getActivo()) {
	                materialExistente.setActivo(true);
	                id = materialExistente.getID();
	            } else {
	                return -1; //nombre ya existe
	            }
	        } else {

	            // 2. Comprobación adicional por ISBN si es libro
	            if (material instanceof TLibro libroDTO) {

	                TypedQuery<BOLibro> queryISBN = em.createNamedQuery(
	                        "com.grupoms.app.negocio.materialJPA.BOLibro.findByISBN",
	                        BOLibro.class
	                );
	                queryISBN.setParameter("isbn", libroDTO.getISBN());

	                BOLibro libroExistente = null;
	                try {
	                    libroExistente = queryISBN.getSingleResult();
	                } catch (Exception ignored) {}

	                if (libroExistente != null) {
	                    if (!libroExistente.getActivo()) {
	                        libroExistente.setActivo(true);
	                        id = libroExistente.getID();
	                    } else {
	                       return -2; //ya existe el isbn
	                    }
	                } else {
	                    BOLibro libro = new BOLibro(libroDTO);
	                    em.persist(libro);
	                    em.flush();
	                    id = libro.getID();
	                }

	            // 3. Comprobación adicional por número si es pintura
	            } else if (material instanceof TPintura pinturaDTO) {

	                TypedQuery<BOPintura> queryNumero = em.createNamedQuery(
	                        "com.grupoms.app.negocio.materialJPA.BOPintura.findByNumero",
	                        BOPintura.class
	                );
	                queryNumero.setParameter("numero", pinturaDTO.getNumero());

	                BOPintura pinturaExistente = null;
	                try {
	                    pinturaExistente = queryNumero.getSingleResult();
	                } catch (Exception ignored) {}

	                if (pinturaExistente != null) {
	                    if (!pinturaExistente.getActivo()) {
	                        pinturaExistente.setActivo(true);
	                        id = pinturaExistente.getID();
	                    } else {
	                        return -3; //ya existe el numero de pintura
	                    }
	                } else {
	                    BOPintura pintura = new BOPintura(pinturaDTO);
	                    em.persist(pintura);
	                    em.flush();
	                    id = pintura.getID();
	                }
	            }
	        }

	        t.commit();

	    } catch (Exception e) {
	        if (t.isActive()) t.rollback();
	        throw e;
	    } finally {
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
				return -1;
			}
			int size = material.getEjemplares().size();
			if (size>0) { //esto funciona?
				t.rollback();
				return -2;
			}

			material.setActivo(false);
			t.commit();
			return 1;
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public List<TMaterial> listarMateriales() {

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();

		final TypedQuery<BOMaterial> query = em
				.createNamedQuery("com.grupoms.app.negocio.materialJPA.BOMaterial.findAll", BOMaterial.class);
		List<TMaterial> lista = query.getResultList().stream().map(bo -> {
			if (bo instanceof BOLibro libro)
				return LibroAssembler.toDTO(libro);
			else if (bo instanceof BOPintura pintura)
				return PinturaAssembler.toDTO(pintura);
			else
				return MaterialAssembler.entityToTransfer(bo);
		}).collect(Collectors.toList());

		t.commit();

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
				throw new IllegalArgumentException("El ID del material no existe o no está activo.");
			} else {
				m = em.find(BOMaterial.class,material.getNombre());
				if(m!=null) {
					em.close();
					throw new IllegalArgumentException("El nombre del material es conflictivo");
				}
				m.setNombre(material.getNombre());
				m.setAutor(material.getAutor());
				m.setTipoMaterial(material.getTipoMaterial());

				if (material.getTipoMaterial() == 0) {
					TPintura pintura = (TPintura) material;
					BOPintura boPintura = (BOPintura) m;
					boPintura.setFecha(pintura.getFecha());
					boPintura.setNumero(pintura.getNumero());

				} else if (material.getTipoMaterial() == 1) {
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
		if (id == null || id < 0)
			return null;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		BOMaterial material = em.find(BOMaterial.class, id);
		if (material == null || !material.getActivo()) {
			em.close();
			return null;
		}
		TMaterial dto;
		if (material instanceof BOLibro libro) {
			dto = LibroAssembler.toDTO(libro);
		} else if (material instanceof BOPintura pintura) {
			dto = PinturaAssembler.toDTO(pintura);
		} else {
			dto = MaterialAssembler.entityToTransfer(material);
		}
		return dto;

	}

}
