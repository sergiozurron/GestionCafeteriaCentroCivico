package com.grupoms.app.presentacion.controlador;

public interface Command {
	
	Context execute(Object data);
}
