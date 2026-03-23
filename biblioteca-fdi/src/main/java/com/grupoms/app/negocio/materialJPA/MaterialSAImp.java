package com.grupoms.app.negocio.materialJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.assembler.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
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
	            if (materialExistente.getActivo()) {
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
	                    if (libroExistente.getActivo()) {
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
	                    if (pinturaExistente.getActivo()) {
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
			List<BOEjemplar>listaV = new ArrayList<>();

			List<BOEjemplar>lista = material.getEjemplares();
			for(BOEjemplar e: lista) {
				if(e.getActivo()) {
					listaV.add(e);
				}
			}
			if(listaV.size()>0) {
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
			em.lock(bo, LockModeType.OPTIMISTIC);
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

	    try {
	        t.begin();

	        // 1. Buscar el material original
	        BOMaterial original = em.find(BOMaterial.class, material.getID());

	        if (original == null || !original.getActivo()) {
	            return -1; // No existe o está inactivo
	        }

	        // 2. Comprobación de nombre duplicado
	        TypedQuery<BOMaterial> qNombre = em.createNamedQuery(
	                "com.grupoms.app.negocio.materialJPA.BOMaterial.findByName",
	                BOMaterial.class
	        );
	        qNombre.setParameter("nombre", material.getNombre());

	        BOMaterial conflictoNombre = null;
	        try {
	            conflictoNombre = qNombre.getSingleResult();
	        } catch (Exception ignored) {}

	        if (conflictoNombre != null && !conflictoNombre.getID().equals(material.getID())) {
	            if (conflictoNombre.getActivo()) {
	                return -2; // Nombre ya existe en otro material activo
	            }
	        }

	        // 3. Validaciones específicas según tipo
	        if (material instanceof TLibro libroDTO) {

	            // Comprobar ISBN duplicado
	            TypedQuery<BOLibro> qISBN = em.createNamedQuery(
	                    "com.grupoms.app.negocio.materialJPA.BOLibro.findByISBN",
	                    BOLibro.class
	            );
	            qISBN.setParameter("isbn", libroDTO.getISBN());

	            BOLibro conflictoISBN = null;
	            try {
	                conflictoISBN = qISBN.getSingleResult();
	            } catch (Exception ignored) {}

	            if (conflictoISBN != null && !conflictoISBN.getID().equals(material.getID())) {
	                if (conflictoISBN.getActivo()) {
	                    return -3; // ISBN ya existe
	                }
	            }

	            // Actualizar datos del libro
	            BOLibro boLibro = (BOLibro) original;
	            boLibro.setISBN(libroDTO.getISBN());
	            boLibro.setEditorial(libroDTO.getEditorial());

	        } else if (material instanceof TPintura pinturaDTO) {

	            // Comprobar número duplicado
	            TypedQuery<BOPintura> qNumero = em.createNamedQuery(
	                    "com.grupoms.app.negocio.materialJPA.BOPintura.findByNumero",
	                    BOPintura.class
	            );
	            qNumero.setParameter("numero", pinturaDTO.getNumero());

	            BOPintura conflictoNumero = null;
	            try {
	                conflictoNumero = qNumero.getSingleResult();
	            } catch (Exception ignored) {}

	            if (conflictoNumero != null && !conflictoNumero.getID().equals(material.getID())) {
	                if (conflictoNumero.getActivo()) {
	                    return -4; // Número de pintura ya existe
	                }
	            }

	            // Actualizar datos de pintura
	            BOPintura boPintura = (BOPintura) original;
	            boPintura.setFecha(pinturaDTO.getFecha());
	            boPintura.setNumero(pinturaDTO.getNumero());
	        }

	        // 4. Actualizar datos comunes
	        original.setNombre(material.getNombre());
	        original.setAutor(material.getAutor());
	        original.setTipoMaterial(material.getTipoMaterial());

	        t.commit();
	        id = material.getID();

	    } catch (Exception e) {
	        if (t.isActive()) t.rollback();
	        throw e;
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
		em.lock(material, LockModeType.OPTIMISTIC);
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
