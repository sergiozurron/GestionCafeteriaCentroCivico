package com.grupoms.app.negocio.pedido;

import java.util.Set;

public class TCarrito {
	private TPedido tPedido;
	private Set<TLineaVenta> tLineasVenta;
	
	//Getters
	public TPedido getPedido() {
		return tPedido;
	}
	
	public Set<TLineaVenta> getLineasVenta() {
        return tLineasVenta;
    }

    // Setters
    public void setPedido(TPedido tPedido) {
        this.tPedido = tPedido;
    }

    public void setLineasVenta(Set<TLineaVenta> tLineasVenta) {
        this.tLineasVenta = tLineasVenta;
    }

}
