package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SocioSA {

    public Integer altaSocio(TSocio socio);

    public Integer bajaSocio(Integer id) throws Exception;

    public Integer modificarSocio(TSocio socio);

    public TSocio mostrarSocio(Integer id);

    public List<TSocio> listarSocios();

    public Pair<TSocio,List<TEjemplar>>  mostrarSocioYEjemplares(Integer id);

    public List<TSocio> mostrarSociosPorPromocin(Integer idPromocion);

    public Integer solicitarEjemplar(Integer idSocio, Integer idEjemplar, LocalDate fechaFinal);

    public Integer devolverEjemplar(Integer idSocio,Integer idEjemplar,LocalDate fechaDevolucion);

    public Integer vincularPromocionASocio(Integer idSocio,Integer idPromocion);

    public Integer desvincularPromocionASocio(Integer idSocio,Integer idPromocion);

}
