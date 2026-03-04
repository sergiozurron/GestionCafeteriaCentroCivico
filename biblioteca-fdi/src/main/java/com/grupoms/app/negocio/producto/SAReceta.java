package com.grupoms.app.negocio.producto;

import java.util.List;

import com.grupoms.app.negocio.ingrediente.TIngrediente;

public interface SAReceta {
	
	Integer vincularIngredienteAProducto(Integer idProducto, Integer idIngrediente);
	
	Integer desvincularIngredienteDeProducto(Integer idProducto, Integer idIngrediente);
	
	List<TIngrediente> listarIngredientesProducto(Integer idProducto);

}
