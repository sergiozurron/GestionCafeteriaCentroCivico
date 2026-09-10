package com.grupoms.app.negocio.mesa;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.mesa.DAOMesa;

public class SAMesaImp implements SAMesa {

	@Override
	public Integer altaMesa(TMesa mesa) {
		Transaction t = null;
		Integer idGenerado = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			mesa.setActivo(true);
			idGenerado = daoMesa.altaMesa(mesa);

			if (idGenerado == null) {
				throw new RuntimeException("Error en integración al intentar insertar la mesa.");
			}

			t.commit();

		} catch (Exception e) {
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					System.err.println("Error fatal durante el rollback: " + ex.getMessage());
				}
			}
			throw new RuntimeException("Error en SA al dar de alta mesa", e);
		}

		return idGenerado;
	}

	@Override
	public Boolean bajaMesa(TMesa mesa) {

		if (mesa == null || mesa.getId() == null || mesa.getId() <= 0)
			throw new IllegalArgumentException("La mesa debe tener un id válido.");

		Transaction t = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			TMesa existente = daoMesa.mostrarMesa(mesa.getId());

			if (existente == null)
				throw new IllegalArgumentException("La mesa no existe.");

			if (!existente.getActivo())
				throw new IllegalStateException("La mesa ya está dada de baja.");

			existente.setActivo(false);
			Boolean exito = daoMesa.bajaMesa(existente);

			if (!exito)
				throw new RuntimeException("No se pudo dar de baja la mesa.");

			t.commit();
			return true;

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw new RuntimeException("Error en SA al dar de baja mesa", e);
		}
	}

	@Override
	public Boolean modificarMesa(TMesa mesa) {
		if (mesa == null)
			throw new IllegalArgumentException("Mesa no válida");
		
		Transaction t = null;
		Boolean exito = false;

		if (mesa == null || mesa.getId() == null || mesa.getId() <= 0)
			throw new IllegalArgumentException("La mesa a modificar no es válida.");

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			TMesa existente = daoMesa.mostrarMesa(mesa.getId());
			if (existente == null || !existente.getActivo()) {
				throw new IllegalArgumentException("No existe una mesa activa con ID " + mesa.getId());
			}

			exito = daoMesa.modificarMesa(mesa);

			if (!exito) {
				throw new RuntimeException("Error en Integración: No se pudo modificar la mesa.");
			}

			t.commit();

		} catch (IllegalArgumentException e) {
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
				}
			}
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
				}
			}
			throw new RuntimeException("Error fatal modificando: " + e.getMessage());
		}

		return exito;
	}

	@Override
	public TMesa mostrarMesa(Integer ID) {
		if (ID == null || ID <= 0) {
			return null;
		}

		Transaction t = null;
		TMesa mesa = null;

		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();

			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
			mesa = daoMesa.mostrarMesa(ID);

			if (mesa == null || !mesa.getActivo()) {
				mesa = null;
			}

			t.commit();

		} catch (Exception e) {

			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					System.err.println("Error crítico durante el rollback: " + ex.getMessage());
				}
			}
			throw new RuntimeException("Error en SA al mostrar mesa", e);
		}

		return mesa;
	}

	@Override
	public List<TMesa> mostrarListaMesa() {
		List<TMesa> listaMesas = new ArrayList<>();
		Transaction t = null;
		try {
			t = TransactionManager.getInstance().newTransaction();
			t.start();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			listaMesas = daoMesa.mostrarListaMesaActivas();

			t.commit();
		} catch (Exception e) {
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			throw new RuntimeException("Error al mostrar la lista de mesas: " + e.getMessage(), e);
		}
		return listaMesas;
	}

}
