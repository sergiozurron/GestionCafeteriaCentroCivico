package com.grupoms.app.presentacion.controlador.comandos;

import java.util.HashMap;
import java.util.Map;

import com.grupoms.app.presentacion.controlador.Evento;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;
import com.grupoms.app.presentacion.mesa.AltaMesaCommand;

public class FactoryCommandImp extends FactoryCommand {
    private Map<Integer, Command> commands = new HashMap<>();
	private Map<Integer, String> views = new HashMap<>();

    public FactoryCommandImp(){
        //cada comando que añadamos hay que añadirlo aqui
        commands.put(Evento.ALTA_MESA, new AltaMesaCommand());
        //views.put(Evento.ALTA_MESA, FactoriaVistas.GUI_ALTA_MESA);
    }
    @Override
    public Command getCommand(Integer event) {
        // TODO Auto-generated method stub
        return commands.get(event);
    }

    @Override
    public String getView(Integer event) {
        // TODO Auto-generated method stub
       return views.get(event);
    }
}
