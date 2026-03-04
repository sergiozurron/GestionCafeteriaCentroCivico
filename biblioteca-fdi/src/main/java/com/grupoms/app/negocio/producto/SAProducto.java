package com.grupoms.app.negocio.producto;

import java.util.List;

public interface SAProducto {

	Integer altaProducto(TProducto producto);

	Boolean bajaProducto(TProducto producto);

	Boolean modificarProducto(TProducto producto);

	TProducto mostrarProducto(Integer id);

	List<TProducto> mostrarListaProductos();
	
	List<TProducto> mostrarProductosPorProveedor(Integer idProveedor);
}
