package com.grupoms.app.presentacion.controlador;

public class Context {

	private int evento;
	private Object datos;

	public Context(int evento, Object datos) {
		this.evento = evento;
		this.datos = datos;
	}

	public Context() {
	}

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
