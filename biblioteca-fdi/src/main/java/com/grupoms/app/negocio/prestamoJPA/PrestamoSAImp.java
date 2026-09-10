package com.grupoms.app.negocio.prestamoJPA;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.assembler.PrestamoAssembler;
import com.grupoms.app.negocio.socioJPA.BOInfantil;
import com.grupoms.app.negocio.socioJPA.BOSocio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

public class PrestamoSAImp implements PrestamoSA {

	@Override
	public Boolean altaPrestamo(TPrestamo prestamo) {

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOSocio socio = em.find(BOSocio.class, prestamo.getIdSocio(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (socio == null || !socio.getActivo()) {
				throw new IllegalArgumentException("Socio inválido");
			}

			BOEjemplar ejemplar = em.find(BOEjemplar.class, prestamo.getIdEjemplar(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (ejemplar == null || !ejemplar.getActivo() || !"DISPONIBLE".equalsIgnoreCase(ejemplar.getEstado())) {
				throw new IllegalArgumentException("Ejemplar no disponible");
			}

			BOPrestamo existente = em.find(BOPrestamo.class,
					new PrestamoId(prestamo.getIdSocio(), prestamo.getIdEjemplar(), new Date()));

			if (existente != null) {
				throw new IllegalStateException("Préstamo ya existe");
			}

			BOPrestamo bo = new BOPrestamo();
			bo.setSocio(socio);
			bo.setEjemplar(ejemplar);
			bo.setFechaInicial(new Date());
			bo.setFechaMaxima(prestamo.getFechaMaxima());
			bo.setPrecioMulta(0.0);

			ejemplar.setEstado("PRESTADO");

			em.persist(bo);

			t.commit();
			return true;

		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			throw new RuntimeException("Error en altaPrestamo", e);
		} finally {
			em.close();
		}
	}

	@Override
	public Boolean devolverPrestamo(PrestamoId idPrestamo) {

		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOPrestamo p = em.find(BOPrestamo.class, idPrestamo, LockModeType.OPTIMISTIC);

			if (p == null || p.getFechaDevuelto() != null) {
				throw new IllegalArgumentException("Préstamo no válido");
			}

			Date now = new Date();

			if (now.after(p.getFechaMaxima())) {
				long dias = (now.getTime() - p.getFechaMaxima().getTime()) / (1000 * 60 * 60 * 24);
				p.setPrecioMulta(dias * 1.0);
			}

			p.getEjemplar().setEstado("DISPONIBLE");
			p.setFechaDevuelto(now);

			t.commit();
			return true;

		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			throw new RuntimeException("Error devolviendo préstamo", e);
		} finally {
			em.close();
		}
	}

	@Override
	public Boolean modificarPrestamo(TPrestamo prestamo) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();

			BOPrestamo bo = em.find(BOPrestamo.class,
					new PrestamoId(prestamo.getIdSocio(), prestamo.getIdEjemplar(), prestamo.getFechaInicial()));

			if (bo == null) {
				t.rollback();
				return false;
			}

			bo.setFechaMaxima(prestamo.getFechaMaxima());
			bo.setPrecioMulta(prestamo.getPrecioMulta());

			t.commit();
			return true;

		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			throw new RuntimeException(e.getMessage());
		} finally {
			em.close();
		}
	}

	@Override
	public TPrestamo mostrarPrestamo(PrestamoId idPrestamo) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			BOPrestamo bo = em.find(BOPrestamo.class, idPrestamo);
			return PrestamoAssembler.toDTO(bo);
		} finally {
			em.close();
		}
	}

	@Override
	public List<TPrestamo> listarPrestamo() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			TypedQuery<BOPrestamo> query = em.createQuery("SELECT p FROM BOPrestamo p", BOPrestamo.class);

			return query.getResultList().stream().map(PrestamoAssembler::toDTO).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			em.close();
		}
	}
}