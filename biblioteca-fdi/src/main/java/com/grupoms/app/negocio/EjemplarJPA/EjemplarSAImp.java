package com.grupoms.app.negocio.EjemplarJPA;

import com.grupoms.app.EntityManagerProvider;
import com.grupoms.app.negocio.materialJPA.BOMaterial;

import jakarta.persistence.EntityManager;

public class EjemplarSAImp implements EjemplarSA {

	@Override
	public Integer altaEjemplar(TEjemplar ejemplar) {
		EntityManager em = EntityManagerProvider.getEntityManager();

		try {
			em.getTransaction().begin();
			BOMaterial boMaterial = em.find(BOMaterial.class, ejemplar.getIdMaterial());

			if (boMaterial == null || !boMaterial.getActivo()) {
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

	@Override
	public Boolean bajaEjemplar(Integer idEjemplar) {
		EntityManager em = EntityManagerProvider.getEntityManager();

		try {
			em.getTransaction().begin();
			BOEjemplar boEjemplar = em.find(BOEjemplar.class, idEjemplar);

			if (boEjemplar == null || !boEjemplar.getActivo()) {
				em.getTransaction().rollback();
				return false;
			}

			boEjemplar.setActivo(false);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}
	}

}
