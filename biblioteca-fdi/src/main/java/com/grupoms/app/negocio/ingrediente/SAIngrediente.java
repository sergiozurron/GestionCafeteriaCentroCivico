package com.grupoms.app.negocio.ingrediente;

import java.util.List;

import com.grupoms.app.negocio.producto.TEntradaReceta;

public interface SAIngrediente {
	public Integer crearIngrediente(TIngrediente ingrediente);

	public Boolean bajaIngrediente(TIngrediente ingrediente);

	public Boolean modificarIngrediente(TIngrediente ingrediente);

	public TIngrediente mostrarIngrediente(Integer ID);

	public List<TIngrediente> mostrarListaIngredientes();

	public List<TEntradaReceta> mostrarIngredientesPorProducto(Integer IDProducto);

	public List<TIngrediente> mostrarIngredientesProveedor(Integer idProveedor);

}
