package com.grupoms.app.negocio.producto;

import java.util.List;

public interface SAProducto {
    public Integer altaProducto(TProducto producto);
    public void bajaProducto(Integer id);
    public void modificarProducto(TProducto producto);
    public TProducto mostrarProducto(Integer id);
    public List<TProducto> mostrarProductos();
}