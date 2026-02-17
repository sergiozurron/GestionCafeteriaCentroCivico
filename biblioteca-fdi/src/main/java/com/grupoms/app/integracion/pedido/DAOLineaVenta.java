package com.grupoms.app.integracion.pedido;

import java.util.List;

import com.grupoms.app.negocio.pedido.TLineaVenta;
import com.grupoms.app.negocio.pedido.TPedidoLinea;

public interface DAOLineaVenta {
	
	Integer altaLineaVenta(TLineaVenta orden) throws Exception;

	Boolean bajaLineaVenta(Integer id) throws Exception;
	
	Integer modificarLineaVenta(TLineaVenta tLineaVenta);
	
	public TLineaVenta mostrarLineaPedido(Integer idPedido, Integer idProducto);
	
	public List<TLineaVenta> listarLineas();
	
	public List<TPedidoLinea> mostrarLineaPedidoPorPedido(Integer idPedido);
}
