package com.grupoms.app.integracion.factoria;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class EntityManagerSingleton {

	private static EntityManagerFactory em = Persistence.createEntityManagerFactory("CentroCivicoJPA");

	public static EntityManagerFactory getEMF() {
		return em;
	}

	/**
	 * Permite inyectar un EntityManagerFactory (útil en tests para usar H2)
	 */
	public static void setEMF(EntityManagerFactory emf) {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = emf;
	}

	/**
	 * Cierra y resetea el EntityManagerFactory (para limpiar entre tests)
	 */
	public static void reset() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = null;
	}

}
