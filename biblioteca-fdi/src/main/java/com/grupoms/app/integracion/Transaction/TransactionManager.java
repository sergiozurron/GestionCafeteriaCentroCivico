package com.grupoms.app.integracion.Transaction;

public interface TransactionManager {

    Transaction newTransaccion() throws Exception;

    Transaction getTransaccion();

    void deleteTransaccion() throws Exception;
}
