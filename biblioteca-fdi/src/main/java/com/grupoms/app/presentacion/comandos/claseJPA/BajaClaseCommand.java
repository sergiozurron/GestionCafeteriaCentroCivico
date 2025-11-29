package com.grupoms.app.presentacion.comandos.claseJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaClaseCommand implements Command {

    @Override
    public Context execute(Object data) {
        Integer idClase = (Integer) data;
        int res = FactoriaSA.getInstance().creaSAClase().bajaClase(idClase);

        if (res <= 0)
            return new Context(Evento.BAJA_CLASE_KO, idClase);

        return new Context(Evento.BAJA_CLASE_OK, idClase);
    }
}
