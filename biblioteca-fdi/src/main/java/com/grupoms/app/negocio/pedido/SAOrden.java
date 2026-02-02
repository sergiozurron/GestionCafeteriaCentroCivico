package com.grupoms.app.negocio.pedido;

public interface SAOrden {
	Integer altaOrden(TLineaVenta orden);

	void vincularProducto(TLineaVenta orden);

	TLineaVenta mostrarOrden(Integer idOrden);

	Boolean bajaOrden(TLineaVenta orden);
}
