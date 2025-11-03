package com.grupoms.app.integracion.Transaction;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class TransactionManagerImp implements TransactionManager {

    // Map para gestionar transacciones por hilo
    private Map<Long, Transaction> transacciones = new HashMap<>();

    private static TransactionManagerImp instance;

    // Singleton
    public static synchronized TransactionManagerImp getInstance() {
        if (instance == null) {
            instance = new TransactionManagerImp();
        }
        return instance;
    }

    @Override
    public Transaction newTransaccion() throws Exception {
        long idHilo = Thread.currentThread().getId();
        Transaction tx = FactoriaTransaction.getInstance().createTransaction();
        tx.start();
        transacciones.put(idHilo, tx);
        return tx;
    }

    @Override
    public Transaction getTransaccion() {
        long idHilo = Thread.currentThread().getId();
        return transacciones.get(idHilo);
    }

    @Override
    public void deleteTransaccion() throws Exception {
        long idHilo = Thread.currentThread().getId();
        Transaction tx = transacciones.get(idHilo);
        if (tx != null) {
            Connection conn = tx.getConnection();
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        transacciones.remove(idHilo);
    }
}
