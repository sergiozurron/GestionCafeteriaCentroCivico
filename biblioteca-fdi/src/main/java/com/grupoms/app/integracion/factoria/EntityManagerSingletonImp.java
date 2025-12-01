package com.grupoms.app.integracion.factoria;

import jakarta.persistence.EntityManagerFactory;

public class EntityManagerSingletonImp extends EntityManagerSingleton {
	
	private EntityManagerFactory emFactory;
	
	public EntityManagerSingletonImp(EntityManagerFactory emf) {
		emFactory = emf;
	}
	
	@Override
	public EntityManagerFactory getEMF() {

		return emFactory;
	}

}
