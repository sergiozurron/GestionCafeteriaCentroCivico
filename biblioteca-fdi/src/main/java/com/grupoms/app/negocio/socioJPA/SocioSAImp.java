package com.grupoms.app.negocio.socioJPA;

import java.util.List;
import java.util.stream.Collectors;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.assembler.AdultoAssembler;
import com.grupoms.app.negocio.assembler.InfantilAssembler;
import com.grupoms.app.negocio.assembler.SocioAssembler;
import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;

public class SocioSAImp implements SocioSA {

	@Override
	public Integer altaSocio(TSocio socio) {
		BOSocio socioExistente = null;
		Integer id = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			TypedQuery<BOSocio> query = em.createNamedQuery("com.grupoms.app.negocio.socioJPA.BOSocio.findByName",
					BOSocio.class);
			query.setParameter("nombre", socio.getNombreYapellido());
			query.setLockMode(LockModeType.OPTIMISTIC);

			try {
				socioExistente = query.getSingleResult();
			} catch (Exception e) {
			}

			if (socioExistente != null) {
				if (!socioExistente.getActivo()) {
					socioExistente.setActivo(true);
					id = socioExistente.getId();
				} else {
					throw new IllegalStateException("El socio ya existe");
				}
			} else {
				if (socio instanceof TAdulto) {
					BOAdulto adulto = new BOAdulto((TAdulto) socio);
					em.persist(adulto);
					em.flush();
					id = adulto.getId();
				} else if (socio instanceof TInfantil) {
					BOInfantil infantil = new BOInfantil((TInfantil) socio);
					em.persist(infantil);
					em.flush();
					id = infantil.getId();
				}
			}
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
	public Integer bajaSocio(Integer id) throws Exception {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			BOSocio socio = em.find(BOSocio.class, id, LockModeType.OPTIMISTIC);

			if (socio == null || !socio.getActivo()) {
				t.rollback();
				throw new Exception("El socio no existe o ya está inactivo.");
			}

			// Solo bloquear si tiene préstamos NO devueltos (pendientes)
			TypedQuery<BOPrestamo> query = em.createNamedQuery("BOPrestamo.findPendientesBySocio", BOPrestamo.class);
			query.setParameter("idSocio", socio.getId());
			List<BOPrestamo> prestamosPendientes = query.getResultList();

			if (!prestamosPendientes.isEmpty()) {
				t.rollback();
				throw new Exception("El socio tiene préstamos pendientes de devolver.");
			}
			
			// Dar de baja los préstamos devueltos que tuviese asociados
			em.createNamedQuery("BOPrestamo.deleteAllByIdSocio").setParameter("idSocio", socio.getId()).executeUpdate();

			// Limpiar promociones al dar de baja
			socio.getPromociones().clear();

			socio.setActivo(false);
			t.commit();
			return 1;
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public Integer modificarSocio(TSocio socio) {
		Integer id = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			BOSocio s = em.find(BOSocio.class, socio.getId(), LockModeType.OPTIMISTIC);

			if (s == null) {
				throw new IllegalArgumentException("El ID del Socio no existe.");
			} else {
				s.setNombreYapellido(socio.getNombreYapellido());
				s.setDni(socio.getDni());
				s.setTipoSocio(socio.getTipoSocio());
				s.setCuota(socio.getCuota());
				s.setActivo(true);

				if (socio.getTipoSocio() == 0 && s instanceof BOAdulto) {
					((BOAdulto) s).setMiembroPleno(((TAdulto) socio).getMiembroPleno());
				} else if (socio.getTipoSocio() == 1 && s instanceof BOInfantil) {
					((BOInfantil) s).setEdad(((TInfantil) socio).getEdad());
					((BOInfantil) s).setReduccion(((TInfantil) socio).getReduccion());
				}
			}
			t.commit();
			id = socio.getId();
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
	public TSocio mostrarSocio(Integer id) {
		if (id == null || id < 0)
			return null;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			BOSocio socio = em.find(BOSocio.class, id);
			if (socio == null || !socio.getActivo()) {
				return null;
			}
			if (socio instanceof BOAdulto) {
				return AdultoAssembler.toDTO((BOAdulto) socio);
			} else if (socio instanceof BOInfantil) {
				return InfantilAssembler.toDTO((BOInfantil) socio);
			} else {
				return SocioAssembler.entityToTransfer(socio);
			}
		} finally {
			em.close();
		}
	}

	@Override
	public List<TSocio> listarSocios() {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			TypedQuery<BOSocio> query = em.createNamedQuery("com.grupoms.app.negocio.socioJPA.BOSocio.findAll",
					BOSocio.class);
			return query.getResultList().stream().map(bo -> {
				if (bo instanceof BOAdulto)
					return AdultoAssembler.toDTO((BOAdulto) bo);
				else if (bo instanceof BOInfantil)
					return InfantilAssembler.toDTO((BOInfantil) bo);
				else
					return SocioAssembler.entityToTransfer(bo);
			}).collect(Collectors.toList());
		} finally {
			em.close();
		}
	}

	@Override
	public List<TSocio> mostrarSociosPorPromocion(Integer idPromocion) {
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		try {
			return em.createNamedQuery("com.grupoms.app.negocio.socioJPA.BOSocio.findByPromocion", BOSocio.class)
					.setParameter("idPromocion", idPromocion).getResultList().stream()
					.map(bo -> {
						if (bo instanceof BOAdulto)
							return AdultoAssembler.toDTO((BOAdulto) bo);
						else if (bo instanceof BOInfantil)
							return InfantilAssembler.toDTO((BOInfantil) bo);
						else
							return SocioAssembler.entityToTransfer(bo);
					}).collect(Collectors.toList());
		} finally {
			em.close();
		}
	}

	@Override
	public Integer vincularPromocionASocio(Integer idSocio, Integer idPromocion) {
		int res = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			BOSocio socio = em.find(BOSocio.class, idSocio, LockModeType.OPTIMISTIC);
			BOPromocion promocion = em.find(BOPromocion.class, idPromocion, LockModeType.OPTIMISTIC);

			if (socio != null && socio.getActivo() && promocion != null && promocion.getActivo()) {
				if (socio.getPromociones().contains(promocion)) {
					throw new Exception("El socio ya tiene esta promoción");
				}
				socio.anyadirPromocion(promocion);
				res = 1;
			}
			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return res;
	}

	@Override
	public Integer desvincularPromocionASocio(Integer idSocio, Integer idPromocion) {
		int res = -1;
		EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			BOSocio socio = em.find(BOSocio.class, idSocio, LockModeType.OPTIMISTIC);
			BOPromocion promocion = em.find(BOPromocion.class, idPromocion, LockModeType.OPTIMISTIC);

			if (socio != null && socio.getActivo() && promocion != null && promocion.getActivo()) {
				if (!socio.getPromociones().contains(promocion)) {
					throw new Exception("El socio no tiene esta promoción");
				}
				socio.eliminarPromocion(promocion);
				res = 1;
			}
			t.commit();
		} catch (Exception e) {
			if (t.isActive())
				t.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return res;
	}
	
	@Override
	public List<TSocio> aplicarPromocion(Integer idPromocion) {
	    EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
	    EntityTransaction t = em.getTransaction();

	    List<TSocio> resultado = new java.util.ArrayList<>();

	    try {
	        t.begin();

	        BOPromocion promocion = em.find(BOPromocion.class, idPromocion, LockModeType.OPTIMISTIC);

	        if (promocion == null || !promocion.getActivo()) {
	            t.rollback();
	            return resultado;
	        }

	        TypedQuery<BOSocio> query = em.createNamedQuery(
	            "com.grupoms.app.negocio.socioJPA.BOSocio.findByPromocion",
	            BOSocio.class
	        );
	        query.setParameter("idPromocion", idPromocion);

	        List<BOSocio> socios = query.getResultList();	        
	        
	        for (BOSocio socio : socios) {
	        	
	        	Integer nuevaCuota = calcularNuevaCuota(socio, promocion);
	        	if(nuevaCuota < 0) nuevaCuota = 0;

	            socio.setCuota(nuevaCuota);

	            if (socio instanceof BOAdulto)
	                resultado.add(AdultoAssembler.toDTO((BOAdulto) socio));
	            else if (socio instanceof BOInfantil)
	                resultado.add(InfantilAssembler.toDTO((BOInfantil) socio));
	            else
	                resultado.add(SocioAssembler.entityToTransfer(socio));
	        }

	        t.commit();

	    } catch (Exception e) {
	        if (t.isActive()) t.rollback();
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }

	    return resultado;
	}
	
	private Integer calcularNuevaCuota(BOSocio socio, BOPromocion promocion) {

	    double nuevaCuota;

	    if (socio.getTipoSocio() == 0) {
	        BOAdulto adulto = (BOAdulto) socio;

	        if (adulto.getMiembroPleno()) {
	            nuevaCuota = socio.getCuota() - (2 * promocion.getDescuento());
	        } else {
	            nuevaCuota = socio.getCuota() - promocion.getDescuento();
	        }

	    } else if (socio.getTipoSocio() == 1) {
	        BOInfantil infantil = (BOInfantil) socio;

	        nuevaCuota = socio.getCuota() -
	                (promocion.getDescuento() * (infantil.getReduccion() / 100.0));
	    } else {
	        nuevaCuota = socio.getCuota();
	    }

	    return (int) Math.round(nuevaCuota);
	}
}