package com.grupoms.app.presentacion.controlador;

public abstract interface Command {
	
	public abstract Context execute(Object data);
}
