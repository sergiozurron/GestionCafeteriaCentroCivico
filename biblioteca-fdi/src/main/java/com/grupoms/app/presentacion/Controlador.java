package com.grupoms.app.presentacion;

public abstract class Controlador {

	private static Controlador instance;
	
	public static Controlador getInstance() {
		if (instance == null) {
			instance = new ControladorImpl();
		}
		return instance;
	}
	
	public abstract void handle(Context context);
	
}
