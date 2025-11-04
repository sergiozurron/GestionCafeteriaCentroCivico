package com.grupoms.app.negocio.pedido;
import java.util.Set;

public interface SAPedido {
    public Integer altaPedido(TPedido pedido);

    public Boolean confirmarPedido(TPedido pedido);

    public Integer modificarPedido(TPedido pedido);

    public TPedido mostrarPedido(Integer idPedido);

    public Set<TPedido> mostrarPedidos();

    public void devolverPedido(TPedido pedido);

    void vincularProducto(TOrden orden);

    void desvincularProducto(TOrden orden);

    public Set<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

    public Set<TPedido> mostrarPedidosPorMesa(Integer idMesa);
}
