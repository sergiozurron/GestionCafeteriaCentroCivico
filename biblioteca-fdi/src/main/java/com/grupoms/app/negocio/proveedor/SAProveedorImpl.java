package com.grupoms.app.negocio.proveedor;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.proveedor.DAOProveedor;
import com.grupoms.app.negocio.ingrediente.TIngrediente;

public class SAProveedorImpl implements SAProveedor {

	@Override
	public Integer altaProveedor(TProveedor tProveedor) {

		Transaction tx = TransactionManager.getInstance().newTransaction();

		try {
			tx.start();

			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();

			TProveedor existente = daoProveedor.buscaProveedorPorNombre(tProveedor.getNombre());

			// 1. proveedor activo ya existe
			if (existente != null && existente.getActivo()) {
				throw new IllegalArgumentException("El proveedor ya existe y está activo");
			}

			// 2. reactivar proveedor inactivo
			if (existente != null && !existente.getActivo()) {

				existente.setActivo(true);
				existente.setTarifa(tProveedor.getTarifa());
				existente.setTiempoEntrega(tProveedor.getTiempoEntrega());

				daoProveedor.modificarProveedor(existente);

				tx.commit();
				return existente.getId();
			}

			// 3. crear nuevo proveedor
			tProveedor.setActivo(true);

			Integer id = daoProveedor.creaProveedor(tProveedor);

			tx.commit();
			return id;

		} catch (Exception e) {

			try {
				tx.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean bajaProveedor(TProveedor tProveedor) {

		Transaction tx = TransactionManager.getInstance().newTransaction();

		try {
			tx.start();

			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			DAOIngrediente daoIngrediente = FactoriaDAO.getInstancia().creaDAOIngrediente();

			TProveedor existente = daoProveedor.mostrarProveedor(tProveedor.getId());
			

			if (existente == null || !existente.getActivo()) {
				throw new IllegalArgumentException("Proveedor no existe o ya está inactivo");
			}

			existente.setActivo(false);
			
			//Eliminacion en cascada de los ingredientes del proveedor
			List<TIngrediente> ingredientes =
					daoIngrediente.mostrarIngredientesProveedor(tProveedor.getId());

			if (ingredientes != null) {
				for (TIngrediente ing : ingredientes) {
					ing.setActivo(false);

					Boolean okIng = daoIngrediente.bajaIngrediente(ing);

					if (!okIng) {
						throw new RuntimeException("No se pudo dar de baja el ingrediente " + ing.getID());
					}
				}
			}

			Boolean ok = daoProveedor.bajaProveedor(existente);

			if (!ok) {
				throw new RuntimeException("No se pudo dar de baja el proveedor");
			}

			tx.commit();
			return true;

		} catch (Exception e) {

			try {
				tx.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean modificarProveedor(TProveedor tProveedor) {

		Transaction tx = TransactionManager.getInstance().newTransaction();

		try {
			tx.start();

			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();

			TProveedor existente = daoProveedor.mostrarProveedor(tProveedor.getId());

			if (existente == null) {
				throw new IllegalArgumentException("Proveedor no existe");
			}
			tProveedor.setActivo(existente.getActivo());

			if (!existente.getNombre().equals(tProveedor.getNombre())) {

				TProveedor otro = daoProveedor.buscaProveedorPorNombre(tProveedor.getNombre());

				if (otro != null && !otro.getId().equals(tProveedor.getId())) {
				    throw new IllegalArgumentException("Ya existe un proveedor con ese nombre");
				}
			}
			if (tProveedor.getNombre() == null || tProveedor.getNombre().isEmpty()) {
			    throw new IllegalArgumentException("El nombre no puede estar vacío");
			}

			if (tProveedor.getTarifa() < 0) {
			    throw new IllegalArgumentException("La tarifa no puede ser negativa");
			}

			if (tProveedor.getTiempoEntrega() < 0) {
			    throw new IllegalArgumentException("El tiempo de entrega no puede ser negativo");
			}

			Boolean ok = daoProveedor.modificarProveedor(tProveedor);

			if (!ok) {
				throw new RuntimeException("No se pudo modificar el proveedor");
			}

			tx.commit();
			return true;

		} catch (Exception e) {

			try {
				tx.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public TProveedor mostrarProveedor(Integer id) {

		Transaction tx = TransactionManager.getInstance().newTransaction();

		try {
			tx.start();

			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();

			TProveedor proveedor = daoProveedor.mostrarProveedor(id);

			tx.commit();
			return proveedor;

		} catch (Exception e) {

			try {
				tx.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TProveedor> mostrarListaProveedores() {

		Transaction tx = TransactionManager.getInstance().newTransaction();

		try {
			tx.start();

			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();

			List<TProveedor> lista = daoProveedor.listarProveedores();

			tx.commit();
			return lista;

		} catch (Exception e) {

			try {
				tx.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}

			throw new RuntimeException(e.getMessage());
		}
	}
}