package com.grupoms.app.negocio.ClaseJPA;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.negocio.assembler.ClaseAssembler;
import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import com.grupoms.app.negocio.salaJPA.BOSala;

public class ClaseSAImp implements ClaseSA {

	// ---- 1) Alta Clase ----

	@Override
	public Integer altaClase(TClase clase) {
		BOClase claseExistente = null;
		Integer id = -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			// ---- 1) Check if class already exists with same type and start date ----
			TypedQuery<BOClase> query = em.createNamedQuery(
				"com.grupoms.app.negocio.claseJPA.BOClase.findByTipoAndFecha",
				BOClase.class
			);
			query.setParameter("tipo", clase.getTipo());
			query.setParameter("fechaInicio", clase.getFechaInicio());

			try {
				claseExistente = query.getSingleResult();
			} catch (Exception e) {
				// No existing class found, this is fine
			}

			if (claseExistente != null) {
				if (!claseExistente.getActivo()) {
					claseExistente.setActivo(true);
					id = claseExistente.getId();
				} else {
					throw new IllegalStateException(
						"La clase de tipo " + clase.getTipo() +
						" con fecha de inicio " + clase.getFechaInicio() +
						" ya existe y está activa"
					);
				}
			} else {
				Integer idSala = clase.getIdSala(); // id de sala introducida

				if (idSala == null) {
					throw new IllegalArgumentException("Debe indicarse la sala para la clase.");
				}

				BOSala sala = em.find(BOSala.class, idSala);
				if (sala == null) {
					throw new IllegalArgumentException(
						"No existe ninguna sala con id " + idSala
					);
				}

				if (!sala.getActivo()) {
				     throw new IllegalStateException(
				        "La sala con id " + idSala + " no está activa."
				     );
				 }

				BOClase nuevaClase = new BOClase(clase);
				nuevaClase.setSala(sala); 

				em.persist(nuevaClase);
				em.flush();
				id = nuevaClase.getId();
			}

			t.commit();
		} catch (Exception e) {
			if (t.isActive()) {
				t.rollback();
			}
			
			throw e; // si quieres propagar la excepción hacia arriba
		} finally {
			em.close();
		}

		return id;
	}


	// ---- 2) Baja Clase ----

	@Override
	public Integer bajaClase(Integer id) {
		int res = -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOClase clase = em.find(BOClase.class, id);
			if (clase != null && clase.getActivo()) {
				clase.setActivo(false);
				res = 1;
			} else {
				res = 0;
			}

			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();

		} finally {
			em.close();
		}

		return res;
	}

	@Override
	public Integer modificarClase(TClase clase) {
		Integer id = -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOClase claseExistente = em.find(BOClase.class, clase.getId());
			if (claseExistente != null) {

				claseExistente.setTipo(clase.getTipo());
				claseExistente.setFechaInicio(clase.getFechaInicio());
				claseExistente.setDuracion(clase.getDuracion());

				em.merge(claseExistente);
				id = claseExistente.getId();
			} else {
				throw new IllegalStateException("La clase con ID " + clase.getId() + " no existe");
			}

			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			throw e;
		} finally {
			em.close();
		}

		return id;
	}

	// ---- 4) Mostrar Clase ----

	@Override
	public TClase mostrarClase(Integer id) {
		if (id == null || id < 0)
			return null;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		BOClase clase = em.find(BOClase.class, id);

		if (clase == null) {
			em.close();
			return null;
		}

		TClase dto = ClaseAssembler.entityToTransfer(clase);
		em.close();
		return dto;
	}

	// ---- 5) Listar Clases ----

	@Override
	public List<TClase> listarClase() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		t.begin();

		final TypedQuery<BOClase> query = em.createNamedQuery("com.grupoms.app.negocio.claseJPA.BOClase.findAll",
				BOClase.class);

		List<TClase> lista = query.getResultList().stream().map(ClaseAssembler::entityToTransfer)
				.collect(Collectors.toList());

		t.commit();
		em.close();

		return lista;
	}

	// ---- 6) Vincular Ejemplar a Clase ----

	@Override
	public Integer vincularEjemplarAClase(Integer idClase, Integer idEjemplar) {
		int res = -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOClase clase = em.find(BOClase.class, idClase);
			BOEjemplar ejemplar = em.find(BOEjemplar.class, idEjemplar);

			if (clase != null && clase.getActivo() && ejemplar != null && ejemplar.getActivo()) {
				clase.anyadirEjemplar(ejemplar);
				res = 1;
			}

			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
		} finally {
			em.close();
		}

		return res;
	}

	// ---- 7) Desvincular Ejemplar de Clase ----

	@Override
	public Integer desvincularEjemplarDeClase(Integer idClase, Integer idEjemplar) {
		int res = -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOClase clase = em.find(BOClase.class, idClase);
			BOEjemplar ejemplar = em.find(BOEjemplar.class, idEjemplar);

			if (clase != null && clase.getActivo() && ejemplar != null && ejemplar.getActivo()) {
				clase.eliminarEjemplar(ejemplar);
				res = 1;
			}

			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
		} finally {
			em.close();
		}

		return res;
	}
}
