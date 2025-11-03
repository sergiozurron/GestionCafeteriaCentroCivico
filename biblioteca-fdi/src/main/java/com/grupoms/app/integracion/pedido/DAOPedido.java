package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TPedido;

import java.util.Set;


public interface DAOPedido{
    public Integer altaPedido(TPedido pedido);

    public Integer modificarPedido(TPedido pedido);

    public TPedido mostrarPedido(Integer idPedido);

    public Set<TPedido> mostrarPedidos();

    public void devolverPedido(TPedido pedido);

    public Integer vincularProducto(Integer idPedido, Integer idProducto, Integer cantidad);

    public Integer desvincularProducto(Integer idPedido, Integer idProducto);

    public Set<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

    public Set<TPedido> mostrarPedidosPorMesa(Integer idMesa);

}