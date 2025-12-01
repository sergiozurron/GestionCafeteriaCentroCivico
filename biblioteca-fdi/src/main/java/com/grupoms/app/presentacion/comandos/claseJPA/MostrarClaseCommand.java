package com.grupoms.app.presentacion.comandos.claseJPA;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarClaseCommand implements Command {

    @Override
    public Context execute(Object data) {
        Integer idClase = (Integer) data;
        TClase clase = FactoriaSA.getInstance().creaSAClase().mostrarClase(idClase);

        if (clase == null)
            return new Context(Evento.MOSTRAR_CLASE_KO, null);

        return new Context(Evento.MOSTRAR_CLASE_OK, clase);
    }
}
