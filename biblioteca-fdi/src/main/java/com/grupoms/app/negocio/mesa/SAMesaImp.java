package com.grupoms.app.negocio.mesa;

import java.util.List;

import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.mesa.DAOMesa;

public class SAMesaImp implements SAMesa{
	
	@Override
	public int altaMesa(TMesa mesa) {
		DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
		TMesa mesaExistente = daoMesa.buscaPorNumero(mesa.getNumero());
		if (mesaExistente != null && mesaExistente.getActivo()) {
			return -1;
		}
		mesa.setActivo(true);
		daoMesa.crea(mesa);
		return mesa.getId();
	}
	
	public void bajaMesa(Integer id) {
		DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
		TMesa mesaExistente = daoMesa.buscarPorId(id);
		if(mesaExistente == null || !mesaExistente.getActivo()) {
			return;
		}
		mesaExistente.setActivo(false);
	}
	
	public void modificarMesa(TMesa mesa) {
		DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
		TMesa mesaExistente = daoMesa.buscarPorId(mesa.getId());
		
		if (mesaExistente != null && mesaExistente.getActivo()) {
			return;
		}
		
		mesaExistente.setNumero(mesa.getNumero());
		mesaExistente.setUbicacion(mesa.getUbicacion());
		mesaExistente.setActivo(mesa.getActivo());
		daoMesa.update(mesaExistente);
	}
	
	public TMesa mostrarMesa(Integer id) {
		DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
		TMesa mesaExistente = daoMesa.buscarPorId(id);
		if (mesaExistente != null && mesaExistente.getActivo()) {
			return null;
		}
		return mesaExistente;
	}
	
	public List<TMesa> mostrarMesas(){
		DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
		return daoMesa.mostrarTodos();
	}
}
