package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.socioJPA.SocioSA;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarSociosPorPromocionCommand implements Command {
    @Override
    public Context execute(Object data) {
        int event;
        Integer idPromocion = (Integer) data;
        SocioSA sa = FactoriaSA.getInstance().creaSASocio();

        java.util.List<TSocio> socios = sa.mostrarSociosPorPromocion(idPromocion);
        if (socios != null) {
            event = Evento.MOSTRAR_SOCIOS_POR_PROMOCION_OK;
        } else {
            event = Evento.MOSTRAR_SOCIOS_POR_PROMOCION_KO;
        }
        return new Context(event, socios);
    }
}
