package com.grupoms.app.presentacion.controlador;

public class Context {

	private Integer evento;
	private Object datos;

	public Context(int evento, Object datos) {
		this.evento = evento;
		this.datos = datos;
	}

	public Context(Integer evento) {
		this.evento = evento;
	}

	public void setEvento(int evento) {
		this.evento = evento;
	}

	public int getEvento() {
		return evento;
	}

	public Object getDatos() {
		return datos;
	}
}
