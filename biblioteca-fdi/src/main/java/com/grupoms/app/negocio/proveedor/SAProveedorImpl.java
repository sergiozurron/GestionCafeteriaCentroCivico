package com.grupoms.app.negocio.proveedor;

import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.proveedor.DAOProveedor;

public class SAProveedorImpl implements SAProveedor {

	@Override
	public Integer altaProveedor(TProveedor tProveedor) {
		Transaction tx = null;
		Integer idGenerado = null;
		try {
			tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();

			TProveedor proveedorExistente = daoProveedor.buscaPorNombre(tProveedor.getNombre());
			if (proveedorExistente != null) {
				if (proveedorExistente.getActivo()) {
					tx.commit();
					return -1; // Ya existe y está activo
				}
				// Reactivar proveedor existente
				proveedorExistente.setActivo(true);
				proveedorExistente.setTarifa(tProveedor.getTarifa());
				proveedorExistente.setTiempoEntrega(tProveedor.getTiempoEntrega());
				daoProveedor.actualiza(proveedorExistente);
				tx.commit();
				return proveedorExistente.getId();
			}

			tProveedor.setActivo(true);
			idGenerado = daoProveedor.crea(tProveedor);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try { tx.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
			}
		}
		return idGenerado;
	}

	@Override
	public Boolean bajaProveedor(TProveedor tProveedor) {
		Transaction tx = null;
		Boolean exito = false;
		try {
			tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();

			// Verificar existencia
			TProveedor proveedorExistente = daoProveedor.buscaPorId(tProveedor.getId());
			if (proveedorExistente == null || !proveedorExistente.getActivo()) {
				tx.commit();
				return false;
			}

			// Baja lógica
			proveedorExistente.setActivo(false);
			exito = daoProveedor.baja(proveedorExistente);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try { tx.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
			}
		}
		return exito;
	}

	@Override
	public Boolean modificarProveedor(TProveedor tProveedor) {
		Transaction tx = null;
		Boolean exito = false;
		try {
			tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();

			// Verificar existencia
			TProveedor proveedorExistente = daoProveedor.buscaPorId(tProveedor.getId());
			if (proveedorExistente == null) {
				tx.commit();
				return false;
			}

			// Verificar nombre único si se cambió
			if (!proveedorExistente.getNombre().equals(tProveedor.getNombre())) {
				TProveedor otroPorNombre = daoProveedor.buscaPorNombre(tProveedor.getNombre());
				if (otroPorNombre != null) {
					tx.commit();
					return false; // Nombre duplicado
				}
			}

			// Modificar
			exito = daoProveedor.actualiza(tProveedor);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try { tx.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
			}
		}
		return exito;
	}

	@Override
	public TProveedor mostrarProveedor(Integer id) {
		return FactoriaDAO.getInstancia().creaDAOProveedor().buscaPorId(id);
	}

	@Override
	public List<TProveedor> mostrarListaProveedores() {
		return FactoriaDAO.getInstancia().creaDAOProveedor().listar();
	}
}
