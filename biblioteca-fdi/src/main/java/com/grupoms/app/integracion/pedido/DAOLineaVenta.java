package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TLineaVenta;

public interface DAOLineaVenta {
	Integer altaLineaVenta(TLineaVenta orden) throws Exception;

	Boolean bajaLineaVenta(Integer id) throws Exception;
	
	Integer modificarLineaVenta(TLineaVenta tLineaVenta);
}
