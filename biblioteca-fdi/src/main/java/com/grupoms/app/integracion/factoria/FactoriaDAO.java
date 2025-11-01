package com.grupoms.app.integracion.factoria;

import com.grupoms.app.integracion.proveedor.DAOProveedor;
import com.grupoms.app.integracion.proveedor.DAOProveedorImpl;

public class FactoriaDAO {

	private static FactoriaDAO instancia;
	
	public static FactoriaDAO getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaDAO();
		}
		return instancia;
	}
	
	public DAOProveedor creaDAOProveedor() {
		return new DAOProveedorImpl();
	}
	
}
