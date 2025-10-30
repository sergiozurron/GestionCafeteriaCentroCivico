package com.grupoms.app.presentacion;

import com.grupoms.app.presentacion.proveedor.AltaProveedorCommand;

public class CommandMapper {

	public static final int ALTA_PROVEEDOR = 1;

	private static CommandMapper instance;

	public static CommandMapper getInstance() {
		if (instance == null) {
			instance = new CommandMapper();
		}
		return instance;
	}

	private Object[] commands = { new AltaProveedorCommand() };

	public Command getCommand(int commandID) {
		return (Command) commands[commandID];
	}

	public String getView(int commandID) {
		return (String) commands[commandID];
	}

}
