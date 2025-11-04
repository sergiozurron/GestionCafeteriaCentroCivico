package com.grupoms.app.presentacion.controlador.comandos.pedido;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.pedido.SAPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class MostrarPedidoCommand implements Command {

    @Override
    public Context execute(Object data) {
        if (!(data instanceof Integer)) {
            return new Context(Evento.MOSTRAR_PEDIDO_KO, "El parámetro debe ser un ID de pedido (Integer).");
        }

        Integer idPedido = (Integer) data;
        SAPedido saPedido = FactoriaSA.getInstance().creaSAPedido();

        try {
            TPedido pedido = saPedido.mostrarPedido(idPedido);

            if (pedido != null)
                return new Context(Evento.MOSTRAR_PEDIDO_OK, pedido);
            else
                return new Context(Evento.MOSTRAR_PEDIDO_KO, "No se encontró ningún pedido con ese ID.");

        } catch (Exception e) {
            e.printStackTrace();
            return new Context(Evento.MOSTRAR_PEDIDO_KO, e.getMessage());
        }
    }
    
}
