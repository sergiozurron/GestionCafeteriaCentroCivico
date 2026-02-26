package com.grupoms.app.negocio.pedido;

import java.util.HashSet;
import java.util.Set;

import com.grupoms.app.negocio.producto.TProducto;

public class TPedidoLinea {

    private TPedido tPedido;                 
    private Set<TLineaPedido> tPedidoLinea;   
    private Set<TProducto> productos;        

    public Set<TLineaPedido> gettLineasVenta() {
        return tPedidoLinea;
    }

    public void incluirLineaVenta(TLineaPedido linea) {
        if (tPedidoLinea == null)
            tPedidoLinea = new HashSet<>();
        tPedidoLinea.add(linea);
    }

    public Set<TProducto> getProductos() {
        return productos;
    }

    public void incluirProducto(TProducto producto) {
        if (productos == null)
            productos = new HashSet<>();
        productos.add(producto);
    }

    public TPedido gettPedido() {
        return tPedido;
    }

    public void settPedido(TPedido tPedido) {
        this.tPedido = tPedido;
    }
}
