package com.grupoms.app.negocio.pedido;

public class TLineaPedido {

	private Integer id;
	private Integer idPedido;
	private Integer idProducto;
	private Integer cantidad;
	private boolean activo;

	public boolean getActivo() {
		return activo;
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getPedidoId() {
		return idPedido;
	}

	public Integer getProductoId() {
		return idProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setActivo(boolean ac) {
		activo = ac;
	}
	public void setId(int int1) {
		this.id = int1;
	}

	public void setPedidoID(int int1) {
		this.idPedido = int1;
	}

	public void setProductID(int int1) {
		this.idProducto = int1;
	}

	public void setCantidad(int int1) {
		this.cantidad = int1;
	}

}
