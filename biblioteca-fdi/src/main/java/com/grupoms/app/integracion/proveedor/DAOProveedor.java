package com.grupoms.app.integracion.proveedor;

import com.grupoms.app.negocio.proveedor.TProveedor;

public interface DAOProveedor {

	void crea(TProveedor proveedor);
	TProveedor buscaPorNombre(String nombre);
	TProveedor buscaPorId(int id);
	void eliminaTodos();
	void actualiza(TProveedor proveedor);
}
