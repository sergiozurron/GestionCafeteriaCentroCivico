package com.grupoms.app.presentacion.factoria;

import java.util.HashMap;
import java.util.Map;
import com.grupoms.app.presentacion.IGUI;
import javax.swing.JFrame;

public class FactoriaVistas {

    private static FactoriaVistas instance;
    private Map<String, IGUI> vistas; // almacena instancias únicas de vistas

    private FactoriaVistas() {
        vistas = new HashMap<>();
    }

    public static FactoriaVistas getInstance() {
        if (instance == null)
            instance = new FactoriaVistas();
        return instance;
    }

    /**
     * Devuelve una vista única (singleton) por nombre.
     * Si ya existe, devuelve la misma instancia.
     * Si no existe, la crea y la guarda.
     */
    public IGUI creaVista(String nombreVista) {
        if (vistas.containsKey(nombreVista)) {
            IGUI vistaExistente = vistas.get(nombreVista);
            // Asegurarse de que la ventana JFrame esté visible
            if (vistaExistente instanceof JFrame) {
                ((JFrame) vistaExistente).setVisible(true);
            }
            return vistaExistente;
        }

        try {
            String[] partes = nombreVista.toLowerCase().split("_");
            if (partes.length < 3)
                throw new IllegalArgumentException("Formato de nombreVista inválido: " + nombreVista);

            String comando = partes[1];
            String entidad = partes[2];

            String nombreClase =
                "com.grupoms.app.presentacion." + entidad + ".GUI_" +
                capitalize(comando) + capitalize(entidad);

            Class<?> clazz = Class.forName(nombreClase);

            // Crear nueva instancia y guardarla
            IGUI vista = (IGUI) clazz.getDeclaredConstructor().newInstance();
            vistas.put(nombreVista, vista);

            if (vista instanceof JFrame) {
                ((JFrame) vista).setVisible(true);
            }

            return vista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
