package com.grupoms.app.integracion.Transaction;

import java.sql.Connection;

public interface Transaction {

    void start() throws Exception;

    void commit() throws Exception;

    void rollback() throws Exception;

    Connection getConnection();
}
