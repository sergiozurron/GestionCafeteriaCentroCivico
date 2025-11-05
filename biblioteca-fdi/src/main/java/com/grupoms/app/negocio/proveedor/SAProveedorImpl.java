package com.grupoms.app.negocio.proveedor;

import java.util.ArrayList;
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

			// Validaciones
			if (tProveedor == null || tProveedor.getNombre() == null || tProveedor.getNombre().trim().isEmpty()) {
				tx.rollback();
				return -1;
			}

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

			// Validaciones
			if (tProveedor == null || tProveedor.getId() == null || tProveedor.getId() <= 0) {
				throw new IllegalArgumentException("El proveedor debe tener un ID válido para dar de baja.");
			}

			// Verificar existencia
			TProveedor proveedorExistente = daoProveedor.buscaPorId(tProveedor.getId());
			if (proveedorExistente == null) {
				tx.commit();
				return false;
			}

			if (!proveedorExistente.getActivo()) {
				tx.commit();
				return false; // Ya está inactivo
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

			// Validaciones
			if (tProveedor == null || tProveedor.getId() == null || tProveedor.getId() <= 0) {
				throw new IllegalArgumentException("El proveedor debe tener un ID válido para modificar.");
			}

			// Verificar existencia
			TProveedor proveedorExistente = daoProveedor.buscaPorId(tProveedor.getId());
			if (proveedorExistente == null) {
				tx.commit();
				return false;
			}

			// Verificar nombre único si se cambió
			if (!proveedorExistente.getNombre().equals(tProveedor.getNombre())) {
				TProveedor otroPorNombre = daoProveedor.buscaPorNombre(tProveedor.getNombre());
				if (otroPorNombre != null && !otroPorNombre.getId().equals(tProveedor.getId())) {
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
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("El ID del proveedor no es válido.");
		}

		Transaction tx = null;
		TProveedor proveedor = null;
		try {
			tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();

			proveedor = daoProveedor.buscaPorId(id);
			if (proveedor == null) {
				throw new IllegalArgumentException("El proveedor con ID " + id + " no existe.");
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try { tx.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
			}
			throw new RuntimeException(e.getMessage(), e);
		}
		return proveedor;
	}

	@Override
	public List<TProveedor> mostrarListaProveedores() {
		List<TProveedor> listaProveedoresActivos = new ArrayList<>();
		Transaction tx = null;
		try {
			tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();

			List<TProveedor> todos = daoProveedor.listar();
			for (TProveedor p : todos) {
				if (p.getActivo()) {
					listaProveedoresActivos.add(p);
				}
			}

			if (listaProveedoresActivos.isEmpty()) {
				throw new IllegalArgumentException("No hay proveedores activos en la base de datos");
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try { tx.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
			}
			throw new IllegalArgumentException("Error al mostrar la lista de proveedores.", e);
		}
		return listaProveedoresActivos;
	}
}
