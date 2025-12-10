package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.socioJPA.SocioSA;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ModificarSocioCommand implements Command {

    @Override
    public Context execute(Object data) {
        if(!(data instanceof TSocio)) {
            return new Context(Evento.MODIFICAR_SOCIO_KO,null);
        }

        TSocio s = (TSocio) data;
        SocioSA sa = FactoriaSA.getInstance().creaSASocio();
        Integer ok = sa.modificarSocio(s);
        if(ok>-1) {
            return new Context(Evento.MODIFICAR_SOCIO_OK,ok);
        }else {
            return new Context(Evento.MODIFICAR_SOCIO_KO,null);
        }
    }
}
