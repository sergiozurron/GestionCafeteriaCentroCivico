package com.grupoms.app.negocio.pedido;
import java.util.Set;

public interface SAPedido {
    public void altaPedido(TPedido pedido);

    public void modificarPedido(TPedido pedido);

    public void confirmarPedido();

    public void devolverPedido(TPedido pedido);

    public void vincularProductoPedido(TProducto producto);

    public void desvincularProductoPedido(TProducto producto);

    public TPedido mostrarPedido(TPedido pedido);

    public Set<TPedido> mostrarListaPedidos();

    public Set<TPedido> mostrarPedidosEmpleado(Integer idEmpleado);

    public Set<TPedido> mostrarPedidosMesa(Integer idMesa);
}
