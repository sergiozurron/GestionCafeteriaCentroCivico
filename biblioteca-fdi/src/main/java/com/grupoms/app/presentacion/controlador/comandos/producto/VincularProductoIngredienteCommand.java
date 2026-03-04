package com.grupoms.app.presentacion.controlador.comandos.producto;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.producto.SAReceta;
import com.grupoms.app.negocio.producto.TEntradaReceta;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class VincularProductoIngredienteCommand implements Command {

    @Override
    public Context execute(Object data) {

        TEntradaReceta entrada = (TEntradaReceta) data;
        SAReceta sa = FactoriaSA.getInstance().creaSAReceta();

        Integer resultado = sa.vincularIngredienteAProducto(
                entrada.getProductoID(),
                entrada.getIngredienteID()
        );

        if (resultado != null && resultado > 0) {
            return new Context(Evento.VINCULAR_PRODUCTO_INGREDIENTE_OK, resultado);
        } else {
            return new Context(Evento.VINCULAR_PRODUCTO_INGREDIENTE_KO, resultado);
        }
    }
}
