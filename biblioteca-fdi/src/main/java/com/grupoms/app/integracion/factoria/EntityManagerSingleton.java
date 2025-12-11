package com.grupoms.app.integracion.factoria;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerSingleton {

	private static EntityManagerFactory em = Persistence.createEntityManagerFactory("CentroCivicoJPA");

	public static EntityManagerFactory getEMF() {
		return em;
	}

	public static void setEMF(EntityManagerFactory emf) {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = emf;
	}

	public static void reset() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = null;
	}

}
