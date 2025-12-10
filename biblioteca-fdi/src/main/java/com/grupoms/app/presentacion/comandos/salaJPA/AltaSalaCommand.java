package com.grupoms.app.presentacion.comandos.salaJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class AltaSalaCommand implements Command {

    @Override
    public Context execute(Object data) {
        TSala sala = (TSala) data;
        int idClase = FactoriaSA.getInstance().creaSASala().altaSala(sala);

        if (idClase == -1)
            return new Context(Evento.ALTA_SALA_KO, idClase);

        return new Context(Evento.ALTA_SALA_OK, idClase);
    }
}
