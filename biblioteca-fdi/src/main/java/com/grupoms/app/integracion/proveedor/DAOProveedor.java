package com.grupoms.app.integracion.proveedor;

import java.util.List;
import com.grupoms.app.negocio.proveedor.TProveedor;

public interface DAOProveedor {

	Integer creaProveedor(TProveedor proveedor);

	TProveedor mostrarProveedor(int id);

	List<TProveedor> listarProveedores();

	Boolean modificarProveedor(TProveedor proveedor);

	Boolean bajaProveedor(TProveedor proveedor);
	
	TProveedor buscaProveedorPorNombre(String nombre);
}
