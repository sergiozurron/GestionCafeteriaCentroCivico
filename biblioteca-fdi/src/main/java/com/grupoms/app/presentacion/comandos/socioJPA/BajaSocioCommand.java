package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaSocioCommand implements Command {
    @Override
    public Context execute(Object data) {
        Integer idSocio = (Integer) data;
        try {
            Integer exito = FactoriaSA.getInstance().creaSASocio().bajaSocio(idSocio);
            if (exito < 0)
                return new Context(Evento.BAJA_SOCIO_KO, null);
            return new Context(Evento.BAJA_SOCIO_OK, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Context(Evento.BAJA_SOCIO_KO, null);
        }
    }
}
