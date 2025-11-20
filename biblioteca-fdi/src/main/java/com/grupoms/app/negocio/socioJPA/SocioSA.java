package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SocioSA {

    public Integer altaSocio(TSocio socio);

    public Integer bajaSocio(Integer id);

    public Integer modificarSocio(TSocio socio);

    public TSocio mostrarSocio(Integer id);

    public List<TSocio> listarSocios();

    public List<TEjemplar> verEjemplaresPrestadosPorSocio(Integer id);

    public Pair<TSocio,List<TEjemplar>>  mostrarSocioYEjemplares(Integer id);

    public Integer solicitarPrestamoEjemplar(Integer idSocio, Integer idEjemplar, LocalDate fecha);
}
