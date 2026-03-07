package com.grupoms.app.integracion.producto;

import java.util.List;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.negocio.producto.TEntradaReceta;

public interface DAOReceta {

	Integer desvincular(Integer idProducto, Integer idIngrediente);

	Integer vincular(Integer idProducto, Integer idIngrediente);

	List<TIngrediente> listarIngredientesProducto(Integer idProducto);
	
	TEntradaReceta mostrarLineaReceta(Integer idProducto, Integer idIngrediente);

}
