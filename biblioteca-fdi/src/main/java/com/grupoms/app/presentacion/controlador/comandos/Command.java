package com.grupoms.app.presentacion.controlador.comandos;

import com.grupoms.app.presentacion.controlador.Context;

public abstract interface Command {
	public abstract Context execute(Object data);
}
