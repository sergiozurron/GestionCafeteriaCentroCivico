package com.grupoms.app.negocio.pedido;

import java.util.List;

public class TCarrito {
	private TPedido tPedido;
	private List<TLineaPedido> tLineasPedido;

	//Getters
	public TPedido getPedido() {
		return tPedido;
	}
	
	public List<TLineaPedido> getLineasPedido() {
        return tLineasPedido;
    }

    // Setters
    public void setPedido(TPedido tPedido) {
        this.tPedido = tPedido;
    }

    public void setLineasPedido(List<TLineaPedido> tLineasPedido) {
        this.tLineasPedido = tLineasPedido;
    }

}
