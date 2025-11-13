package com.grupoms.app.negocio.entityManager;

public abstract class EntityManager {

    private static EntityManager instance;

    public synchronized static getInstance(){
        if(instance == null)
            instance = new EntityManagerImp(Persistence.createEMF("CentroCivicoJPA"));
        return instance;
    }

    public abstract getEMF();
    
}
