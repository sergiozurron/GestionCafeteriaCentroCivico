package com.grupoms.app.integracion.Transaction;

public abstract class TransactionManager{
    private static TransactionManager instance;

    public static synchronized TransactionManager getInstance(){
        if(instance == null) instance = new TransactionManagerImp();
        return instance;
    }

    public abstract Transaction newTransaction();

    public abstract Transaction getTransaction();

    public abstract void deleteTransaction();

}