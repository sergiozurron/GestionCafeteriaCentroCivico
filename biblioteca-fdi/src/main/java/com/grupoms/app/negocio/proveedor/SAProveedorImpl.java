package com.grupoms.app.negocio.proveedor;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.proveedor.DAOProveedor;

public class SAProveedorImpl implements SAProveedor {

	@Override
	public int altaProveedor(TProveedor tProveedor) {
		try {
			Transaction tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();
			TProveedor proveedorExistente = daoProveedor.buscaPorNombre(tProveedor.getNombre());
			if (proveedorExistente != null) {
				if (proveedorExistente.getActivo()) {
					tx.commit();
					return -1;
				}
				proveedorExistente.setActivo(true);
				daoProveedor.actualiza(proveedorExistente);
				tx.commit();
				return proveedorExistente.getId();
			}
			tProveedor.setActivo(true);
			daoProveedor.crea(tProveedor);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tProveedor.getId();
	}

}
