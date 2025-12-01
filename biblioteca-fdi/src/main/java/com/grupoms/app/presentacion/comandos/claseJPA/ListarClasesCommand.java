package com.grupoms.app.presentacion.comandos.claseJPA;

import java.util.List;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarClasesCommand implements Command {

    @Override
    public Context execute(Object data) {
        List<TClase> lista = FactoriaSA.getInstance().creaSAClase().listarClase();

        if (lista == null || lista.isEmpty())
            return new Context(Evento.LISTAR_CLASES_KO, null);

        return new Context(Evento.LISTAR_CLASES_OK, lista);
    }
}
