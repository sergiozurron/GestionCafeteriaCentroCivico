package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TPedido;

import java.util.List;

public interface DAOPedido {
	public Integer altaPedido(TPedido pedido);

	public Boolean modificarPedido(TPedido pedido);

	public TPedido mostrarPedido(Integer idPedido);

	public List<TPedido> mostrarListaPedidos();

	public void devolverPedido(TPedido pedido);

	public List<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

	public List<TPedido> mostrarPedidosPorMesa(Integer idMesa);

}