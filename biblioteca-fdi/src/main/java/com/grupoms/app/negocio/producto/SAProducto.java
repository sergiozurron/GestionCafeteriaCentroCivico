package com.grupoms.app.negocio.producto;

import java.util.List;

public interface SAProducto {
    // Dar de alta un producto devuelve el ID generado
    Integer altaProducto(TProducto producto);

    // Dar de baja un producto devuelve true si se pudo dar de baja
    Boolean bajaProducto(TProducto producto);

    // Modificar un producto devuelve true si se pudo modificar
    Boolean modificarProducto(TProducto producto);

    // Consultar un producto por ID
    TProducto mostrarProducto(Integer id);

    // Consultar todos los productos activos
    List<TProducto> mostrarListaProductos();
}
