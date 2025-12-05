package com.grupoms.app;

import javax.swing.SwingUtilities;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class App {

	public static void main(String[] args) {
		// Inicializar el EntityManager al inicio de la aplicación
		EntityManagerSingleton.getEMF().createEntityManager();
		FactoriaVistas.getInstance(); // Inicializar vistas
		SwingUtilities.invokeLater(() -> new Principal());
	}
}
