package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TOrden;

public interface DAOOrden {
	Integer altaOrden(TOrden orden) throws Exception;

	Boolean bajaOrden(Integer id) throws Exception;
}
