package com.grupoms.app.negocio.EjemplarJPA;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.assembler.EjemplarAssembler;
import com.grupoms.app.negocio.materialJPA.BOMaterial;
import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

public class EjemplarSAImp implements EjemplarSA {

	@Override
	public Integer altaEjemplar(TEjemplar ejemplar) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();

		try {
			em.getTransaction().begin();
			BOMaterial boMaterial = em.find(BOMaterial.class, ejemplar.getIdMaterial(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			if (boMaterial == null || !boMaterial.getActivo()) {
				em.getTransaction().rollback();
				return -1;
			}

			BOEjemplar boEjemplar = new BOEjemplar();
			boEjemplar.setEstado("DISPONIBLE");
			boEjemplar.setActivo(true);
			boEjemplar.setMaterial(boMaterial);
			boMaterial.getEjemplares().add(boEjemplar);
			em.persist(boEjemplar);
			em.persist(boMaterial);
			em.getTransaction().commit();
			return boEjemplar.getId();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return -1;
		} finally {
			em.close();
		}
	}

	@Override
	public Boolean bajaEjemplar(Integer idEjemplar) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();

		try {
			em.getTransaction().begin();
			BOEjemplar boEjemplar = em.find(BOEjemplar.class, idEjemplar, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			if (boEjemplar == null || !boEjemplar.getActivo()) {
				em.getTransaction().rollback();
				return false;
			}
			
			List<BOClase> clasesAsociadas = em.createNamedQuery("com.grupoms.app.negocio.claseJPA.BOClase.findByEjemplar", BOClase.class)
					.setParameter("idEjemplar", idEjemplar).getResultList();
			
			if (!clasesAsociadas.isEmpty()) {
				em.getTransaction().rollback();
				return false;
			}
			
			List<BOPrestamo> prestamosAsociados = em.createNamedQuery("BOPrestamo.findByEjemplar", BOPrestamo.class)
					.setParameter("idEjemplar", idEjemplar).getResultList();
			
			if (!prestamosAsociados.isEmpty()) {
				em.getTransaction().rollback();
				return false;
			}

			boEjemplar.setActivo(false);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public Boolean modificarEjemplar(TEjemplar ejemplar) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();

		try {
			em.getTransaction().begin();
			BOEjemplar boEjemplar = em.find(BOEjemplar.class, ejemplar.getId(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			if (boEjemplar == null || !boEjemplar.getActivo()) {
				em.getTransaction().rollback();
				return false;
			}

			BOMaterial boMaterial = em.find(BOMaterial.class, ejemplar.getIdMaterial(),
					LockModeType.OPTIMISTIC_FORCE_INCREMENT);

			if (boMaterial == null || !boMaterial.getActivo()) {
				em.getTransaction().rollback();
				return false;
			}

			boEjemplar.setEstado(ejemplar.getEstado());
			boEjemplar.setMaterial(boMaterial);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public TEjemplar mostrarEjemplar(Integer idEjemplar) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();

		BOEjemplar boEjemplar = em.find(BOEjemplar.class, idEjemplar);

		if (boEjemplar == null || !boEjemplar.getActivo()) {
			return null;
		}

		em.close();

		return EjemplarAssembler.toTransferObject(boEjemplar);
	}

	@Override
	public List<TEjemplar> listarEjemplares() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();

		List<TEjemplar> ejemplares = em.createNamedQuery("com.grupoms.app.negocio.EjemplarJPA.BOEjemplar.findAllActivos", BOEjemplar.class).getResultList()
				.stream().map(EjemplarAssembler::toTransferObject).collect(Collectors.toList());

		em.close();

		return ejemplares;
	}

	@Override
	public List<TEjemplar> listarEjemplaresPorMaterial(Integer idMaterial) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();

		return em.createNamedQuery("com.grupoms.app.negocio.EjemplarJPA.BOEjemplar.findByMaterialId", BOEjemplar.class)
				.setParameter("materialId", idMaterial).getResultList().stream()
				.map(EjemplarAssembler::toTransferObject).toList();
	}
	
	@Override
	public List<TEjemplar> listarEjemplaresPrestadosPorAdultosPlenos(Date fechaInicio,Date fechaFin) {

	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();

	    List<TEjemplar> res = em.createNamedQuery(
	            "BOEjemplar.findPrestadosPorAdultosPlenosEnFechas", BOEjemplar.class)
	            .setParameter("fechaInicio", fechaInicio)
	            .setParameter("fechaFin", fechaFin)
	            .getResultList()
	            .stream()
	            .map(EjemplarAssembler::toTransferObject)
	            .toList();

	    em.close();
	    return res;
	}

}
