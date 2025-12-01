package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.List;

public class SocioSAImp implements SocioSA{

    @Override
    public Integer altaSocio(TSocio socio) {
        return 0;
    }

    @Override
    public Integer bajaSocio(Integer id) {
        return 0;
    }

    @Override
    public Integer modificarSocio(TSocio socio) {
        return 0;
    }

    @Override
    public TSocio mostrarSocio(Integer id) {
        return null;
    }

    @Override
    public List<TSocio> listarSocios() {
        return List.of();
    }

    @Override
    public List<TEjemplar> verEjemplaresPrestadosPorSocio(Integer id) {
        return List.of();
    }

    @Override
    public Pair<TSocio, List<TEjemplar>> mostrarSocioYEjemplares(Integer id) {
        return null;
    }

    @Override
    public Integer solicitarPrestamoEjemplar(Integer idSocio, Integer idEjemplar, LocalDate fecha) {
        return 0;
    }
}
