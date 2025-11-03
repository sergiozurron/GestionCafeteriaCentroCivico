package com.grupoms.app.negocio.pedido;
import java.util.Set;

public interface SAPedido {
     public Integer altaPedido(TPedido pedido);

    public Integer confirmarPedido(Integer idPedido);

    public Integer modificarPedido(TPedido pedido);

    public TPedido mostrarPedido(Integer idPedido);

    public Set<TPedido> mostrarPedidos();

    public Integer devolverPedido(Integer idPedido);

    public Integer vincularProducto(Integer idPedido, Integer idProducto, Integer cantidad);

    public Integer desvincularProducto(Integer idPedido, Integer idProducto);

    public Set<TPedido> mostrarPedidosPorEmpleado(Integer idEmpleado);

    public Set<TPedido> mostrarPedidosPorMesa(Integer idMesa);
}
