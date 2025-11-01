package com.grupoms.app.presentacion.controlador;

import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class ControladorImpl extends Controlador {

	@Override
	public void handle(Context peticion) {
		CommandMapper commandMapper = CommandMapper.getInstance();
		Context respuesta = commandMapper.getCommand(peticion.getEvento()).execute(peticion.getDatos());
		FactoriaVistas.getInstance().creaVista(commandMapper.getView(peticion.getEvento())).actualizar(respuesta);
	}

}
