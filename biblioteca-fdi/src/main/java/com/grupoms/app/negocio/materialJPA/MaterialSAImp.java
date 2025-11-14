package com.grupoms.app.negocio.materialJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.negocio.entityManager.EntityManagerSingleton;
import com.grupoms.app.negocio.materialJPA.Material;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

public class MaterialSAImp implements MaterialSA{

	@Override
	public Integer altaPintura(TMaterial material) {
		// TODO Auto-generated method stub
		return null;
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
		final TypedQuery<Material> query = em.createNamedQuery("Negocio.MaterialJPA.Material.findAll", Material.class);
		final List<TMaterial> lista = query.getResultList().stream().map(Material::entityToTransfer).collect(Collectors.toList());
		
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
		final TypedQuery<Material> query = em.createNamedQuery("Negocio.MaterialJPA.Material.findAll", Material.class);
		final List<TMaterial> lista = query.getResultList().stream().map(Material::entityToTransfer).collect(Collectors.toList());
		
		//actualizo la lista
		for(TMaterial m: lista) {
			if(m.getTipo() == tipo)
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
		
		//usamos optimistic para poder detectar cambios concurrentes
		Material m = em.find(Material.class,id, LockModeType.OPTIMISTIC);
		
		TMaterial res = null;
		//Si no lo encuentro, rollback de la transaccion
		if(m == null)t.rollback();
		else {
			res = m.entityToTransfer();
			t.commit();
		}
		
		em.close();
		return res;
	}

}
