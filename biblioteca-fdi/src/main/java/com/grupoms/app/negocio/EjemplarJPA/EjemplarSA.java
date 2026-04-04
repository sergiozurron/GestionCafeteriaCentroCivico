package com.grupoms.app.negocio.EjemplarJPA;

import java.util.Date;
import java.util.List;

public interface EjemplarSA {

	Integer altaEjemplar(TEjemplar ejemplar);

	Boolean bajaEjemplar(Integer idEjemplar);

	Boolean modificarEjemplar(TEjemplar ejemplar);

	TEjemplar mostrarEjemplar(Integer idEjemplar);

	List<TEjemplar> listarEjemplaresPorMaterial(Integer idMaterial);

	List<TEjemplar> listarEjemplares();
	
	List<TEjemplar> listarEjemplaresPrestadosPorAdultosPlenos(Date fechaInicio,Date fechaFin);
}
