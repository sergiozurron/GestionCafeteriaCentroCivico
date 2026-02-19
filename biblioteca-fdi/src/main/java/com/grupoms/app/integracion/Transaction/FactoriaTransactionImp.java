package com.grupoms.app.integracion.Transaction;

public class FactoriaTransactionImp extends FactoriaTransaction {

	@Override
	public Transaction createTransaction() {
		return new TransactionMySQL();
	}

}
