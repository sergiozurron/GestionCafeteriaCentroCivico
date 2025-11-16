package com.grupoms.app.negocio.materialJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.negocio.assembler.*;
import com.grupoms.app.negocio.entityManager.EntityManagerSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class MaterialSAImp implements MaterialSA{

	@Override
	public synchronized Integer altaPintura(TMaterial material) {
		Integer id = -1;
		BOMaterial materialExistente=null;
		BOPintura pintura =null;

		//empiezo la transaccion
		EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			//verificamos si existe
			TypedQuery<BOMaterial> query = em.createNamedQuery("com.grupoms.app.negocio.materialJPA.BOMaterial.findByName", BOMaterial.class);
			query.setParameter("nombre", material.getNombre());
			try {
				materialExistente = query.getSingleResult();
			}catch(Exception e){
				//No hay material existente con ese nombre
			}
			if(materialExistente!=null) {
				if(!materialExistente.getActivo()) { //si no esta activo
					materialExistente.setActivo(true);
					em.merge(materialExistente);
					id = materialExistente.getID();
				}else {
					throw new IllegalStateException("La pintura con nombre "+ material.getNombre()+ " ya existe");
				}
				
			}else {
				pintura = new BOPintura(material);
				em.persist(pintura);
				em.flush();
				id= pintura.getID();
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
	public Integer altaLibro(TMaterial material) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer bajaMaterial(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TMaterial> listarMateriales() {
		//Empiezo la transaccion y creo el emf
		EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		//Ejecuto la query
		final TypedQuery<BOMaterial> query = em.createNamedQuery("com.grupoms.app.negocio.materialJPA.Material.findAll", BOMaterial.class);
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
	public List<TMaterial> listarMaterialTipo(Integer tipo) {
		//Empiezo la transaccion y creo el emf
		EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		List<TMaterial> listaFinal = new ArrayList<TMaterial>();
		
		//ejecuto la query
		final TypedQuery<BOMaterial> query = em.createNamedQuery("Negocio.MaterialJPA.Material.findAll", BOMaterial.class);
		final List<TMaterial> lista = query.getResultList().stream().map(MaterialAssembler::entityToTransfer).collect(Collectors.toList());
		
		//actualizo la lista
		for(TMaterial m: lista) {
			if(m.getTipoMaterial() == tipo)
				listaFinal.add(m);
		}
		
		//guardo la transaccion y cierro el em
		t.commit();
		em.close();
		return listaFinal;
	}

	@Override
	public Integer modificarMaterial(TMaterial material) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TMaterial mostrarMaterial(Integer id) {
		//Empiezo la transacccion
		EntityManager em = EntityManagerSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		return null;
		
		//usamos optimistic para poder detectar cambios concurrentes
		
	}

}
