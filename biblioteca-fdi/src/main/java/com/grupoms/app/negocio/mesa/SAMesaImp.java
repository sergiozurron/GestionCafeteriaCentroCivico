package com.grupoms.app.negocio.mesa;

import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.integracion.factoria.FactoriaDAO;
import com.grupoms.app.integracion.mesa.DAOMesa;

public class SAMesaImp implements SAMesa{
	private DAOMesa daoMesa = FactoriaDAO.getInstancia().creaDAOMesa();
	
	@Override
	public Integer altaMesa(TMesa mesa) {
		Transaction t =null;
		Integer idGenerado =null;
		try{
			t = TransactionManager.getInstance().newTransaction();
			t.start();
			mesa.setActivo(true);
			idGenerado = daoMesa.altaMesa(mesa);
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(t!=null){
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
			}
		}
		return idGenerado;
	}
	
	@Override
	public Boolean bajaMesa(TMesa mesa) {
		Transaction t = null;
        Boolean exito = false;
		
        try {
            // 1. Iniciar transacción
            t = TransactionManager.getInstance().newTransaction();
            t.start();
            // 2. Inicializar campos del ingrediente
            mesa.setActivo(false);
            daoMesa.bajaIngrediente(mesa);
            
            // 4. Commit
            t.commit();
            exito = true;

        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) {
                try { t.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            }
        }
        return exito;
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
