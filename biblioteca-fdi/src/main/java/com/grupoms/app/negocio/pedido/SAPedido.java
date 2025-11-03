package com.grupoms.app.negocio.pedido;
import java.util.Set;

public interface SAPedido {
    void vincularProductoPedido(Integer idProducto, Integer idPedido);

    public void modificarPedido (TPedido pedido);

    public Set<TPedido> mostrarListaPedidos();

    public Set<TPedido> mostrarPedidosEmpleado(Integer idEmpleado);
    public Set<TPedido> mostrarPedidosMesa(Integer idMesa);

    public void devolverPedido(TPedido pedido);

    public void confirmarPedido(TPedido pedido);
}
