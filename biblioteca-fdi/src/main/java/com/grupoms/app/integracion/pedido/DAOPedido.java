package com.grupoms.app.integracion.pedido;

import com.grupoms.app.negocio.pedido.TPedido;

import java.util.Set;


public interface DAOPedido{
    public Integer confirmarPedido(TPedido pedido);

    public TPedido mostrarPedido(Integer id);

    public Set<TPedido> mostrarListaPedidos();

    public Integer modificarPedido (TPedido tpedido);

    public Integer devolverPedido(Integer id);
}