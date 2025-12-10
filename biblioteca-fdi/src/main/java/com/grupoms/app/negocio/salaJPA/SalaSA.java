package com.grupoms.app.negocio.salaJPA;

import java.util.List;

import com.grupoms.app.negocio.ClaseJPA.TClase;

public interface SalaSA {
	public Integer altaSala(TSala sala);
	public Integer bajaSala(Integer id) throws Exception;
	public Integer modificarSala(TSala sala);
	public TSala mostrarSala(Integer id);
	public List<TSala> listarSala();
	public List<TClase> mostrarClasesPorSala(Integer idSala);
}
