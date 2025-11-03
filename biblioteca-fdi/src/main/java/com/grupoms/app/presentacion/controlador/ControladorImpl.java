package com.grupoms.app.presentacion.controlador;

import com.grupoms.app.presentacion.controlador.comandos.Command;
import com.grupoms.app.presentacion.controlador.comandos.FactoryCommand;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class ControladorImpl extends Controlador {

	@Override
	public void handle(Context context) {
		FactoryCommand factory = FactoryCommand.getInstance();
		Command command = factory.getCommand(context.getEvento());

		if (command != null) {
			Context respuesta = command.execute(context.getDatos());
			String vista = factory.getView(context.getEvento());
			if (vista != null)
				FactoriaVistas.getInstance().creaVista(vista).actualizar(respuesta);
		}
	}

}
