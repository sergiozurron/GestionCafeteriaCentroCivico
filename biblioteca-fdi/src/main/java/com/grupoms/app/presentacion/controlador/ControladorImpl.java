package com.grupoms.app.presentacion.controlador;

import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class ControladorImpl extends Controlador {

	@Override
	public void handle(Context context) {
		CommandMapper commandMapper = CommandMapper.getInstance();
		Context respuesta = commandMapper.getCommand(context.getEvento()).execute(context.getDatos());
		//tendriamos que mirar si la factoria devuelve null o no
		FactoriaVistas.getInstance().creaVista(commandMapper.getView(context.getEvento())).actualizar(respuesta);
	}

}
