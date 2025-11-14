package com.grupoms.app.negocio.materialJPA;

import java.util.Date;

public class TPintura extends TMaterial{
	
	protected int numero;
	protected Date fecha;
	
	public TPintura(String autor, int tipoProducto, int numero, Date fecha) {
		super(autor,tipoProducto);
		this.numero=numero;
		this.fecha=fecha;
	}
	
	//GETTERS
	
	public int getNumero() {
		return numero;
	}
	
	public Date getFecha() {
		return fecha;
	}
	//SETTERS
	
	public void setNumero(int numero) {
		this.numero=numero;
	}
	
	public void setFecha(Date fecha) {
		this.fecha=fecha;
	}
}
