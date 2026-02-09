package com.grupoms.app.negocio.pedido;


import java.util.Set;

public interface SAPedido {
	
	public Integer altaPedido(TPedido pedido);

    TPedidoLinea cerrarPedido(TCarrito carrito);

    TPedidoLinea mostrarPedidoPorID(int id);

    Set<TPedido> listarPedidos();

    boolean modificarPedido(TPedido pedido);

    boolean devolverLinea(TLineaVenta linea);
}
