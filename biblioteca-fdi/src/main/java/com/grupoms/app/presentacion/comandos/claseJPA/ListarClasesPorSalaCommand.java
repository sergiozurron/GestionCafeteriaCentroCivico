package com.grupoms.app.presentacion.comandos.claseJPA;

import com.grupoms.app.negocio.ClaseJPA.ClaseSA;
import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.comandos.Command;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

import java.util.List;

public class ListarClasesPorSalaCommand implements Command {

    @Override
    public Context execute(Object data) {

        int event;

        String idSalaStr = (String) data;
        Integer idSala = Integer.parseInt(idSalaStr);

        ClaseSA sa = FactoriaSA.getInstance().creaSAClase();
        List<TClase> clases = sa.listarClasesPorSala(idSala);

        if (clases != null) {
            event = Evento.LISTAR_CLASES_POR_SALA_OK;
        } else {
            event = Evento.LISTAR_CLASES_POR_SALA_KO;
        }

        return new Context(event, clases);
    }
}