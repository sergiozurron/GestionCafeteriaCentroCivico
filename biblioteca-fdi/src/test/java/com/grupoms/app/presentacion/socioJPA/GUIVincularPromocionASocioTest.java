package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

import org.junit.jupiter.api.Test;

public class GUIVincularPromocionASocioTest {

    @Test
    public void actualizar_nullContext_doesNotThrow() {
        GUI_VincularPromocionASocio gui = new GUI_VincularPromocionASocio();
        // Llamada a actualizar con null no debe lanzar excepciones
        gui.actualizar(null);
        gui.dispose();
    }

    @Test
    public void actualizar_OK_KO_cases_doNotThrow() {
        GUI_VincularPromocionASocio gui = new GUI_VincularPromocionASocio();
        Context ok = new Context(Evento.VINCULAR_PROMOCION_OK, null);
        Context ko = new Context(Evento.VINCULAR_PROMOCION_KO, null);
        // Llamadas: no lanzan excepciones
        gui.actualizar(ok);
        gui.actualizar(ko);
        gui.dispose();
    }
}
