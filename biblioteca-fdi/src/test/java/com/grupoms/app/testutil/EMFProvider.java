package com.grupoms.app.testutil;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFProvider {
    public static EntityManagerFactory createTestEMF() {
        return Persistence.createEntityManagerFactory("CentroCivicoJPA");
    }

    public static void setAsGlobal(EntityManagerFactory emf) {
        EntityManagerSingleton.setEMF(emf);
    }

    public static void resetGlobal() {
        EntityManagerSingleton.reset();
    }
}

