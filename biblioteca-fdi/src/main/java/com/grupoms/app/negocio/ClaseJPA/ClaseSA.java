package com.grupoms.app.negocio.ClaseJPA;

import java.util.List;

/**
 * Service interface for Clase use cases.
 * 1) Basic CRUD operations for Clase.
 * 2) Linking and unlinking Ejemplar to/from Clase.
 */
public interface ClaseSA {

    Integer altaClase(TClase clase);

    Integer bajaClase(Integer id);

    Integer modificarClase(TClase clase);

    TClase mostrarClase(Integer id);

    List<TClase> listarClase();

    Integer vincularEjemplarAClase(Integer idClase, Integer idEjemplar);
   
    Integer desvincularEjemplarDeClase(Integer idClase, Integer idEjemplar);
}
