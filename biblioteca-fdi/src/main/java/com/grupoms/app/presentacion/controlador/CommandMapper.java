package com.grupoms.app.presentacion.controlador;

import java.util.Map;

import com.grupoms.app.presentacion.factoria.FactoriaVistas;
import com.grupoms.app.presentacion.proveedor.AltaProveedorCommand;

public class CommandMapper {

	private static CommandMapper instance;

	private Map<Integer, CommandMapEntry> commands = Map.of(
			Evento.ALTA_PROVEEDOR, new CommandMapEntry(new AltaProveedorCommand(), FactoriaVistas.GUI_ALTA_PROVEEDOR)
			);

	public static CommandMapper getInstance() {
		if (instance == null) {
			instance = new CommandMapper();
		}
		return instance;
	}

	public Command getCommand(int commandId) {
		return commands.get(commandId).getCommand();
	}

	public String getView(int commandId) {
		return commands.get(commandId).getNombreVista();
	}

}
