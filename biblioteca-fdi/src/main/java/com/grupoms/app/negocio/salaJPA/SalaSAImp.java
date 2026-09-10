package com.grupoms.app.negocio.salaJPA;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.assembler.SalaAssembler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

public class SalaSAImp implements SalaSA {

	@Override
	public Integer altaSala(TSala sala) {
		BOSala salaExistente = null;
		Integer id = -1;

		if (sala == null || sala.getNombre() == null || sala.getNombre().trim().isEmpty() || sala.getCapacidad() == null
				|| sala.getCapacidad() <= 0)
			return -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			TypedQuery<BOSala> query = em.createNamedQuery("com.grupoms.app.negocio.salaJPA.BOSala.findByName",
					BOSala.class);
			query.setParameter("nombre", sala.getNombre());

			try {
				salaExistente = query.getSingleResult();
			} catch (Exception e) {
			}

			if (salaExistente != null) {
				if (!salaExistente.getActivo()) {
					em.lock(salaExistente, LockModeType.OPTIMISTIC);
					salaExistente.setActivo(true);
					salaExistente.setNombre(sala.getNombre());
					salaExistente.setCapacidad(sala.getCapacidad());
					id = salaExistente.getId();
				} else {
					t.rollback();
					return -1;
				}
			} else {
				BOSala nuevaSala = new BOSala();
				nuevaSala.setNombre(sala.getNombre());
				nuevaSala.setCapacidad(sala.getCapacidad());
				nuevaSala.setActivo(true);

				em.persist(nuevaSala);
				em.flush();
				id = nuevaSala.getId();
			}

			if (t.isActive())
				t.commit();

		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
		}
		return id;
	}

	@Override
	public Integer bajaSala(Integer id) {
		if (id == null || id <= 0)
			return -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOSala sala = em.find(BOSala.class, id);
			if (sala != null)
				em.lock(sala, LockModeType.OPTIMISTIC);

			if (sala == null || !sala.getActivo()) {
				t.rollback();
				return -1;
			}

			boolean tieneClasesActivas = false;
			List<BOClase> clases = sala.getClases();
			if (clases != null) {
				for (BOClase clase : clases) {
					if (clase.getActivo()) {
						tieneClasesActivas = true;
						break;
					}
				}
			}

			if (tieneClasesActivas) {
				t.rollback();
				return -2;
			}

			sala.setActivo(false);
			t.commit();
			return 1;

		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
		}
	}

	@Override
	public Integer modificarSala(TSala sala) {
		if (sala == null || sala.getId() == null || sala.getId() <= 0 || sala.getNombre() == null
				|| sala.getNombre().trim().isEmpty() || sala.getCapacidad() == null || sala.getCapacidad() <= 0)
			return -1;

		Integer id = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		try {
			BOSala s = em.find(BOSala.class, sala.getId());
			if (s != null)
				em.lock(s, LockModeType.OPTIMISTIC);

			if (s == null || !s.getActivo()) {
				t.rollback();
				return -1;
			} else {
				TypedQuery<BOSala> query = em.createNamedQuery("com.grupoms.app.negocio.salaJPA.BOSala.findByName",
						BOSala.class);
				query.setParameter("nombre", sala.getNombre());
				BOSala salaMismoNombre = null;
				try {
					salaMismoNombre = query.getSingleResult();
				} catch (Exception e) {
				}

				if (salaMismoNombre != null && !salaMismoNombre.getId().equals(s.getId()) && salaMismoNombre.getActivo()) {
					t.rollback();
					return -1;
				}

				s.setNombre(sala.getNombre());
				s.setCapacidad(sala.getCapacidad());
				t.commit();
				id = s.getId();
			}
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			em.close();
		}

		return id;
	}

	@Override
	public TSala mostrarSala(Integer id) {
		if (id == null || id <= 0)
			return null;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			BOSala sala = em.find(BOSala.class, id);

			if (sala == null || !sala.getActivo())
				return null;

			return SalaAssembler.entityToTransfer(sala);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public List<TSala> listarSala() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();

		List<TSala> lista = Collections.emptyList();

		try {
			TypedQuery<BOSala> query = em.createNamedQuery("com.grupoms.app.negocio.salaJPA.BOSala.findAll",
					BOSala.class);

			lista = query.getResultList().stream().map(SalaAssembler::entityToTransfer).collect(Collectors.toList());

			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

		return lista;
	}

}