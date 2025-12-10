package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.socioJPA.SocioSA;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarSocioCommand implements Command {
    @Override
    public Context execute(Object data) {
        Integer id = -1;
        if (data instanceof Integer) {
            id = (Integer) data;
        }

        SocioSA sa = FactoriaSA.getInstance().creaSASocio();
        try {
            TSocio res = sa.mostrarSocio(id);
            return (res != null)
                    ? new Context(Evento.MOSTRAR_SOCIO_OK, res)
                    : new Context(Evento.MOSTRAR_SOCIO_KO, null);
        } catch (IllegalArgumentException e) {
            return new Context(Evento.MOSTRAR_SOCIO_KO, null);
        }
    }
}
