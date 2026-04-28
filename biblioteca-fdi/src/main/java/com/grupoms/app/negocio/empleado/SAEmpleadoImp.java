package com.grupoms.app.negocio.empleado;

import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.empleado.DAOEmpleado;
import com.grupoms.app.integracion.factoria.FactoriaDAO;

public class SAEmpleadoImp implements SAEmpleado {

	@Override
	public Integer crearEmpleado(TEmpleado empleado) {

		if (empleado == null)
			throw new IllegalArgumentException("El empleado no puede ser nulo.");

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOEmpleado dao = FactoriaDAO.getInstancia().creaDAOEmpleado();

			empleado.setActivo(true);

			Integer id = dao.crearEmpleado(empleado);

			if (id == null || id <= 0)
				throw new RuntimeException("No se pudo generar el empleado");

			t.commit();
			return id;

		} catch (Exception e) {

			try {
				if (t != null) t.rollback();
			} catch (Exception ex) { }

			throw new RuntimeException("Error creando empleado: " + e.getMessage(), e);
		}
	}

	@Override
	public Boolean bajaEmpleado(TEmpleado empleado) {

		if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
			throw new IllegalArgumentException("ID de empleado inválido.");

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOEmpleado dao = FactoriaDAO.getInstancia().creaDAOEmpleado();

			TEmpleado existente = dao.mostrarEmpleado(empleado.getID());

			if (existente == null)
				throw new IllegalStateException("El empleado no existe.");

			if (!existente.getActivo())
				throw new IllegalStateException("El empleado ya está dado de baja.");

			existente.setActivo(false);

			Boolean ok = dao.bajaEmpleado(existente);

			if (!ok)
				throw new RuntimeException("No se pudo dar de baja el empleado.");

			t.commit();
			return true;

		} catch (Exception e) {

			try {
				if (t != null) t.rollback();
			} catch (Exception ex) { }

			throw new RuntimeException("Error dando de baja empleado: " + e.getMessage(), e);
		}
	}

	@Override
	public Boolean modificarEmpleado(TEmpleado empleado) {

		if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
			throw new IllegalArgumentException("ID de empleado inválido.");

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOEmpleado dao = FactoriaDAO.getInstancia().creaDAOEmpleado();

			TEmpleado existente = dao.mostrarEmpleado(empleado.getID());

			if (existente == null)
				throw new IllegalStateException("El empleado no existe.");

			empleado.setActivo(existente.getActivo());

			Boolean ok = dao.modificarEmpleado(empleado);

			if (!ok)
				throw new RuntimeException("No se pudo modificar el empleado.");

			t.commit();
			return true;

		} catch (Exception e) {

			try {
				if (t != null) t.rollback();
			} catch (Exception ex) { }

			throw new RuntimeException("Error modificando empleado: " + e.getMessage(), e);
		}
	}

	@Override
	public TEmpleado mostrarEmpleado(Integer id) {

		if (id == null || id <= 0)
			throw new IllegalArgumentException("ID inválido.");

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOEmpleado dao = FactoriaDAO.getInstancia().creaDAOEmpleado();

			TEmpleado emp = dao.mostrarEmpleado(id);

			if (emp == null)
				throw new IllegalStateException("El empleado no existe.");

			t.commit();
			return emp;

		} catch (Exception e) {

			try {
				if (t != null) t.rollback();
			} catch (Exception ex) { }

			throw new RuntimeException("Error mostrando empleado: " + e.getMessage(), e);
		}
	}

	@Override
	public List<TEmpleado> mostrarListaEmpleados() {

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOEmpleado dao = FactoriaDAO.getInstancia().creaDAOEmpleado();

			List<TEmpleado> lista = dao.mostrarListaEmpleados();

			t.commit();
			return lista;

		} catch (Exception e) {

			try {
				if (t != null) t.rollback();
			} catch (Exception ex) { }

			throw new RuntimeException("Error listando empleados: " + e.getMessage(), e);
		}
	}
}