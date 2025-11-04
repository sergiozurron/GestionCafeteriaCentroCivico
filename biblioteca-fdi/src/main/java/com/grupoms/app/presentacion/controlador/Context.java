package com.grupoms.app.presentacion.controlador;

public class Context {

	private Integer evento;
	private Object datos;

	public Context(int evento, Object datos) {
		this.evento = evento;
		this.datos = datos;
	}

	public Context() {
        //TODO Auto-generated constructor stub
    }

    public void setEvento(int evento) {
		this.evento = evento;
	}

	public Integer getEvento() {
		return evento;
	}

	public Object getDatos() {
		return datos;
	}

	public void setDato(Object dato){
		this.datos = dato;
	}
}
