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

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

			if (producto == null) {
				throw new IllegalArgumentException("Producto nulo");
			}

			producto.setActivo(true);

			if (producto.getNombre() == null || producto.getNombre().isEmpty())
				throw new IllegalArgumentException("El nombre no puede estar vacío");

			if (producto.getStock() == null || producto.getStock() < 0)
				throw new IllegalArgumentException("El stock no puede ser negativo");

			if (producto.getPrecio() < 0)
				throw new IllegalArgumentException("El precio no puede ser negativo");

			if (producto.getCalorias() != null && producto.getCalorias() < 0)
				throw new IllegalArgumentException("Las calorías no pueden ser negativas");

			if (producto.getTamanho() != null && producto.getTamanho() < 0)
				throw new IllegalArgumentException("El tamaño no pueden ser negativo");

			if (producto.getTiempoPreparacion() != null && producto.getTiempoPreparacion() < 0)
				throw new IllegalArgumentException("El tiempo de preparacion no pueden ser negativas");

			Integer id = daoProducto.altaProducto(producto);

			if (id == null) {
				throw new RuntimeException("Error creando producto");
			}

			t.commit();
			return id;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean bajaProducto(TProducto producto) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

			if (producto == null || producto.getId() == null) {
				throw new IllegalArgumentException("Producto inválido");
			}

			TProducto existente = daoProducto.mostrarProducto(producto.getId());

			if (existente == null || !existente.getActivo()) {
				throw new IllegalStateException("Producto no existe o inactivo");
			}

			existente.setActivo(false);

			Boolean ok = daoProducto.bajaProducto(existente);

			if (!ok) {
				throw new RuntimeException("No se pudo dar de baja");
			}

			t.commit();
			return true;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Boolean modificarProducto(TProducto producto) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

			validarProducto(producto);
			TProducto existente = daoProducto.mostrarProducto(producto.getId());
			if (existente == null || !existente.getActivo()) {
				throw new IllegalStateException("Producto no activo");
			}

			Boolean ok = daoProducto.modificarProducto(producto);

			if (!ok) {
				throw new RuntimeException("No se pudo modificar");
			}

			t.commit();
			return true;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public TProducto mostrarProducto(Integer id) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			if (id == null || id <= 0) {
				throw new IllegalArgumentException("ID de producto inválido");
			}

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

			TProducto p = daoProducto.mostrarProducto(id);
			if (p == null) {
				throw new IllegalStateException("El producto no existe o está inactivo");
			}

			t.commit();
			return p;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TProducto> mostrarListaProductos() {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

			List<TProducto> todos = daoProducto.mostrarListaProductos();

			List<TProducto> activos = new ArrayList<>();

			for (TProducto p : todos) {
				if (p.getActivo())
					activos.add(p);
			}

			t.commit();
			return activos;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
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

	private void validarProducto(TProducto producto) {

		if (producto == null)
			throw new IllegalArgumentException("Producto nulo");

		if (producto.getNombre() == null || producto.getNombre().isEmpty())
			throw new IllegalArgumentException("El nombre no puede estar vacío");

		if (producto.getPrecio() < 0)
			throw new IllegalArgumentException("El precio no puede ser negativo");

		if (producto.getStock() == null || producto.getStock() < 0)
			throw new IllegalArgumentException("El stock no puede ser negativo");

		if (producto instanceof TComida) {

			TComida comida = (TComida) producto;

			if (comida.getCalorias() < 0)
				throw new IllegalArgumentException("Las calorías no pueden ser negativas");

			if (comida.getTiempoPreparacion() < 0)
				throw new IllegalArgumentException("El tiempo de preparación no puede ser negativo");
		} else {
			if (producto.getTamanho() < 0) {
				throw new IllegalArgumentException("El tamaño no puede ser negativo");
			}
		}
	}
}
