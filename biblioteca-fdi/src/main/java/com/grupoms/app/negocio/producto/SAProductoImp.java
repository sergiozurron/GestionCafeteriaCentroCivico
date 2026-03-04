package com.grupoms.app.negocio.producto;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.producto.DAOProducto;

public class SAProductoImp implements SAProducto {

	private DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();

	@Override
	public Integer altaProducto(TProducto producto) {
		Transaction t = null;
		Integer idGenerado = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			producto.setActivo(true);

			idGenerado = daoProducto.altaProducto(producto);
			if (idGenerado == -1) {
				throw new RuntimeException("No se pudo dar de alta el producto");
			}

			producto.setId(idGenerado);

			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return idGenerado;
	}

	@Override
	public Boolean bajaProducto(TProducto producto) {
		if (producto == null || producto.getId() == null || producto.getId() <= 0) {
			throw new IllegalArgumentException("El producto no es válido.");
		}

		Transaction t = null;
		Boolean exito = false;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			TProducto existente = daoProducto.mostrarProducto(producto.getId());
			if (existente == null || !existente.getActivo()) {
				throw new IllegalArgumentException("No existe un producto activo con ID " + producto.getId());
			}

			daoProducto.bajaProducto(producto.getId());

			t.commit();
			exito = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return exito;
	}

	@Override
	public Boolean modificarProducto(TProducto producto) {
		if (producto == null || producto.getId() == null || producto.getId() <= 0) {
			throw new IllegalArgumentException("El producto no es válido.");
		}

		Transaction t = null;
		Boolean exito = false;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			TProducto existente = daoProducto.mostrarProducto(producto.getId());
			if (existente == null || !existente.getActivo()) {
				throw new IllegalArgumentException("No existe un producto activo con ID " + producto.getId());
			}

			daoProducto.modificarProducto(producto);

			t.commit();
			exito = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return exito;
	}

	@Override
	public TProducto mostrarProducto(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("El ID del producto no es válido.");
		}

		Transaction t = null;
		TProducto producto = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			producto = daoProducto.mostrarProducto(id);
			if (producto == null) {
				throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
			}

			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			throw new RuntimeException(e.getMessage(), e);
		}

		return producto;
	}

	@Override
	public List<TProducto> mostrarListaProductos() {
		List<TProducto> listaProductos = new ArrayList<>();
		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			List<TProducto> todos = daoProducto.mostrarListaProductos();
			for (TProducto producto : todos) {
				if (producto.getActivo()) {
					listaProductos.add(producto);
				}
			}

			if (listaProductos.isEmpty()) {
				throw new IllegalArgumentException("No hay productos activos en la base de datos.");
			}

			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			throw new IllegalArgumentException("Error al mostrar la lista de productos.", e);
		}

		return listaProductos;
	}
	
	@Override
	public List<TProducto> mostrarProductosPorProveedor(Integer idProveedor) {

	    if (idProveedor == null || idProveedor <= 0) {
	        throw new IllegalArgumentException("El ID del proveedor no es válido.");
	    }

	    List<TProducto> listaProductos = new ArrayList<>();
	    Transaction t = null;

	    try {
	        t = TransactionManager.getInstance().newTransaction();
	        t.start();

	        List<TProducto> todos = daoProducto.mostrarProductosPorProveedor(idProveedor);

	        for (TProducto producto : todos) {
	            if (producto.getActivo()) {
	                listaProductos.add(producto);
	            }
	        }

	        if (listaProductos.isEmpty()) {
	            throw new IllegalArgumentException(
	                "No hay productos activos asociados al proveedor con ID " + idProveedor);
	        }

	        t.commit();

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (t != null) {
	            try {
	                t.rollback();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	        throw new IllegalArgumentException(
	            "Error al mostrar los productos por proveedor.", e);
	    }

	    return listaProductos;
	}
}
