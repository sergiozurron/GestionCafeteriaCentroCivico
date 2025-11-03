package com.grupoms.app.integracion.ingrediente;

import java.util.Set;

public interface DAOIngrediente {
    public Integer altaIngrediente(TIngrediente ingrediente);

    public TIngrediente mostrarIngrediente(Integer id);

    public Set<TIngrediente> mostrarListaIngredientes();

    public Integer modificarIngrediente(TIngrediente tingrediente);

    public Integer bajaIngrediente(Integer id);
}
