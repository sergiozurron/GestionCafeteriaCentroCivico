package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TPedido;


import java.util.List;

public interface DAOPedido {
	
	Boolean modificarPedido(TPedido pedido);
	
	Integer altaPedido (TPedido pedido);
	
	TPedido mostrarPedido(Integer id);
	
	List<TPedido> mostrarListaPedidos();
	
	List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

	List<TPedido> mostrarPedidosPorMesa(Integer idMesa);
	
	Boolean devolverPedido(Integer id);

}