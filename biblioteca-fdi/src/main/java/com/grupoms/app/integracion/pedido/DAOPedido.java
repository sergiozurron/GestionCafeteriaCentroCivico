package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TPedido;


import java.util.List;

public interface DAOPedido {
	Integer cerrarPedido (TPedido pedido);
	
	TPedido mostrarPedido(Integer id);
	
	List<TPedido> listarPedidos();
	
	Boolean modificarPedido(TPedido pedido);
	
	Integer devolverPedido(Integer id);

	List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

	List<TPedido> mostrarPedidosPorMesa(Integer idMesa);


}