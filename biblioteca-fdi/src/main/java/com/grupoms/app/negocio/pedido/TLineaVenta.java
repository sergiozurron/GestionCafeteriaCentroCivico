package com.grupoms.app.negocio.pedido;

public class TLineaVenta {

	private Integer id;
	private Integer idPedido;
	private Integer idProducto;
	private Integer cantidad;
	private Double precioVenta;

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

	public Double getPrecio() {
		return precioVenta;
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

	public void setPrecioVenta(double double1) {

		this.precioVenta = double1;
	}

}
