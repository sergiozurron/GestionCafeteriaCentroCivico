package com.grupoms.app.presentacion.factoria;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.IGUI;

public class FactoriaVistas {


    private static FactoriaVistas instance;
    private Map<String, IGUI> vistas;

    private FactoriaVistas() {
    	vistas = new HashMap<>();
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
    try {
        String[] partes = nombreVista.split("_");
        if (partes.length < 3)
            throw new IllegalArgumentException("Formato de nombreVista inválido: " + nombreVista);

        String comando = capitalize(partes[1]);

        // juntar el resto como entidad compuesta (por ejemplo INGREDIENTE_PRODUCTO → IngredienteProducto)
        StringBuilder entidadBuilder = new StringBuilder();
        for (int i = 2; i < partes.length; i++) {
            entidadBuilder.append(capitalize(partes[i].toLowerCase()));
        }
        String entidad = entidadBuilder.toString();

        String nombreClase =
            "com.grupoms.app.presentacion." +
            partes[2].toLowerCase() + // paquete base
            ".GUI_" + comando + entidad;

        Class<?> clazz = Class.forName(nombreClase);
        return (IGUI) clazz.getDeclaredConstructor().newInstance();

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
