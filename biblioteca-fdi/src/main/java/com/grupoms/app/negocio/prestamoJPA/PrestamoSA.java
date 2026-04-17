package com.grupoms.app.negocio.prestamoJPA;

import java.util.List;

public interface PrestamoSA {

	public Boolean altaPrestamo(TPrestamo prestamo);

	public Boolean devolverPrestamo(PrestamoId idPrestamo);

	public Boolean modificarPrestamo(TPrestamo prestamo);

	public TPrestamo mostrarPrestamo(PrestamoId idPrestamo);

	public List<TPrestamo> listarPrestamo();

	public Double calcularPrecioPromocion(TCalculoPrecioPromocion data);

}
