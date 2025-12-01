package com.grupoms.app.presentacion.comandos.salaJPA;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaSalaCommand implements Command {

    @Override
    public Context execute(Object data) {
        TClase clase = (TClase) data;
        int idClase = FactoriaSA.getInstance().creaSAClase().altaClase(clase);

        if (idClase == -1)
            return new Context(Evento.ALTA_SALA_KO, null);

        return new Context(Evento.ALTA_SALA_OK, idClase);
    }
}
