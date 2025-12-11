package com.grupoms.app.integracion.queries;

public abstract class FactoriaQuery {

	private static FactoriaQuery instance;

	private static synchronized FactoriaQuery getInstance() {
		if (instance == null)
			instance = new FactoriaQueryImp();

		return instance;
	}

	public Query getNewQuery(Integer id) {

		return null;
	}

	public abstract Query getNewQuery(String nombre);

}
