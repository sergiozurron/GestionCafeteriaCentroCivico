package com.grupoms.app.integracion.ingrediente;

import java.util.List;
import com.grupoms.app.negocio.ingrediente.*;

public interface DAOIngrediente {
	public Integer crearIngrediente(TIngrediente ingrediente);

	public TIngrediente mostrarIngrediente(Integer id);

	public List<TIngrediente> mostrarListaIngredientes();

	public List<TIngrediente> listarIngredientesPorProducto(Integer idProducto);

	public List<TIngrediente> mostrarIngredientesProveedor(Integer idProveedor);

	public Boolean modificarIngrediente(TIngrediente tingrediente);

	public Boolean bajaIngrediente(TIngrediente ingrediente);

}
