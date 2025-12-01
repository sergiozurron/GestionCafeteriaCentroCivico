package com.grupoms.app.negocio.materialJPA;

import java.util.List;

public interface MaterialSA {
	
	
	public Integer bajaMaterial(Integer id) throws Exception;
	
	public List<TMaterial> listarMateriales();
		
	public Integer modificarMaterial(TMaterial material);
	
	public TMaterial mostrarMaterial(Integer id);

	public Integer altaMaterial(TMaterial material);
}
