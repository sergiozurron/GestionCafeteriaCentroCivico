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
		    if (t != null) {
		        try {
		            t.rollback();
		        } catch (Exception ex) {
		            throw new RuntimeException("Error durante rollback en crearEmpleado", ex);
		        }
		    }
		    throw e;
		}

		return idGenerado;
	}

	@Override
	public Boolean bajaEmpleado(TEmpleado empleado) {
		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
				throw new IllegalArgumentException("El empleado debe tener un ID válido para dar de baja.");

			TEmpleado existente = dao.mostrarEmpleado(empleado.getID());
			if (existente == null)
				throw new IllegalArgumentException("El empleado no existe.");
			if (!existente.getActivo())
				throw new IllegalArgumentException("El empleado ya está dado de baja.");

			empleado.setActivo(false);

			Boolean exito = dao.bajaEmpleado(empleado);

			if (!exito)
				throw new RuntimeException("No se pudo dar de baja el empleado");

			t.commit();
			return true;

		} catch (Exception e) {
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					throw new RuntimeException("Error en rollback", ex);
				}
			}
			throw e;
		}
	}

	@Override
	public Boolean modificarEmpleado(TEmpleado empleado) {
		Transaction t = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			if (empleado == null || empleado.getID() == null || empleado.getID() <= 0)
				throw new IllegalArgumentException("El empleado debe tener un ID válido para modificar.");
			empleado.setActivo(true);

			TEmpleado emp = dao.mostrarEmpleado(empleado.getID());
			if (emp == null)
				throw new IllegalArgumentException("El empleado con ID " + empleado.getID() + " no existe.");

			Boolean exito = dao.modificarEmpleado(empleado);
			
			if (!exito)
				throw new RuntimeException("No se pudo dar de baja el empleado");

			t.commit();
			return true;

		} catch (Exception e) {
		    if (t != null) {
		        try {
		            t.rollback();
		        } catch (Exception ex) {
		            throw new RuntimeException("Error durante rollback en modificarEmpleado", ex);
		        }
		    }
		    throw e;
		}
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
		    if (t != null) {
		        try {
		            t.rollback();
		        } catch (Exception ex) {
		            throw new RuntimeException("Error durante rollback en mostrarEmpleado", ex);
		        }
		    }
		    throw e;
		}

		return emp;
	}

	@Override
	public List<TEmpleado> mostrarListaEmpleados() {
		List<TEmpleado> todos = new ArrayList<>();
		Transaction t = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			todos = dao.mostrarListaEmpleados();

			t.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					throw new RuntimeException("Error en modificarEmpleado", e);
				}
			}
		}

		return todos;
	}
}
