package com.grupoms.app.negocio.pedido;

import java.util.HashSet;
import java.util.Set;

public class TPedidoLinea {

    private TPedido tPedido;                 
    private Set<TLineaVenta> tLineasVenta;   

    // Getter 
    public Set<TLineaVenta> gettLineasVenta() {
        return tLineasVenta;
    }


    public void incluirLineaVenta(TLineaVenta linea) {
        if (tLineasVenta == null)
            tLineasVenta = new HashSet<>();
        tLineasVenta.add(linea);
    }

    // Getter y setter del pedido
    public TPedido gettPedido() {
        return tPedido;
    }

    public void settPedido(TPedido tPedido) {
        this.tPedido = tPedido;
    }
}
