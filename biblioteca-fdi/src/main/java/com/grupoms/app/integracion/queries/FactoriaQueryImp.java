package com.grupoms.app.integracion.queries;

public class FactoriaQueryImp extends FactoriaQuery{

	@Override
	public Query getNewQuery(String nombre) {
		switch(nombre) {
		case "ejemplaresSociosPlenoPorFecha":
			return new ejemplaresSociosPlenoPorFecha();
		case "mostrarProductosConIngredientesProveedor":
		return null;
		}
	}

}
