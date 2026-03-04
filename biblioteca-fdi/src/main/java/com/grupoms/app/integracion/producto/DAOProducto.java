package com.grupoms.app.integracion.producto;

import java.util.List;

import com.grupoms.app.negocio.producto.TProducto;

public interface DAOProducto {
	public Integer altaProducto(TProducto producto);

	public Boolean bajaProducto(TProducto producto);

	public Boolean modificarProducto(TProducto producto);

	public TProducto mostrarProducto(Integer id);

	public List<TProducto> mostrarListaProductos();

	void eliminaTodas();
	
	public List<TProducto> mostrarProductosPorProveedor(Integer idProveedor);
}