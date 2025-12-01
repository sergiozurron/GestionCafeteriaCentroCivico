package com.grupoms.app.presentacion.comandos.claseJPA;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarClaseCommand implements Command {

    @Override
    public Context execute(Object data) {
        TClase clase = (TClase) data;
        int idClase = FactoriaSA.getInstance().creaSAClase().modificarClase(clase);

        if (idClase == -1)
            return new Context(Evento.MODIFICAR_CLASE_KO, null);

        return new Context(Evento.MODIFICAR_CLASE_OK, idClase);
    }
}
