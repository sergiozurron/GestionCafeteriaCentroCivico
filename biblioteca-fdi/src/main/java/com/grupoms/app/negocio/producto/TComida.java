package com.grupoms.app.negocio.producto;

public class TComida extends TProducto {

	private Integer calorias;
	private Integer tiempoPreparacion;

	public TComida() {
		super("Comida");
	}

	@Override
	public Integer getCalorias() {
		return calorias;
	}

	@Override
	public void setCalorias(Integer calorias) {
		this.calorias = calorias;
	}

	@Override
	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	@Override
	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	@Override
	public Integer getTamanho() {
		return null;
	}

	@Override
	public void setTamanho(Integer tamanho) {

	}
}
