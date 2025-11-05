package com.grupoms.app.negocio.ingrediente;

import java.util.List;

public interface SAIngrediente{
        public Integer crearIngrediente(TIngrediente ingrediente);

        public Boolean bajaIngrediente(TIngrediente ingrediente);

        public Boolean modificarIngrediente(TIngrediente ingrediente);

        public TIngrediente mostrarIngrediente(Integer ID);

        public List<TIngrediente> mostrarListaIngredientes();

        public List<TIngrediente> mostrarIngredientePorProducto(Integer IDProducto);

        public List<TIngrediente> mostrarProveedorPorIngrediente(TIngrediente ingrediente);

        public void vincularProducto(Integer idIngrediente, Integer idProducto, Integer cantidad);


}
