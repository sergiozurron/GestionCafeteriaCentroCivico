package com.grupoms.app.negocio.proveedor;

import java.util.List;

public interface SAProveedor {

	Integer altaProveedor(TProveedor tproveedor);
	Boolean bajaProveedor(TProveedor tproveedor);
	Boolean modificarProveedor(TProveedor tproveedor);
	TProveedor mostrarProveedor(Integer id);
	List<TProveedor> mostrarListaProveedores();
}
