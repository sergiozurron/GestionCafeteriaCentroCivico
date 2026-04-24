package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.socioJPA.SocioSA;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

import java.util.List;

public class CalcularCuotaPromocionCommand implements Command {

    @Override
    public Context execute(Object data) {

        SocioSA sa = FactoriaSA.getInstance().creaSASocio();

        try {
            int idPromocion = (Integer) data;

            List<TSocio> lista = sa.aplicarPromocion(idPromocion);

            return new Context(Evento.APLICAR_PROMOCION_OK, lista);

        } catch (Exception e) {
            return new Context(Evento.APLICAR_PROMOCION_KO, null);
        }
    }
}
