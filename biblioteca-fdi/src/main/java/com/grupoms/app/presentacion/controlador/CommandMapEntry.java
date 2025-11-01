package com.grupoms.app.presentacion.controlador;

public class CommandMapEntry {

	private Command command;
	private String nombreVista;
	
	public CommandMapEntry(Command command, String nombreVista) {
		this.command = command;
		this.nombreVista = nombreVista;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public String getNombreVista() {
		return nombreVista;
	}

	public void setNombreVista(String nombreVista) {
		this.nombreVista = nombreVista;
	}
	
}
