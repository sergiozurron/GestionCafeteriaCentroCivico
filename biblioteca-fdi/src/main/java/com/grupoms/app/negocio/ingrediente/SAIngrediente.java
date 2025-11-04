package com.grupoms.app.negocio.ingrediente;

import java.util.Set;

import com.grupoms.app.negocio.pedido.TPedido;

public interface SAIngrediente{
        public Integer crearIngrediente(TIngrediente ingrediente);

        public Boolean bajaIngrediente(Integer ID);

        public Boolean modificarIngrediente(TIngrediente ingrediente);

        public TIngrediente mostrarIngrediente(Integer ID);

        public Set<TIngrediente> mostrarListaIngredientes();

        public Set<TIngrediente> mostrarIngredientePorProducto(Integer IDProducto);

        public Set<TIngrediente> mostrarProveedorPorIngrediente(TIngrediente ingrediente);

        public void vincularProducto(Integer idIngrediente, Integer idProducto, Integer cantidad);

}
