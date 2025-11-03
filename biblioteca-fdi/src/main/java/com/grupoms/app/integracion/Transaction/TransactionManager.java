package com.grupoms.app.integracion.Transaction;

public interface TransactionManager {

    Transaction newTransaction() throws Exception;

    Transaction getTransaction();

    void deleteTransaction() throws Exception;

    static TransactionManager getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }
}
