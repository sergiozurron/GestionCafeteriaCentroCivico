package com.grupoms.app.negocio.EjemplarJPA;

public interface EjemplarSA {
	
	Integer altaEjemplar(TEjemplar ejemplar);
	Boolean bajaEjemplar(Integer idEjemplar);
	Boolean modificarEjemplar(TEjemplar ejemplar);
}
