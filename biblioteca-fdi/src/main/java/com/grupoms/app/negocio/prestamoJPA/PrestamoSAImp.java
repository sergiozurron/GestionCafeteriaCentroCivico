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
				t.rollback();
				return false;
			}

			BOEjemplar ejemplar = em.find(BOEjemplar.class, prestamo.getIdEjemplar(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (ejemplar == null || !ejemplar.getActivo() || !"DISPONIBLE".equalsIgnoreCase(ejemplar.getEstado())) {
				t.rollback();
				return false;
			}
			
			BOPrestamo prestamoExistente = em.find(BOPrestamo.class, new PrestamoId(prestamo.getIdSocio(), prestamo.getIdEjemplar(), new Date()));
			if (prestamoExistente != null) {
				t.rollback();
				return false;
			}

			BOPrestamo boPrestamo = new BOPrestamo();
			boPrestamo.setSocio(socio);
			boPrestamo.setEjemplar(ejemplar);
			boPrestamo.setFechaInicial(new Date());
			boPrestamo.setFechaMaxima(prestamo.getFechaMaxima());
			boPrestamo.setPrecioMulta(0.0);

			ejemplar.setEstado("PRESTADO");

			em.persist(boPrestamo);

			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
		return true;
	}

	@Override
	public Boolean devolverPrestamo(PrestamoId idPrestamo) {
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();

	    try {
	        t.begin();

	        BOPrestamo boprestamo = em.find(BOPrestamo.class, idPrestamo, LockModeType.OPTIMISTIC);

	        if (boprestamo == null || boprestamo.getFechaDevuelto() != null) {
	            t.rollback();
	            return false;
	        }
 
        	Date fechaActual = new Date();
            if (fechaActual.after(boprestamo.getFechaMaxima())) {
                long diasAtraso = (fechaActual.getTime() - boprestamo.getFechaMaxima().getTime()) / (1000 * 60 * 60 * 24);
                double multa = diasAtraso * 1.0; // Suponiendo una multa de 1.0 por día de atraso
                boprestamo.setPrecioMulta(multa);
            }

            boprestamo.getEjemplar().setEstado("DISPONIBLE");
        	boprestamo.setFechaDevuelto(new java.util.Date());

	        t.commit();
	        return true;

	    } catch (Exception e) {
	        if (t.isActive())
	            t.rollback();
	        e.printStackTrace();
	        return false;
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

			BOPrestamo bo = em.find(BOPrestamo.class, new PrestamoId(prestamo.getIdSocio(), prestamo.getIdEjemplar(), prestamo.getFechaInicial()));

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
			e.printStackTrace();
			return false;
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
			TypedQuery<BOPrestamo> query = em.createQuery("SELECT p FROM BOPrestamo p",
					BOPrestamo.class);

			return query.getResultList().stream().map(PrestamoAssembler::toDTO).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			em.close();
		}
	}

	@Override
	public Double calcularPrecioPromocion(TCalculoPrecioPromocion calculo) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		BOSocio socio = em.find(BOSocio.class, calculo.getIdSocio());
		try {
			if (socio == null || !socio.getActivo()) {
				return null;
			}
			
			Optional<BOPromocion> promocionOpt = socio.getPromociones().stream()
					.filter(p -> p.getID().equals(calculo.getIdPromocion()) && p.getActivo())
					.findFirst();
			
			if (promocionOpt.isEmpty()) {
				return null;
			}
			
			BOPromocion promocion = promocionOpt.get();
			
			Double precioFinal = (1 - promocion.getDescuento() / 100) * socio.getCuota();
			
			if (socio instanceof BOInfantil) {
				BOInfantil infantil = (BOInfantil) socio;
				precioFinal *= (1 - infantil.getReduccion() / 100);
			}
			
			return precioFinal;
		} catch (Exception e){
			e.printStackTrace();
			return socio.getCuota().doubleValue();
		} finally {
			em.close();
		}
	}
}