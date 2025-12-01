package com.grupoms.app;

import javax.swing.SwingUtilities;

import com.grupoms.app.presentacion.factoria.FactoriaVistas;

public class App {

	public static void main(String[] args) {
		// Inicializar el EntityManager al inicio de la aplicación
		EntityManagerProvider.getEntityManager();
		FactoriaVistas.getInstance(); // Inicializar la factoria de vistas
		SwingUtilities.invokeLater(() -> new Principal());
	}
}
