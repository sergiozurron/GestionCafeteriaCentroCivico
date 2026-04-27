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
	
	// Significado de los códigos de error:
	// -1: producto inválido
	// -2: ingrediente inválido
	// -3: relación inexistente o duplicada
	// -4: error inesperado
	// -99: excepción

	@Override
	public Integer vincularIngredienteAProducto(Integer idProducto, Integer idIngrediente) {

		Transaction t = TransactionManager.getInstance().newTransaction();

		try {
			t.start();

			DAOProducto daoProducto = FactoriaDAO.getInstancia().creaDAOProducto();
			DAOIngrediente daoIngrediente = FactoriaDAO.getInstancia().creaDAOIngrediente();
			DAOReceta daoReceta = FactoriaDAO.getInstancia().creaDAOReceta();

			// 1. Producto
			TProducto producto = daoProducto.mostrarProducto(idProducto);
			if (producto == null || !producto.getActivo()) {
				t.commit();
				return -1;
			}

			// 2. Ingrediente
			TIngrediente ingrediente = daoIngrediente.mostrarIngrediente(idIngrediente);
			if (ingrediente == null || !ingrediente.getActivo()) {
				t.commit();
				return -2;
			}

			// 3. Ya existe
			TEntradaReceta receta = daoReceta.mostrarLineaReceta(idProducto, idIngrediente);
			if (receta != null && receta.getActivo()) {
				t.commit();
				return -3;
			}

			// 4. Insertar
			Integer id = daoReceta.vincular(idProducto, idIngrediente);

			t.commit();

			return (id != null) ? id : -4;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return -99;
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

			// 1. Comprobar producto
			TProducto producto = daoProducto.mostrarProducto(idProducto);
			if (producto == null || !producto.getActivo()) {
				t.commit();
				return -1;
			}

			// 2. Comprobar ingrediente
			TIngrediente ingrediente = daoIngrediente.mostrarIngrediente(idIngrediente);
			if (ingrediente == null || !ingrediente.getActivo()) {
				t.commit();
				return -2;
			}

			// 3. Comprobar relación
			TEntradaReceta receta = daoReceta.mostrarLineaReceta(idProducto, idIngrediente);
			if (receta == null || !receta.getActivo()) {
				t.commit();
				return -3;
			}

			// 4. Desvincular
			Integer filas = daoReceta.desvincular(idProducto, idIngrediente);

			t.commit();

			return (filas != null && filas > 0) ? 1 : -4;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return -99;
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

			return lista != null ? lista : new ArrayList<>();

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
