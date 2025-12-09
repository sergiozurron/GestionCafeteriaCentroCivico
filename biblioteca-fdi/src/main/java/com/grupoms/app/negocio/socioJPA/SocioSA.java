package com.grupoms.app.negocio.socioJPA;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;

public interface SocioSA {

    public Integer altaSocio(TSocio socio);

    public Integer bajaSocio(Integer id) throws Exception;

    public Integer modificarSocio(TSocio socio);

    public TSocio mostrarSocio(Integer id);

    public List<TSocio> listarSocios();

    public Pair<TSocio,List<TEjemplar>>  mostrarSocioYEjemplares(Integer idSocio);

    public List<TSocio> mostrarSociosPorPromocion(Integer idPromocion);

    public Integer vincularPromocionASocio(Integer idSocio,Integer idPromocion);

    public Integer desvincularPromocionASocio(Integer idSocio,Integer idPromocion);

	Integer solicitarEjemplar(Integer idSocio, Integer idEjemplar, Date fechaMaxima);

	Integer devolverEjemplar(Integer idSocio, Integer idEjemplar, Date fechaDevolucion);

}
