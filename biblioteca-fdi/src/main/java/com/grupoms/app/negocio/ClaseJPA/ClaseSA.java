package com.grupoms.app.negocio.ClaseJPA;

import java.util.List;

public interface ClaseSA {

	Integer altaClase(TClase clase);

	Integer bajaClase(Integer id);

	Integer modificarClase(TClase clase);

	TClase mostrarClase(Integer id);

	List<TClase> listarClase();

	List<TClase> listarClasesPorSala(Integer idSala);

	Integer vincularEjemplarAClase(Integer idClase, Integer idEjemplar);

	Integer desvincularEjemplarDeClase(Integer idClase, Integer idEjemplar);
}
