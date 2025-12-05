package com.grupoms.app.integracion.factoria;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class EntityManagerSingleton {

	private static final EntityManagerFactory em = Persistence.createEntityManagerFactory("CentroCivicoJPA");

	public static EntityManagerFactory getEMF() {
		return em;
	}
    
}
