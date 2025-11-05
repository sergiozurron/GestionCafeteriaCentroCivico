package com.grupoms.app.negocio.mesa;

import java.util.List;

import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.mesa.DAOMesa;

public class SAMesaImp implements SAMesa{
	private DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
	
	@Override
	public int altaMesa(TMesa mesa) {
		
		TMesa mesaExistente = daoMesa.mostrarMesa(mesa.getId());
		if (mesaExistente != null && mesaExistente.getActivo()) {
			return -1;
		}
		mesa.setActivo(true);
		daoMesa.altaMesa(mesa);
		return mesa.getId();
	}
	
	@Override
	public void bajaMesa(Integer id) {
		TMesa mesaExistente = daoMesa.mostrarMesa(id);
		if(mesaExistente == null || !mesaExistente.getActivo()) {
			throw new IllegalArgumentException("No existe esa mesa");
		}
		mesaExistente.setActivo(false);
		daoMesa.bajaMesa(id);
	}
	
	@Override
	public void modificarMesa(TMesa mesa) {
		TMesa mesaExistente = daoMesa.mostrarMesa(mesa.getId());
		
		if (mesaExistente != null && mesaExistente.getActivo()) {
			throw new IllegalArgumentException("No existe esa mesa");
		}
		
		mesaExistente.setNumero(mesa.getNumero());
		mesaExistente.setUbicacion(mesa.getUbicacion());
		mesaExistente.setActivo(mesa.getActivo());
		daoMesa.modificarMesa(mesaExistente);
	}
	
	@Override
	public TMesa mostrarMesa(Integer id) {
		TMesa mesaExistente = daoMesa.mostrarMesa(id);
		if (mesaExistente != null && mesaExistente.getActivo()) {
			throw new IllegalArgumentException("No existe esa mesa");
		}
		return mesaExistente;
	}
	
	@Override
	public List<TMesa> mostrarListaMesa(){
		return daoMesa.mostrarListaMesa();
	}

}
