package com.grupoms.app.negocio.prestamoJPA;

import java.util.List;

public interface PrestamoSA {

	public Integer altaPrestamo(TPrestamo prestamo);

	public Integer devolverPrestamo(Integer idPrestamo);

	public Integer modificarPrestamo(TPrestamo prestamo);

	public TPrestamo mostrarPrestamo(Integer idPrestamo);

	public List<TPrestamo> listarPrestamo();

	public Double calcularPrecioPromocion(TCalculoPrecioPromocion data);

}
