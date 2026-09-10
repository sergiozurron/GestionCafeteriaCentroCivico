package com.grupoms.app.integracion.Transaction;

public abstract class FactoriaTransaction {
	private static FactoriaTransaction instance;

	public static synchronized FactoriaTransaction getInstance() {
		if (instance == null)
			instance = new FactoriaTransactionImp();

		return instance;

	}

	public abstract Transaction createTransaction();
}
