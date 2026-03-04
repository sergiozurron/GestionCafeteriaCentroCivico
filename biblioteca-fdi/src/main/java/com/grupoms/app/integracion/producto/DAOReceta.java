package com.grupoms.app.integracion.producto;

import java.util.List;

import com.grupoms.app.negocio.ingrediente.TIngrediente;

public interface DAOReceta {

	Integer desvincular(Integer idProducto, Integer idIngrediente);

	Integer vincular(Integer idProducto, Integer idIngrediente);

	List<TIngrediente> listarIngredientesProducto(Integer idProducto);

}
