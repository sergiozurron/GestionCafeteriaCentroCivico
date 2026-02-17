package com.grupoms.app.negocio.pedido;


import java.util.List;

public interface SAPedido {

    Integer cerrarPedido(TCarrito carrito);

    TPedidoLinea mostrarPedidoPorID(int id);

    List<TPedido> listarPedidos();

    Integer modificarPedido(TPedido pedido);

    boolean devolverLinea(TLineaVenta linea);
}
