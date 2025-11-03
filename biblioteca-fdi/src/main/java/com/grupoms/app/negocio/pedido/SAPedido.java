package com.grupoms.app.negocio.pedido;
import java.util.Set;

public interface SAPedido {
    public void altaPedido(TPedido pedido);

    public void modificarPedido(TPedido pedido);

    public void confirmarPedido(TPedido pedido);

    public void devolverPedido(TPedido pedido);

    public void vincularProductoPedido(Integer idProducto, Integer idPedido);

    public void desvincularProductoPedido(Integer idProducto, Integer idPedido);

    public TPedido mostrarPedido(Integer idPedido);

    public Set<TPedido> mostrarListaPedidos();

    public Set<TPedido> mostrarPedidosEmpleado(Integer idEmpleado);

    public Set<TPedido> mostrarPedidosMesa(Integer idMesa);
}
