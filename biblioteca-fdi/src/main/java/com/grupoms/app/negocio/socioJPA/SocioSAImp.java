package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.assembler.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SocioSAImp implements SocioSA{

    @Override
    public Integer altaSocio(TSocio socio) {
        BOSocio socioExistente=null;
        Integer id=-1;
        BOAdulto adulto=null;
        BOInfantil infantil=null;

        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            TypedQuery<BOSocio> query=em.createNamedQuery("com.grupoms.app.negocio.socioJPA.BOScio.findByName",BOSocio.class);
            query.setParameter("nombre y apellido",socio.getNombreYapellido());

            try{
                socioExistente=query.getSingleResult();
            } catch (Exception e) {
                //
            }
            if(socioExistente!=null){
                if(!socioExistente.getActivo()) { //si no esta activo, lo activo
                    socioExistente.setActivo(true);
                    id=socioExistente.getId();
                }else {
                    throw new IllegalStateException("El socio con nombre y apellido "+ socio.getNombreYapellido()+ " ya existe");
                }
            }
            else{
                if(socio instanceof TAdulto adultoDTO){
                    adulto=new BOAdulto(adultoDTO);
                    em.persist(adulto);
                    em.flush();
                    id=adulto.getId();
                } else if (socio instanceof TInfantil infantilDTO) {
                    infantil=new BOInfantil(infantilDTO);
                    em.persist(infantil);
                    em.flush();
                    id=infantil.getId();
                }
            }
            t.commit();
        } catch (Exception e) {
            if(t.isActive())
                t.rollback();
            throw e;
        }finally {
            em.close();
        }
        return id;
    }

    @Override
    public Integer bajaSocio(Integer id) throws Exception {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();

            BOSocio socio = em.find(BOSocio.class, id);

            if (socio == null || !socio.getActivo()) {
                t.rollback();
                throw new Exception("El socio no existe o ya está inactivo.");
            }

            if (!socio.getPrestamo().isEmpty()) {
                t.rollback();
                throw new Exception("Elimine primero los ejemplares asociados a este socio.");
            }

            socio.setActivo(false);
            t.commit();
            return 1;
        } catch (Exception e) {
            if(t.isActive())t.rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    @Override
    public Integer modificarSocio(TSocio socio) {
        Integer id = -1;
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        try {
            BOSocio s = em.find(BOSocio.class, socio.getId());

            if (s == null) {
                em.close();
                throw new IllegalArgumentException("El ID del Socio no existe.");
            }
            else {
                // Actualizar campos comunes
                s.setNombreYapellido(socio.getNombreYapellido());
                s.setDni(socio.getDni());
                s.setTipoSocio(socio.getTipoSocio());
                s.setCuota(socio.getCuota());
                s.setActivo(true);
                // Actualizar campos específicos según tipo
                if (socio.getTipoSocio() == 0) {        // Adulto
                    TAdulto adulto = (TAdulto) socio;
                    BOAdulto boAdulto = (BOAdulto) s;
                    boAdulto.setMiembroPleno(adulto.getMiembroPleno());

                } else if (socio.getTipoSocio() == 1) { // Infantil
                    TInfantil infantil = (TInfantil) socio;
                    BOInfantil boInfantil = (BOInfantil) s;
                    boInfantil.setEdad(infantil.getEdad());
                    boInfantil.setReduccion(infantil.getReduccion());
                }
            }
            t.commit();
            id = socio.getId();

        } finally {
            em.close();
        }

        return id;
    }

    @Override
    public TSocio mostrarSocio(Integer id) {
        if(id==null ||id<0)return null;
        //Empiezo la transacccion
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        BOSocio socio = em.find(BOSocio.class, id);
        if(socio==null) {
            em.close();
            return null;
        }
        TSocio dto;
        if (socio instanceof BOAdulto adulto) {
            dto = AdultoAssembler.toDTO(adulto);  // devuelve TAdulto
        } else if (socio instanceof BOInfantil infantil) {
            dto = InfantilAssembler.toDTO(infantil); // devuelve TInfantil
        } else {
            dto = SocioAssembler.entityToTransfer(socio);
        }
        return dto;
    }

    @Override
    public List<TSocio> listarSocios() {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();

        final TypedQuery<BOSocio> query = em.createNamedQuery("com.grupoms.app.negocio.socioJPA.BOSocio.findAll", BOSocio.class);
        List<TSocio> lista = query
                .getResultList()
                .stream()
                .map(bo -> {
                    if (bo instanceof BOAdulto adulto)
                        return AdultoAssembler.toDTO(adulto);
                    else if (bo instanceof BOInfantil infantil)
                        return InfantilAssembler.toDTO(infantil);
                    else
                        return SocioAssembler.entityToTransfer(bo); // fallback
                })
                .collect(Collectors.toList());

        t.commit();

        //cierro el em
        em.close();
        return lista;
    }

    @Override
    public Pair<TSocio, List<TEjemplar>> mostrarSocioYEjemplares(Integer idSocio) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        TSocio socio=mostrarSocio(idSocio);
        TypedQuery<BOEjemplar> query = em.createNamedQuery("com.grupoms.app.negocio.EjemplarJPA.BOEjemplar.findBySocio", BOEjemplar.class);
        query.setParameter("idSocio", idSocio);

        List<TEjemplar> lista = query.getResultList()
                .stream()
                .map(EjemplarAssembler::toTransferObject)
                .collect(Collectors.toList());

        t.commit();
        em.close();
        return Pair.of(socio,lista);
    }

    @Override
    public List<TSocio> mostrarSociosPorPromocion(Integer idPromocion) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        EntityTransaction t = em.getTransaction();
        List<TSocio> lista = new ArrayList<>();
        try {
            t.begin();
            BOPromocion promocion = em.find(BOPromocion.class, idPromocion);
            if(promocion == null || !promocion.getActivo()) {
                t.rollback();
                return Collections.emptyList();
            }
            for(BOSocio s: promocion.getSocios()) {
                lista.add(SocioAssembler.entityToTransfer(s));
            }
            t.commit();
        } catch(Exception ex) {
            if(t.isActive()) t.rollback();
            ex.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
        return lista;
    }

    @Override
    public Integer solicitarEjemplar(Integer idSocio, Integer idEjemplar, LocalDate fechaFinal) {
        return 0;
    }

    @Override
    public Integer devolverEjemplar(Integer idSocio, Integer idEjemplar, LocalDate fechaDevolucion) {
        return 0;
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
                if(socio.getPromocions().contains(promocion))throw new Exception("El socio ya tiene esta promoción.");
                socio.anyadirPromocion(promocion);
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
                if (!socio.getPromocions().contains(promocion))
                    throw new Exception("El socio no tiene esta promoción.");
                socio.eliminarPromocion(promocion);
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
