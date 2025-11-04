package com.grupoms.app.integracion.ingrediente;

import java.util.Set;
import com.grupoms.app.negocio.ingrediente.*;

public interface DAOIngrediente {
    public Integer crearIngrediente(TIngrediente ingrediente);

    public TIngrediente mostrarIngrediente(Integer id);

    public Set<TIngrediente> mostrarListaIngredientes();
    
    public Set<TIngrediente> mostrarIngredientePorProducto(Integer idProducto);

    public Set<TIngrediente> mostrarProveedorPorIngrediente(Integer idProveedor);

    public Boolean modificarIngrediente(TIngrediente tingrediente);

    public Boolean bajaIngrediente(Integer id);
}
