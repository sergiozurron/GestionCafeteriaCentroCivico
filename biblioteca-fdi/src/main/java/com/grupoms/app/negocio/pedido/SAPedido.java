package com.grupoms.app.negocio.pedido;


import java.util.List;

public interface SAPedido {
	Boolean modificarPedido(TPedido pedido);
	
	Integer altaPedido(TPedido pedido);
	
	TCarrito mostrarPedido(Integer idPedido);
	
	List<TPedido> mostrarListaPedidos();
	
	List <TPedido> mostrarPedidosPorMesa(Integer idMesa);
	
	List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);
	
	void devolverPedido(Integer idPedido);
	
	Integer cerrarPedido(Integer idPedido);
	
}
