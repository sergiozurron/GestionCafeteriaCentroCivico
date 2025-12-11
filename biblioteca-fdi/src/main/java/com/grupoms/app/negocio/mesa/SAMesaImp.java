package com.grupoms.app.negocio.mesa;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.mesa.DAOMesa;

public class SAMesaImp implements SAMesa {
	private DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

	@Override
	public Integer altaMesa(TMesa mesa) {
		Transaction t = null;
		Integer idGenerado = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();
			mesa.setActivo(true);
			idGenerado = daoMesa.altaMesa(mesa);
			if (idGenerado == -1) {
				throw new RuntimeException("No se pudo dar de alta la mesa");
			}
			mesa.setId(idGenerado);
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
	public Boolean bajaMesa(TMesa mesa) {
		Transaction t = null;
		Boolean exito = false;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			if (mesa == null || mesa.getId() == null || mesa.getId() <= 0)
				throw new IllegalArgumentException("La mesa debe tener un id válido para dar de baja.");

			mesa.setActivo(false);

			daoMesa.bajaMesa(mesa);

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
	public Boolean modificarMesa(TMesa mesa) {
		Transaction t = null;
		Boolean exito = false;

		if (mesa == null || mesa.getId() == null || mesa.getId() <= 0)
			throw new IllegalArgumentException("La mesa a modificar no es válida.");

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			TMesa existente = daoMesa.mostrarMesa(mesa.getId());
			if (existente == null || !existente.getActivo())
				throw new IllegalArgumentException("No existe una mesa activa con ID " + mesa.getId());

			exito = daoMesa.modificarMesa(mesa);

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
	public TMesa mostrarMesa(Integer ID) {
		if (ID == null || ID <= 0)
			throw new IllegalArgumentException("El ID del ingrediente no es válido.");
		Transaction t = null;
		TMesa ing = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();

			ing = daoMesa.mostrarMesa(ID);
			if (ing == null)
				throw new IllegalArgumentException("El ingrediente con ID " + ID + " no existe.");

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
		return ing;
	}

	@Override
	public List<TMesa> mostrarListaMesa() {
		List<TMesa> listaIngredientes = new ArrayList<>();
		Transaction t = null;
		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			List<TMesa> todos = daoMesa.mostrarListaMesa();
			for (TMesa ing : todos) {
				if (ing.getActivo()) {
					listaIngredientes.add(ing);
				}
			}
			if (listaIngredientes.isEmpty()) {
				throw new IllegalArgumentException("No hay ingredientes activos en la base de datos.");
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
			throw new IllegalArgumentException("Error al mostrar la lista de ingredientes", e);
		}
		return listaIngredientes;
	}

}
