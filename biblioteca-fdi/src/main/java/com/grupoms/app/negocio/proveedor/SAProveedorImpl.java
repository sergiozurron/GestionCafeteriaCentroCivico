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

			TProveedor proveedorExistente = daoProveedor.buscaProveedorPorNombre(tProveedor.getNombre());
			if (proveedorExistente != null) {
				if (proveedorExistente.getActivo()) {
					tx.commit();
					return -1;
				}

				proveedorExistente.setActivo(true);
				proveedorExistente.setTarifa(tProveedor.getTarifa());
				proveedorExistente.setTiempoEntrega(tProveedor.getTiempoEntrega());
				daoProveedor.modificarProveedor(proveedorExistente);
				tx.commit();
				return proveedorExistente.getId();
			}

			tProveedor.setActivo(true);
			idGenerado = daoProveedor.creaProveedor(tProveedor);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try { TransactionManager.getInstance().deleteTransaction(); } catch (Exception ignored) {}
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

			TProveedor proveedorExistente = daoProveedor.mostrarProveedor(tProveedor.getId());
			if (proveedorExistente == null || !proveedorExistente.getActivo()) {
				tx.commit();
				return false;
			}

			proveedorExistente.setActivo(false);
			exito = daoProveedor.bajaProveedor(proveedorExistente);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try { TransactionManager.getInstance().deleteTransaction(); } catch (Exception ignored) {}
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

			TProveedor proveedorExistente = daoProveedor.mostrarProveedor(tProveedor.getId());
			if (proveedorExistente == null) {
				tx.commit();
				return false;
			}

			if (!proveedorExistente.getNombre().equals(tProveedor.getNombre())) {
				TProveedor otroPorNombre = daoProveedor.buscaProveedorPorNombre(tProveedor.getNombre());
				if (otroPorNombre != null) {
					tx.commit();
					return false;
				}
			}

			exito = daoProveedor.modificarProveedor(tProveedor);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try { TransactionManager.getInstance().deleteTransaction(); } catch (Exception ignored) {}
		}
		return exito;
	}

	@Override
	public TProveedor mostrarProveedor(Integer id) {
		Transaction tx = null;
		TProveedor proveedor = null;
		try {
			tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();

			proveedor = daoProveedor.mostrarProveedor(id);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try { TransactionManager.getInstance().deleteTransaction(); } catch (Exception ignored) {}
		}
		return proveedor;
	}

	@Override
	public List<TProveedor> mostrarListaProveedores() {
		Transaction tx = null;
		List<TProveedor> lista = new ArrayList<>();
		try {
			tx = TransactionManager.getInstance().newTransaction();
			DAOProveedor daoProveedor = FactoriaDAO.getInstancia().creaDAOProveedor();
			tx.start();

			lista = daoProveedor.listarProveedores();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try { TransactionManager.getInstance().deleteTransaction(); } catch (Exception ignored) {}
		}
		return lista;
	}
}
