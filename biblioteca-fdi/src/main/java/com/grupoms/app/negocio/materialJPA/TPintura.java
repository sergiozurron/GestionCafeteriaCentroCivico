package com.grupoms.app.negocio.materialJPA;

public class TPintura extends TMaterial {

	protected int numero;
	protected String fecha;

	public TPintura(String autor, int tipoProducto, String nombre, int numero, String fecha) {
		super(autor, tipoProducto, nombre);
		this.numero = numero;
		this.fecha = fecha;
	}

	public TPintura() {
	}

	public int getNumero() {
		return numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
