package com.grupoms.app.negocio.socioJPA;

import java.util.List;


public interface SocioSA {

	public Integer altaSocio(TSocio socio);

	public Integer bajaSocio(Integer id) throws Exception;

	public Integer modificarSocio(TSocio socio);

	public TSocio mostrarSocio(Integer id);

	public List<TSocio> listarSocios();

	public List<TSocio> mostrarSociosPorPromocion(Integer idPromocion);

	public Integer vincularPromocionASocio(Integer idSocio, Integer idPromocion);

	public Integer desvincularPromocionASocio(Integer idSocio, Integer idPromocion);
	
	public List<TSocio> aplicarPromocion(Integer idPromocion);

}
