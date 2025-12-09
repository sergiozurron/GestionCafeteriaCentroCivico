package com.grupoms.app.negocio.socioJPA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.assembler.AdultoAssembler;
import com.grupoms.app.negocio.assembler.EjemplarAssembler;
import com.grupoms.app.negocio.assembler.InfantilAssembler;
import com.grupoms.app.negocio.assembler.SocioAssembler;
import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
            TypedQuery<BOSocio> query = em.createNamedQuery("com.grupoms.app.negocio.socioJPA.BOSocio.findByName", BOSocio.class);
            query.setParameter("nombre", socio.getNombreYapellido());

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
            if (t.isActive()) t.rollback();
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
            BOSocio socio = em.find(BOSocio.class, id);

            if (socio == null || !socio.getActivo()) {
                t.rollback();
                throw new Exception("El socio no existe o ya está inactivo.");
            }

            boolean tienePrestamosActivos = false;
            if (socio.getPrestamos() != null) {
                for (BOPrestamo p : socio.getPrestamos()) {
                    if (p.getActivo() && p.getFechaDevuelto() == null) {
                        tienePrestamosActivos = true;
                        break;
                    }
                }
            }

            if (tienePrestamosActivos) {
                t.rollback();
                throw new Exception("El socio tiene préstamos pendientes.");
            }

            socio.setActivo(false);
            t.commit();
            return 1;
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
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
            BOSocio s = em.find(BOSocio.class, socio.getId());

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
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
        return id;
    }

    @Override
    public TSocio mostrarSocio(Integer id) {
        if (id == null || id < 0) return null;
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
            TypedQuery<BOSocio> query = em.createNamedQuery("com.grupoms.app.negocio.socioJPA.BOSocio.findAll", BOSocio.class);
            return query.getResultList().stream().map(bo -> {
                if (bo instanceof BOAdulto) return AdultoAssembler.toDTO((BOAdulto) bo);
                else if (bo instanceof BOInfantil) return InfantilAssembler.toDTO((BOInfantil) bo);
                else return SocioAssembler.entityToTransfer(bo);
            }).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public Pair<TSocio, List<TEjemplar>> mostrarSocioYEjemplares(Integer idSocio) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        try {
            TSocio socio = mostrarSocio(idSocio);
            if (socio == null) return null;

            TypedQuery<BOEjemplar> query = em.createNamedQuery("BOEjemplar.findBySocio", BOEjemplar.class);
            query.setParameter("idSocio", idSocio);

            List<TEjemplar> lista = query.getResultList().stream()
                    .map(EjemplarAssembler::toTransferObject)
                    .collect(Collectors.toList());

            return Pair.of(socio, lista);
        } finally {
            em.close();
        }
    }

    @Override
    public List<TSocio> mostrarSociosPorPromocion(Integer idPromocion) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        List<TSocio> lista = new ArrayList<>();
        try {
            BOPromocion promocion = em.find(BOPromocion.class, idPromocion);
            if (promocion != null && promocion.getActivo()) {
                for (BOSocio s : promocion.getSocios()) {
                    if (s.getActivo()) {
                        lista.add(SocioAssembler.entityToTransfer(s));
                    }
                }
            }
        } finally {
            em.close();
        }
        return lista;
    }

    @Override
    public Integer solicitarEjemplar(Integer idSocio, Integer idEjemplar, Date fechaMaxima) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        Integer idPrestamo = -1;

        try {
            t.begin();
            BOSocio socio = em.find(BOSocio.class, idSocio);
            if (socio == null || !socio.getActivo()) throw new Exception("Socio no válido");

            BOEjemplar ejemplar = em.find(BOEjemplar.class, idEjemplar);
            if (ejemplar == null || !ejemplar.getActivo()) throw new Exception("Ejemplar no válido");

            if (!"DISPONIBLE".equalsIgnoreCase(ejemplar.getEstado())) {
                throw new Exception("El ejemplar no está disponible");
            }

            BOPrestamo prestamo = new BOPrestamo(socio, ejemplar, fechaMaxima);
            ejemplar.setEstado("PRESTADO");
            em.persist(prestamo);

            socio.getPrestamos().add(prestamo);
            ejemplar.getPrestamos().add(prestamo);

            t.commit();
            idPrestamo = prestamo.getId();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
        return idPrestamo;
    }

    @Override
    public Integer devolverEjemplar(Integer idSocio, Integer idEjemplar, Date fechaDevolucion) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        Integer res = -1;

        try {
            t.begin();
            TypedQuery<BOPrestamo> query = em.createNamedQuery("BOPrestamo.findActivoBySocioYEjemplar", BOPrestamo.class);
            query.setParameter("idSocio", idSocio);
            query.setParameter("idEjemplar", idEjemplar);

            BOPrestamo prestamo;
            try {
                prestamo = query.getSingleResult();
            } catch (Exception e) {
                throw new Exception("No hay préstamo activo");
            }

            prestamo.setFechaDevuelto(fechaDevolucion);

            if (fechaDevolucion.after(prestamo.getFechaMaxima())) {
                long diffInMillies = Math.abs(fechaDevolucion.getTime() - prestamo.getFechaMaxima().getTime());
                long diasRetraso = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                prestamo.setPrecioMulta(diasRetraso * 2.0);
            } else {
                prestamo.setPrecioMulta(0.0);
            }

            BOEjemplar ejemplar = prestamo.getEjemplar();
            ejemplar.setEstado("DISPONIBLE");

            t.commit();
            res = prestamo.getId();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            return -1;
        } finally {
            em.close();
        }
        return res;
    }

    @Override
    public Integer vincularPromocionASocio(Integer idSocio, Integer idPromocion) {
        int res = -1;
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            BOSocio socio = em.find(BOSocio.class, idSocio);
            BOPromocion promocion = em.find(BOPromocion.class, idPromocion);

            if (socio != null && socio.getActivo() && promocion != null && promocion.getActivo()) {
                if (socio.getPromocions().contains(promocion)) {
                    throw new Exception("El socio ya tiene esta promoción");
                }
                socio.anyadirPromocion(promocion);
                res = 1;
            }
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
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
            BOSocio socio = em.find(BOSocio.class, idSocio);
            BOPromocion promocion = em.find(BOPromocion.class, idPromocion);

            if (socio != null && socio.getActivo() && promocion != null && promocion.getActivo()) {
                if (!socio.getPromocions().contains(promocion)) {
                    throw new Exception("El socio no tiene esta promoción");
                }
                socio.eliminarPromocion(promocion);
                res = 1;
            }
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return res;
    }
}