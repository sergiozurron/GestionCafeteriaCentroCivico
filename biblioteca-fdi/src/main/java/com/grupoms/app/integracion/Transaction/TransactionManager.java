package com.grupoms.app.integracion.Transaction;

public interface TransactionManager {

    Transaction newTransaccion() throws Exception;

    Transaction getTransaccion();

    void deleteTransaccion() throws Exception;

    static TransactionManager getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }
}
