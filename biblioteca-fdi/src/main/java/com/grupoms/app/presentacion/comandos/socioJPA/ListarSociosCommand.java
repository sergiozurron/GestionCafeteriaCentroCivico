package com.grupoms.app.presentacion.comandos.socioJPA;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.socioJPA.SocioSA;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

import java.util.List;

public class ListarSociosCommand implements Command {
    @Override
    public Context execute(Object data) {
        SocioSA sa = FactoriaSA.getInstance().creaSASocio();
        try {
            List<TSocio> lista = sa.listarSocios();
            return new Context(Evento.LISTAR_SOCIOS_OK, lista);
        }catch (Exception e) {
            // Cualquier excepción se traduce a KO
            return new Context(Evento.LISTAR_SOCIOS_KO, null);
        }
    }
}
