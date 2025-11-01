package com.grupoms.app.negocio.proveedor;

import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.proveedor.DAOProveedor;

public class SAProveedorImpl implements SAProveedor {

	@Override
	public int altaProveedor(TProveedor tProveedor) {
		DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
		TProveedor proveedorExistente = daoProveedor.buscaPorNombre(tProveedor.getNombre());
		if (proveedorExistente != null && proveedorExistente.getActivo()) {
			return -1;
		}
		tProveedor.setActivo(true);
		daoProveedor.crea(tProveedor);
		return tProveedor.getId();
	}

}
