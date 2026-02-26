package com.grupoms.app.negocio.pedido;

import java.util.List;

public class TCarrito {
	private TPedido tPedido;
	private List<TLineaPedido> tLineasVenta;
	
	//Getters
	public TPedido getPedido() {
		return tPedido;
	}
	
	public List<TLineaPedido> getLineasVenta() {
        return tLineasVenta;
    }

    // Setters
    public void setPedido(TPedido tPedido) {
        this.tPedido = tPedido;
    }

    public void setLineasVenta(List<TLineaPedido> tLineasVenta) {
        this.tLineasVenta = tLineasVenta;
    }

}
