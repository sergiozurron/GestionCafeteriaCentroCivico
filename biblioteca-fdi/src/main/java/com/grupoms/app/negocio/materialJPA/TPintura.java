package com.grupoms.app.negocio.materialJPA;

import java.util.Date;

public class TPintura extends TMaterial{
	
	protected int numero;
	protected String fecha;
	
	public TPintura(String autor, int tipoProducto, int numero, String fecha) {
		super(autor,tipoProducto);
		this.numero=numero;
		this.fecha=fecha;
	}
	
	public TPintura() {}
	
	//GETTERS
	public int getNumero() {
		return numero;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	//SETTERS
	public void setNumero(int numero) {
		this.numero=numero;
	}
	
	public void setFecha(String fecha) {
		this.fecha=fecha;
	}
}
