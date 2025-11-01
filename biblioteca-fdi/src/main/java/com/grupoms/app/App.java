package com.grupoms.app;

import javax.swing.SwingUtilities;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> FactoriaVistas.getInstance().creaVista(FactoriaVistas.GUI_ALTA_PROVEEDOR).actualizar(new Context()));
	}
}
