package com.grupoms.app.negocio.EjemplarJPA;

import com.grupoms.app.EntityManagerProvider;
import com.grupoms.app.negocio.materialJPA.BOMaterial;

import jakarta.persistence.EntityManager;

public class EjemplarSAImp implements EjemplarSA {

	@Override
	public int altaEjemplar(TEjemplar ejemplar) {
		EntityManager em = EntityManagerProvider.getEntityManager();

		try {
			em.getTransaction().begin();
			BOMaterial boMaterial = em.find(BOMaterial.class, ejemplar.getIdMaterial());
			
			if (boMaterial == null) {
				em.getTransaction().rollback();
				return -1;
			}
			
			BOEjemplar boEjemplar = new BOEjemplar();
			boEjemplar.setEstado(ejemplar.getEstado());
			boEjemplar.setActivo(true);
			boEjemplar.setMaterial(boMaterial);
			
			em.persist(boEjemplar);
			em.getTransaction().commit();
			return boEjemplar.getId();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return -1;
		}
	}

}
