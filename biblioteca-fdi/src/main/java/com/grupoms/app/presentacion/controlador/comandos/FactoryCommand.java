package com.grupoms.app.presentacion.controlador.comandos;

public abstract class FactoryCommand {
	private static FactoryCommand instance;

	public static FactoryCommand getInstance() {
		if (instance == null)
			instance = new FactoryCommandImp();
		return instance;
	}

	public abstract Command getCommand(Integer event);

	public abstract String getView(Integer event);
}
