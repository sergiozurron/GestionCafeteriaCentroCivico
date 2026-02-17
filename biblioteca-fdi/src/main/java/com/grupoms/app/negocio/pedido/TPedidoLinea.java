package com.grupoms.app.negocio.pedido;

import java.util.HashSet;
import java.util.Set;

public class TPedidoLinea {

    private TPedido tPedido;                 
    private Set<TPedidoLinea> tPedidoLinea;   

    // Getter 
    public Set<TPedidoLinea> gettLineasVenta() {
        return tPedidoLinea;
    }


    public void incluirLineaVenta(TPedidoLinea linea) {
        if (tPedidoLinea == null)
        	tPedidoLinea = new HashSet<>();
        tPedidoLinea.add(linea);
    }

    // Getter y setter del pedido
    public TPedido gettPedido() {
        return tPedido;
    }

    public void settPedido(TPedido tPedido) {
        this.tPedido = tPedido;
    }
}
