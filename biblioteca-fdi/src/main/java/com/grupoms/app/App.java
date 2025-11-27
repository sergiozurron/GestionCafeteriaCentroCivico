package com.grupoms.app;

import javax.swing.SwingUtilities;


public class App {

	public static void main(String[] args) {
		// Inicializar el EntityManager al inicio de la aplicación
		EntityManagerProvider.getEntityManager();
		SwingUtilities.invokeLater(() -> new Principal());	}
}
