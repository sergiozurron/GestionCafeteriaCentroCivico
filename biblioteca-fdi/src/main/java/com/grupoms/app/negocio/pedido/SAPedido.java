package com.grupoms.app.negocio.pedido;

import java.util.List;

public interface SAPedido {
	public Integer altaPedido(TPedido pedido);

	public Boolean confirmarPedido(TPedido pedido);

	public Integer modificarPedido(TPedido pedido);

	public TPedido mostrarPedido(Integer idPedido);

	public List<TPedido> mostrarPedidos();

	public void devolverPedido(TPedido pedido);

	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa);
}
