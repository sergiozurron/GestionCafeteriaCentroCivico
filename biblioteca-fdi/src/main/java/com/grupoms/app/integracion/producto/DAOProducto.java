package com.grupoms.app.integracion.producto;

import java.util.List;

import com.grupoms.app.negocio.producto.TProducto;

public interface DAOProducto {
    public Integer altaProducto(TProducto producto);
    public Integer bajaProducto(Integer id);
    public Integer modificarProducto(TProducto producto);
    public TProducto mostrarProducto(Integer id);
    public List<TProducto> mostrarListaProductos();
    void eliminaTodas();
}