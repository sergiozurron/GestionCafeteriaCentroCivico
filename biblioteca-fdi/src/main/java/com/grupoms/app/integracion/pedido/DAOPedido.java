package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TPedido;

import java.util.Set;


public interface DAOPedido{
    public Integer altaPedido(TPedido pedido);

    public Boolean modificarPedido(TPedido pedido);

    public TPedido mostrarPedido(Integer idPedido);

    public Set<TPedido> mostrarListaPedidos();

    public void devolverPedido(TPedido pedido);

    public Set<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

    public Set<TPedido> mostrarPedidosPorMesa(Integer idMesa);

}