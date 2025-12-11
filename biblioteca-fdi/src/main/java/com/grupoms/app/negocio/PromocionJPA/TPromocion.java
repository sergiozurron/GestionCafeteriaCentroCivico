package com.grupoms.app.negocio.PromocionJPA;

public class TPromocion {
	protected Integer id;
	protected Double descuento;
	protected String tipo;
	protected boolean activo;

	public TPromocion(Double descuento, String tipo) {
		this.descuento = descuento;
		this.tipo = tipo;
	}

	public TPromocion() {
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Integer getId() {
		return id;
	}

	public Double getDescuento() {
		return descuento;
	}

	public String getTipo() {
		return tipo;
	}

	public boolean getActivo() {
		return activo;
	}
}
