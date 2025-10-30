package com.grupoms.app.presentacion;

public class ControladorImpl extends Controlador {

	@Override
	public void handle(Context context) {
		CommandMapper commandMapper = CommandMapper.getInstance();
		Context respuesta = commandMapper.getCommand(context.getEvento()).execute(context.getDatos());
		// FactoriaVista.creaVista(commandMapper.getView(respuesta.getEvento())).actualizar(respuesta.getDatos());
	}

}
