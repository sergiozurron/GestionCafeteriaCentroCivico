package com.grupoms.app.negocio.producto;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.ingrediente.DAOIngrediente;
import com.grupoms.app.integracion.producto.DAOProducto;
import com.grupoms.app.integracion.producto.DAOReceta;
import com.grupoms.app.negocio.ingrediente.TIngrediente;

public class SARecetaImp implements SAReceta {

	@Override
	public Integer vincularIngredienteAProducto(Integer idProducto, Integer idIngrediente) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			DAOIngrediente daoIngrediente = FactoriaDAO.getInstancia().creaDAOIngrediente();
			DAOReceta daoReceta = FactoriaDAO.getInstancia().creaDAOReceta();

			TProducto producto = daoProducto.mostrarProducto(idProducto);
			if (producto == null || !producto.getActivo()) {
				throw new IllegalArgumentException("Producto no válido");
			}

			TIngrediente ingrediente = daoIngrediente.mostrarIngrediente(idIngrediente);
			if (ingrediente == null || !ingrediente.getActivo()) {
				throw new IllegalArgumentException("Ingrediente no válido");
			}

			TEntradaReceta existente = daoReceta.mostrarLineaReceta(idProducto, idIngrediente);
			if (existente != null && existente.getActivo()) {
				throw new IllegalStateException("La relación ya existe");
			}

			Integer id = daoReceta.vincular(idProducto, idIngrediente);

			if (id == null) {
				throw new RuntimeException("No se pudo vincular ingrediente");
			}

			t.commit();
			return id;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException("Error en vincularIngredienteAProducto", e);
		}
	}

	@Override
	public Integer desvincularIngredienteDeProducto(Integer idProducto, Integer idIngrediente) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			DAOIngrediente daoIngrediente = FactoriaDAO.getInstancia().creaDAOIngrediente();
			DAOReceta daoReceta = FactoriaDAO.getInstancia().creaDAOReceta();

			TProducto producto = daoProducto.mostrarProducto(idProducto);
			if (producto == null || !producto.getActivo()) {
				throw new IllegalArgumentException("Producto no válido");
			}

			TIngrediente ingrediente = daoIngrediente.mostrarIngrediente(idIngrediente);
			if (ingrediente == null || !ingrediente.getActivo()) {
				throw new IllegalArgumentException("Ingrediente no válido");
			}

			TEntradaReceta receta = daoReceta.mostrarLineaReceta(idProducto, idIngrediente);
			if (receta == null || !receta.getActivo()) {
				throw new IllegalStateException("Relación no existente");
			}

			Integer filas = daoReceta.desvincular(idProducto, idIngrediente);

			if (filas == null || filas <= 0) {
				throw new RuntimeException("No se pudo desvincular");
			}

			t.commit();
			return filas;

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<TIngrediente> listarIngredientesProducto(Integer idProducto) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOReceta daoReceta = FactoriaDAO.getInstancia().creaDAOReceta();

			List<TIngrediente> lista = daoReceta.listarIngredientesProducto(idProducto);

			t.commit();
			return (lista != null) ? lista : new ArrayList<>();

		} catch (Exception e) {
			try {
				t.rollback();
			} catch (Exception ex) {
			}
			throw new RuntimeException("Error listando ingredientes del producto", e);
		}
	}
}
