package com.grupoms.app.integracion.Transaction;

public interface Transaction {

	void start() ;

	void commit() ;

	void rollback() ;

	public Object getResource();
	
}
