package com.grupoms.app.presentacion;

public class Context {

	private int evento;
	private Object datos;
	
	public void setEvento(int evento) {
		this.evento = evento;
	}
	
	public int getEvento() {
		return evento;
	}
	
	public void setDatos(Object datos) {
		this.datos = datos;
	}
	
	public Object getDatos() {
		return datos;
	}
}
