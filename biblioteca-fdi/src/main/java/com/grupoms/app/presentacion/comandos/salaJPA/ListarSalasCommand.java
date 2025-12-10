package com.grupoms.app.presentacion.comandos.salaJPA;

import java.util.List;

import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.negocio.salaJPA.SalaSA;
import com.grupoms.app.negocio.salaJPA.TSala;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.controlador.comandos.Command;

public class ListarSalasCommand implements Command{
	@Override
	public Context execute(Object data) {

		SalaSA sa = FactoriaSA.getInstance().creaSASala();
		try {
			List<TSala> lista = sa.listarSala();
            return new Context(Evento.LISTAR_SALAS_OK, lista);
		}catch (Exception e) {
            return new Context(Evento.LISTAR_SALAS_KO, null);
        }
	}

}