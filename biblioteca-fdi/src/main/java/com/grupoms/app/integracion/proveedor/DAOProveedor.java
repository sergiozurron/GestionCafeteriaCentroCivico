package com.grupoms.app.integracion.proveedor;

import java.util.List;
import com.grupoms.app.negocio.proveedor.TProveedor;

public interface DAOProveedor {

	Integer crea(TProveedor proveedor);

	TProveedor buscaPorNombre(String nombre);

	TProveedor buscaPorId(int id);

	List<TProveedor> listar();

	Boolean actualiza(TProveedor proveedor);

	Boolean baja(TProveedor proveedor);

	void eliminaTodos();
}
