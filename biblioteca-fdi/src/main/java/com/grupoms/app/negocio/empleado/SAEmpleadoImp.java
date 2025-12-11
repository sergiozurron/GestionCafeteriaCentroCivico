package com.grupoms.app.negocio.empleado;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.empleado.DAOEmpleado;
import com.grupoms.app.integracion.empleado.DAOEmpleadoImp;

public class SAEmpleadoImp implements SAEmpleado {

	DAOEmpleado dao = new DAOEmpleadoImp();

	@Override
	public Integer crearEmpleado(TEmpleado empleado) {
		Transaction t = null;
		Integer idGenerado = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			if (empleado == null)
				throw new IllegalArgumentException("El empleado no puede ser nulo.");
			empleado.setActivo(true);

			idGenerado = dao.crearEmpleado(empleado);

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
	public Boolean bajaEmpleado(TEmpleado empleado) {
		Transaction t = null;
		Boolean exito = false;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
				throw new IllegalArgumentException("El empleado debe tener un ID válido para dar de baja.");

			empleado.setActivo(false);

			dao.bajaEmpleado(empleado);

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
	public Boolean modificarEmpleado(TEmpleado empleado) {
		Transaction t = null;
		Boolean exito = false;
		TEmpleado emp = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
				throw new IllegalArgumentException("El empleado debe tener un ID válido para modificar.");

			emp = dao.mostrarEmpleado(empleado.getID());
			if (emp == null)
				throw new IllegalArgumentException("El empleado con ID " + empleado.getID() + " no existe.");

			exito = dao.modificarEmpleado(empleado);

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

		return exito;
	}

	@Override
	public TEmpleado mostrarEmpleado(Integer ID) {
		if (ID == null || ID <= 0)
			throw new IllegalArgumentException("El ID del empleado no es válido.");

		Transaction t = null;
		TEmpleado emp = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			emp = dao.mostrarEmpleado(ID);

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

		return emp;
	}

	@Override
	public List<TEmpleado> mostrarListaEmpleados() {
		List<TEmpleado> listaEmpleadosActivos = new ArrayList<>();
		Transaction t = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			List<TEmpleado> todos = dao.mostrarListaEmpleados();
			for (TEmpleado e : todos) {
				if (e.getActivo()) {
					listaEmpleadosActivos.add(e);
				}
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
		}

		return listaEmpleadosActivos;
	}
}
