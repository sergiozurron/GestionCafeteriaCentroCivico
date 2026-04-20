package com.grupoms.app.negocio.pedido;

import java.util.List;

import com.grupoms.app.negocio.mesa.TMesa;

public class TCarrito {
	private TPedido tPedido;
	private List<TLineaPedido> tLineasPedido;
	private TMesa tMesa;
	//Getters
	public TPedido getPedido() {
		return tPedido;
	}
	
	public List<TLineaPedido> getLineasPedido() {
        return tLineasPedido;
    }
	
	public TMesa getMesa() {
		return tMesa;
	}

    // Setters
    public void setPedido(TPedido tPedido) {
        this.tPedido = tPedido;
    }

    public void setLineasPedido(List<TLineaPedido> tLineasPedido) {
        this.tLineasPedido = tLineasPedido;
    }

    public void setMesa(TMesa mesa) {
    	this.tMesa = mesa;
    }
}
