package com.grupoms.app.presentacion.factoria;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.pedido.*;

public class FactoriaVistas {


    private static FactoriaVistas instance;
    private Map<String, IGUI> vistas = new HashMap<>();

    private FactoriaVistas() {

    }

    public static FactoriaVistas getInstance() {
        if (instance == null)
            instance = new FactoriaVistas();
        return instance;
    }

    /**
     * Crea o devuelve una vista (GUI) según el nombre que se pasa.
     * Ejemplo: "GUI_MOSTRAR_PEDIDO" → com.grupoms.app.presentacion.pedido.GUI_MostrarPedido
     */
    public IGUI creaVista(String nombreVista) {
        if (vistas.containsKey(nombreVista))
            return vistas.get(nombreVista);

        try {
            // Ejemplo de entrada: "GUI_MOSTRAR_PEDIDO"
            String[] partes = nombreVista.toLowerCase().split("_");

            if (partes.length < 3)
                throw new IllegalArgumentException("Formato de nombreVista inválido: " + nombreVista);

            String comando = partes[1]; // mostrar, alta, devolver...
            String entidad = partes[2]; // pedido, mesa, proveedor...

            // Construimos el nombre completo de la clase (paquete + nombre)
            String nombreClase =
                "com.grupoms.app.presentacion." + entidad + ".GUI_" +
                capitalize(comando) + capitalize(entidad);

            Class<?> clazz = Class.forName(nombreClase);
            IGUI gui = (IGUI) clazz.getDeclaredConstructor().newInstance();
            vistas.put(nombreVista, gui);
            return gui;

        } catch (Exception e) {
            System.err.println("No se pudo crear la vista: " + nombreVista);
            e.printStackTrace();
            return null;
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
