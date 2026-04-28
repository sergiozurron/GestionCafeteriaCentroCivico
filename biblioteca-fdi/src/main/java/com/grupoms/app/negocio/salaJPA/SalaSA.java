package com.grupoms.app.negocio.salaJPA;

import java.util.List;


public interface SalaSA {
	public Integer altaSala(TSala sala);

	public Integer bajaSala(Integer id);

	public Integer modificarSala(TSala sala);

	public TSala mostrarSala(Integer id);

	public List<TSala> listarSala();

}
