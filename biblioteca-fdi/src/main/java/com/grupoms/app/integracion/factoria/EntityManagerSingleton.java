package com.grupoms.app.integracion.factoria;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerSingleton {

	private static EntityManagerFactory em;

	public static synchronized EntityManagerFactory getEMF() {
		if (em == null) {
			em = Persistence.createEntityManagerFactory("CentroCivicoJPA");
		}
		return em;
	}

	public static synchronized void setEMF(EntityManagerFactory emf) {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = emf;
	}

	public static synchronized void reset() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = null;
	}

}
