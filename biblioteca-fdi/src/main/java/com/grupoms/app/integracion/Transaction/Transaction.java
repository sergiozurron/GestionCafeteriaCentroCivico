package com.grupoms.app.integracion.Transaction;

import java.sql.Connection;

public interface Transaction {

	void start() ;

	void commit() ;

	void rollback() ;

	Connection getConnection();
}
