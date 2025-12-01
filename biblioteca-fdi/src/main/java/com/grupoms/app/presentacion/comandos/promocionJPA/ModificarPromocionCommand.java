package com.grupoms.app.presentacion.comandos.promocionJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.comandos.Command;
import com.grupoms.app.negocio.PromocionJPA.PromocionSA;
import com.grupoms.app.negocio.PromocionJPA.TPromocion;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class ModificarPromocionCommand implements Command {

    @Override
    public Context execute(Object data) {
        if (!(data instanceof TPromocion)) {
            return new Context(Evento.MODIFICAR_PROMOCION_KO, null);
        }

        TPromocion promocion = (TPromocion) data;
        PromocionSA sa = FactoriaSA.getInstance().creaSAPromocion();
        Integer ok = sa.modificarPromocion(promocion);
        if (ok > -1) {
            return new Context(Evento.MODIFICAR_PROMOCION_OK, ok);
        } else {
            return new Context(Evento.MODIFICAR_PROMOCION_KO, null);
        }
    }
}