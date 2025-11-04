package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAOrden;
import com.grupoms.app.negocio.pedido.TOrden;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class BajaOrdenCommand implements Command{

    @Override
    public Context execute(Object data) {
        if (!(data instanceof TOrden)) {
            return new Context(Evento.BAJA_ORDEN_KO, null);
        }

        TOrden orden = (TOrden) data;
        SAOrden saOrden = FactoriaSA.getInstance().creaSAOrden();

        try {
            saOrden.bajaOrden(orden);
            return new Context(Evento.BAJA_ORDEN_OK, orden);
        } catch (Exception e) {
            return new Context(Evento.BAJA_ORDEN_KO, null);
        }
    }
    
}
