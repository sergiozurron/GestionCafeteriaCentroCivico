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
import jakarta.persistence.TypedQuery;

public class PrestamoSAImp implements PrestamoSA {

	@Override
	public Integer altaPrestamo(TPrestamo prestamo) {
		Integer id = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();

			BOSocio socio = em.find(BOSocio.class, prestamo.getIdSocio());
			if (socio == null || !socio.getActivo()) {
				t.rollback();
				return -1;
			}

			BOEjemplar ejemplar = em.find(BOEjemplar.class, prestamo.getIdEjemplar());
			if (ejemplar == null || !ejemplar.getActivo()) {
				t.rollback();
				return -1;
			}

			if (!"DISPONIBLE".equalsIgnoreCase(ejemplar.getEstado())) {
				t.rollback();
				return -1;
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
			id = boPrestamo.getId();

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
	public Integer devolverPrestamo(Integer idPrestamo) {
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();

	    try {
	        t.begin();

	        BOPrestamo boprestamo = em.find(BOPrestamo.class, idPrestamo);

	        if (boprestamo == null || boprestamo.getFechaDevuelto() != null) {
	            t.rollback();
	            return -1;
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
	        return boprestamo.getId();

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
	public Integer modificarPrestamo(TPrestamo prestamo) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();

			BOPrestamo bo = em.find(BOPrestamo.class, prestamo.getId());

			if (bo == null) {
				t.rollback();
				return -1;
			}

			bo.setFechaMaxima(prestamo.getFechaMaxima());
			bo.setPrecioMulta(prestamo.getPrecioMulta());

			t.commit();
			return bo.getId();

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
	public TPrestamo mostrarPrestamo(Integer idPrestamo) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			BOPrestamo bo = em.find(BOPrestamo.class, idPrestamo);
			if (bo == null) {
				return null;
			}
			return PrestamoAssembler.toDTO(bo);
		} finally {
			em.close();
		}
	}

	@Override
	public List<TPrestamo> listarPrestamo() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			TypedQuery<BOPrestamo> query = em.createQuery("SELECT p FROM BOPrestamo p WHERE p.activo = true",
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