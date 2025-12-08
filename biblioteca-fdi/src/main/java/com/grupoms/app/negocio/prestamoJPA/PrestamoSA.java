package com.grupoms.app.negocio.prestamoJPA;

import java.util.List;

public interface PrestamoSA {

    public Integer altaPrestamo(TPrestamo prestamo);

    public Integer bajaPrestamo(Integer idPrestamo);

    public Integer modificarPrestamo(TPrestamo prestamo);

    public TPrestamo mostrarPrestamo(Integer idPrestamo);

    public List<TPrestamo> listarPrestamo();

}
