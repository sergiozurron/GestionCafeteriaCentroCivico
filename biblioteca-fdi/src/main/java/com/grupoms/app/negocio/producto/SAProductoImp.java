package com.grupoms.app.negocio.producto;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.producto.DAOProducto;

public class SAProductoImp implements SAProducto {

	@Override
	public Integer altaProducto(TProducto producto) {

		Transaction t = null;
		Integer idGenerado = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			t.start();

			if (producto == null) {
				t.commit();
				return -1;
			}

			producto.setActivo(true);

			idGenerado = daoProducto.altaProducto(producto);

			t.commit();

			return (idGenerado != null) ? idGenerado : -2;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return -99;
		}
	}

	@Override
	public Boolean bajaProducto(TProducto producto) {

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			t.start();

			if (producto == null || producto.getId() == null) {
				t.commit();
				return false;
			}

			TProducto existente = daoProducto.mostrarProducto(producto.getId());

			if (existente == null || !existente.getActivo()) {
				t.commit();
				return false;
			}

			existente.setActivo(false);

			Boolean exito = daoProducto.bajaProducto(existente);

			t.commit();

			return exito;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean modificarProducto(TProducto producto) {

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			t.start();

			if (producto == null || producto.getId() == null) {
				t.commit();
				return false;
			}

			TProducto existente = daoProducto.mostrarProducto(producto.getId());

			if (existente == null || !existente.getActivo()) {
				t.commit();
				return false;
			}

			Boolean exito = daoProducto.modificarProducto(producto);

			t.commit();

			return exito;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public TProducto mostrarProducto(Integer id) {

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			t.start();

			TProducto producto = daoProducto.mostrarProducto(id);

			t.commit();

			return producto;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TProducto> mostrarListaProductos() {

		Transaction t = null;
		List<TProducto> lista = new ArrayList<>();

		try {
			t = TransactionManager.getInstance().newTransaction();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			t.start();

			List<TProducto> todos = daoProducto.mostrarListaProductos();

			for (TProducto p : todos) {
				if (p.getActivo()) {
					lista.add(p);
				}
			}

			t.commit();

			return lista;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public List<TProducto> mostrarProductosPorProveedor(Integer idProveedor) {

		Transaction t = null;
		List<TProducto> listaProductos = new ArrayList<>();

		try {
			t = TransactionManager.getInstance().newTransaction();
			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			t.start();

			// Validación básica
			if (idProveedor == null || idProveedor <= 0) {
				t.commit();
				return listaProductos;
			}

			List<TProducto> todos = daoProducto.mostrarProductosPorProveedor(idProveedor);

			if (todos != null) {
				for (TProducto producto : todos) {
					if (producto.getActivo()) {
						listaProductos.add(producto);
					}
				}
			}

			t.commit();

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return new ArrayList<>();
		}

		return listaProductos;
	}
}
