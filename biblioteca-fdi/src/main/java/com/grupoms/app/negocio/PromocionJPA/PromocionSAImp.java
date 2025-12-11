package com.grupoms.app.negocio.PromocionJPA;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.assembler.PromocionAssembler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PromocionSAImp implements PromocionSA {
	public Integer altaPromocion(TPromocion promocion) {
		BOPromocion promocionExistente = null;
		Integer id = -1;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			TypedQuery<BOPromocion> query = em.createNamedQuery(
					"com.grupoms.app.negocio.PromocionJPA.BOPromocion.findByInstance", BOPromocion.class);
			query.setParameter("tipo", promocion.getTipo());
			query.setParameter("descuento", promocion.getDescuento());
			try {
				promocionExistente = query.getSingleResult();
			} catch (Exception e) {

			}
			if (promocionExistente != null) {
				if (!promocionExistente.getActivo()) {
					promocionExistente.setActivo(true);
					id = promocionExistente.getID();
				} else {
					throw new IllegalStateException("La promoción con tipo " + promocion.getTipo() + " y descuento "
							+ promocion.getDescuento() + " ya existe");
				}
			} else {
				BOPromocion nuevaPromocion = new BOPromocion(promocion);
				nuevaPromocion.setActivo(true);
				em.persist(nuevaPromocion);
				em.flush();
				id = nuevaPromocion.getID();
			}
			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
		} finally {
			em.close();
		}
		return id;
	}

	public Integer bajaPromocion(Integer id) {
		int res = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			BOPromocion promocion = em.find(BOPromocion.class, id);
			if (promocion != null && promocion.getActivo()) {
				promocion.setActivo(false);
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

	public Integer modificarPromocion(TPromocion promocion) {
		Integer id = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		try {
			BOPromocion promocionExistente = em.find(BOPromocion.class, promocion.getId());
			if (promocionExistente != null) {
				promocionExistente.setTipo(promocion.getTipo());
				promocionExistente.setDescuento(promocion.getDescuento());
				em.merge(promocionExistente);
				id = promocionExistente.getID();
			} else {
				throw new IllegalStateException("La promoción con ID " + promocion.getId() + " no existe");
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

	public TPromocion mostrarPromocion(Integer id) {
		if (id == null || id < 0)
			return null;

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		BOPromocion promocion = em.find(BOPromocion.class, id);
		if (promocion == null) {
			em.close();
			return null;
		}
		TPromocion dto = PromocionAssembler.toDTO(promocion);
		return dto;
	}

	public List<TPromocion> listarPromociones() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		final TypedQuery<BOPromocion> query = em
				.createNamedQuery("com.grupoms.app.negocio.PromocionJPA.BOPromocion.findAll", BOPromocion.class);
		List<TPromocion> lista = query.getResultList().stream().map(PromocionAssembler::toDTO)
				.collect(Collectors.toList());
		t.commit();
		em.close();
		return lista;
	}

	public List<TPromocion> VerPromocionesPorSocio(Integer idSocio) {
    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
    List<TPromocion> lista = null;

    try {
        TypedQuery<BOPromocion> query = em.createNamedQuery(
                "com.grupoms.app.negocio.PromocionJPA.BOPromocion.findBySocio",
                BOPromocion.class
        );
        query.setParameter("idSocio", idSocio);

        lista = query.getResultList()
                    .stream()
                    .map(PromocionAssembler::toDTO)
                    .collect(Collectors.toList());

    } catch (Exception e) {
        e.printStackTrace();
        lista = Collections.emptyList();
    } finally {
        em.close();
    }

    return lista;
}

}
