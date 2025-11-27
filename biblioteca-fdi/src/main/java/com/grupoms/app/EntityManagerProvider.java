package com.grupoms.app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {

	private static final EntityManager em = Persistence.createEntityManagerFactory("CentroCivicoJPA").createEntityManager();

	public static EntityManager getEntityManager() {
		return em;
	}
	
}
