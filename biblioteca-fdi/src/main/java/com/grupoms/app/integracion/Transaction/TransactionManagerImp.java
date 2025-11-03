package com.grupoms.app.integracion.Transaction;

import java.util.concurrent.ConcurrentHashMap;

public class TransactionManagerImp extends TransactionManager{
    private ConcurrentHashMap<Thread, Transaction> transactions;

    public TransactionManagerImp(){
        transactions = new ConcurrentHashMap<Thread,Transaction>();
    }

    @Override
    public Transaction newTransaction(){
        Thread currentThread = Thread.currentThread();
        Transaction existente = transactions.get(currentThread);

        if (existente == null) {
            Transaction nueva = FactoriaTransaction.getInstance().createTransaction();
            transactions.put(currentThread, nueva);
            return nueva;
        }
        throw new IllegalStateException("Ya existe una transacción activa para este hilo.");
    }

    @Override
    public Transaction getTransaction() {
        Thread currentThread = Thread.currentThread();
        Transaction t = transactions.get(currentThread);

        if (t != null) {
            return t;
        }
        throw new IllegalStateException("No hay ninguna transacción activa para este hilo.");
    }

    @Override
    public void deleteTransaction() {
        Thread currentThread = Thread.currentThread();
        Transaction t = transactions.get(currentThread);

        if (t != null) {
            transactions.remove(currentThread);
        } else {
            throw new IllegalStateException("No existe una transacción para eliminar en este hilo.");
        }
    }
}