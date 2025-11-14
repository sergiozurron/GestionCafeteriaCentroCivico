package com.grupoms.app.negocio.entityManager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public abstract class EntityManagerSingleton {

    private static EntityManagerSingleton instance;

    public synchronized static EntityManagerSingleton getInstance(){
        if(instance == null)
            instance = new EntityManagerSingletonImp(Persistence.createEntityManagerFactory("CentroCivicoJPA"));
        return instance;
    }

    public abstract EntityManagerFactory getEMF();
    
}
