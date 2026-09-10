package com.grupoms.app.negocio.ingrediente;

import java.util.List;

import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.negocio.producto.TEntradaReceta;
import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

public class SAIngredienteImp implements SAIngrediente {

	@Override
	public Integer crearIngrediente(TIngrediente ingrediente) {

		if (ingrediente == null)
			throw new IllegalArgumentException("Ingrediente no válido");

		if (ingrediente.getNombre() == null || ingrediente.getNombre().isEmpty())
			throw new IllegalArgumentException("El nombre no puede estar vacío");

		if (ingrediente.getIDProveedor() == null || ingrediente.getIDProveedor() <= 0)
			throw new IllegalArgumentException("Proveedor no válido");

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOIngrediente dao = FactoriaDAO.getInstancia().creaDAOIngrediente();

			com.grupoms.app.integracion.proveedor.DAOProveedor daoProveedor = FactoriaDAO.getInstancia()
					.creaDAOProveedor();
			com.grupoms.app.negocio.proveedor.TProveedor proveedor = daoProveedor
					.mostrarProveedor(ingrediente.getIDProveedor());

			if (proveedor == null)
				throw new IllegalStateException("El proveedor no existe");

			if (!proveedor.getActivo())
				throw new IllegalStateException("El proveedor no está activo");

			ingrediente.setActivo(true);

			Integer id = dao.crearIngrediente(ingrediente);

			if (id == null || id <= 0)
				throw new RuntimeException("No se pudo crear el ingrediente");

			t.commit();
			return id;

		} catch (Exception e) {

			try {
				t.rollback();
			} catch (Exception ex) {
			}

			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Boolean modificarIngrediente(TIngrediente ingrediente) {

		if (ingrediente == null || ingrediente.getID() == null || ingrediente.getID() <= 0)
			throw new IllegalArgumentException("Ingrediente no válido");

		if (ingrediente.getIDProveedor() == null || ingrediente.getIDProveedor() <= 0)
			throw new IllegalArgumentException("Proveedor no válido");

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOIngrediente daoIngrediente = FactoriaDAO.getInstancia().creaDAOIngrediente();
			com.grupoms.app.integracion.proveedor.DAOProveedor daoProveedor = FactoriaDAO.getInstancia()
					.creaDAOProveedor();
			TIngrediente existente = daoIngrediente.mostrarIngrediente(ingrediente.getID());

			if (existente == null)
				throw new IllegalArgumentException("El ingrediente no existe");

			if (!existente.getActivo())
				throw new IllegalArgumentException("El ingrediente está inactivo");
			com.grupoms.app.negocio.proveedor.TProveedor proveedor = daoProveedor
					.mostrarProveedor(ingrediente.getIDProveedor());
			if (proveedor == null)
				throw new IllegalArgumentException("El proveedor no existe");
			if (!proveedor.getActivo())
				throw new IllegalArgumentException("El proveedor no está activo");
			ingrediente.setActivo(existente.getActivo());
			Boolean exito = daoIngrediente.modificarIngrediente(ingrediente);
			if (!exito)
				throw new RuntimeException("No se pudo modificar el ingrediente");

			t.commit();
			return true;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
				throw new RuntimeException("Error en rollback", ex);
			}
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Boolean bajaIngrediente(TIngrediente ingrediente) {

		if (ingrediente == null || ingrediente.getID() == null || ingrediente.getID() <= 0)
			throw new IllegalArgumentException("Ingrediente no válido");

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOIngrediente dao = FactoriaDAO.getInstancia().creaDAOIngrediente();

			TIngrediente existente = dao.mostrarIngrediente(ingrediente.getID());

			if (existente == null)
				throw new IllegalArgumentException("El ingrediente no existe");

			if (!existente.getActivo())
				throw new IllegalArgumentException("El ingrediente ya está dado de baja");

			existente.setActivo(false);

			Boolean exito = dao.bajaIngrediente(existente);

			t.commit();
			return exito;

		} catch (Exception e) {
			t.rollback();
			throw new RuntimeException("Error en SA al dar de baja ingrediente", e);
		}
	}

	@Override
	public TIngrediente mostrarIngrediente(Integer id) {

		if (id == null || id <= 0)
			throw new IllegalArgumentException("ID no válido");

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOIngrediente dao = FactoriaDAO.getInstancia().creaDAOIngrediente();

			TIngrediente ing = dao.mostrarIngrediente(id);

			if (ing == null)
				throw new IllegalArgumentException("Ingrediente no encontrado");

			t.commit();
			return ing;

		} catch (Exception e) {
			t.rollback();
			throw new RuntimeException("Error en SA al mostrar ingrediente", e);
		}
	}

	@Override
	public List<TIngrediente> mostrarListaIngredientes() {

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOIngrediente dao = FactoriaDAO.getInstancia().creaDAOIngrediente();

			List<TIngrediente> lista = dao.mostrarListaIngredientes();

			t.commit();
			return lista;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw new RuntimeException("Error en SA al mostrar lista de ingredientes", e);
		}
	}

	@Override
	public List<TEntradaReceta> mostrarIngredientesPorProducto(Integer IDProducto) {

		if (IDProducto == null || IDProducto <= 0)
			throw new IllegalArgumentException("ID de producto no válido");

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOIngrediente dao = FactoriaDAO.getInstancia().creaDAOIngrediente();

			List<TEntradaReceta> lista = dao.listarIngredientesPorProducto(IDProducto);

			t.commit();
			return lista;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw new RuntimeException("Error en SA al listar ingredientes por producto", e);
		}
	}

	@Override
	public List<TIngrediente> mostrarIngredientesProveedor(Integer idProveedor) {

		if (idProveedor == null || idProveedor <= 0)
			throw new IllegalArgumentException("Proveedor no válido");

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOIngrediente dao = FactoriaDAO.getInstancia().creaDAOIngrediente();

			List<TIngrediente> lista = dao.mostrarIngredientesProveedor(idProveedor);

			t.commit();
			return lista;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw new RuntimeException("Error al mostrar ingredientes por proveedor", e);
		}
	}
}