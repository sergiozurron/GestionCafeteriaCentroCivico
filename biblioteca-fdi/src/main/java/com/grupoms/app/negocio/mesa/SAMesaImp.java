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
			TMesa mesaExistente = daoMesa.leerMesaPorNumero(mesa.getNumero());

			if (mesaExistente != null) {
				if (mesaExistente.getActivo()) {

					throw new IllegalArgumentException("Ya existe una mesa activa con el número: " + mesa.getNumero());
				} else {

					mesa.setId(mesaExistente.getId());
					mesa.setActivo(true);

					Boolean modificada = daoMesa.modificarMesa(mesa);
					if (!modificada) {
						throw new RuntimeException("Error al reactivar la mesa existente.");
					}
					idGenerado = mesa.getId();
				}
			} else {

				mesa.setActivo(true);
				idGenerado = daoMesa.altaMesa(mesa);

				if (idGenerado == null) {

					throw new RuntimeException("Error en integración al intentar insertar la mesa.");
				}
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

			throw new RuntimeException(e.getMessage());
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

			if (mesa == null || mesa.getId() == null || mesa.getId() <= 0) {
				throw new IllegalArgumentException("La mesa debe tener un id válido para dar de baja.");
			}
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
			TMesa mesaEnBD = daoMesa.mostrarMesa(mesa.getId());

			if (mesaEnBD == null) {
				throw new IllegalArgumentException(
						"No se puede dar de baja: La mesa con ID " + mesa.getId() + " no existe.");
			}

			if (!mesaEnBD.getActivo()) {
				throw new IllegalStateException(
						"La mesa con ID " + mesa.getId() + " ya estaba dada de baja previamente.");
			}

			mesa.setActivo(false);
			exito = daoMesa.bajaMesa(mesa);

			if (!exito) {
				throw new RuntimeException("Error en Integración: No se pudo actualizar el estado de la mesa.");
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
			throw new RuntimeException(e.getMessage());
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
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();

			// 1. Comprobamos que la mesa que queremos modificar existe y está activa
			TMesa existente = daoMesa.mostrarMesa(mesa.getId());
			if (existente == null || !existente.getActivo()) {
				throw new IllegalArgumentException("No existe una mesa activa con ID " + mesa.getId());
			}

			// 2. ¡FUERA RESTRICCIONES! Como ya no exigimos número único, 
			// mandamos modificar directamente al DAO sin hacer más comprobaciones.
			exito = daoMesa.modificarMesa(mesa);

			if (!exito) {
				throw new RuntimeException("Error en Integración: No se pudo modificar la mesa.");
			}

			t.commit();
			
		} catch (IllegalArgumentException e) {
			// Atrapamos validaciones de negocio limpias
			if (t != null) {
				try { t.rollback(); } catch (Exception ex) { }
			}
			throw e; 
			
		} catch (Exception e) {
			// Fallos graves (BD caída, etc)
			e.printStackTrace();
			if (t != null) {
				try { t.rollback(); } catch (Exception ex) { }
			}
			throw new RuntimeException("Error fatal modificando: " + e.getMessage());
		}
		
		return exito;
	}

	@Override
	public TMesa mostrarMesa(Integer ID) {
		if (ID == null || ID <= 0)
			throw new IllegalArgumentException("El ID de la mesa no es válido.");
		Transaction t = null;
		TMesa mesa = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			t.start();
			DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
			mesa = daoMesa.mostrarMesa(ID);
			if (mesa == null)
				throw new IllegalArgumentException("La mesa con ID " + ID + " no existe.");

			t.commit();

		} catch (Exception e) {
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					System.err.println("Error crítico durante el rollback: " + ex.getMessage());
				}
			}
			throw new RuntimeException(e.getMessage(), e);
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
	        
	        // El DAO hace el trabajo duro, el SA solo orquesta
	        listaMesas = daoMesa.mostrarListaMesaActivas(); 

	        t.commit();
	    } catch (Exception e) {
	        if (t != null) {
	            try { t.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
	        }
	        throw new RuntimeException("Error al mostrar la lista de mesas: " + e.getMessage(), e);
	    }
	    return listaMesas;
	}

}
