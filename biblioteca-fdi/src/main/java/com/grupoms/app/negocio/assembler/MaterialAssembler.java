package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.materialJPA.BOMaterial;
import com.grupoms.app.negocio.materialJPA.TMaterial;

public class MaterialAssembler {
	 // Convierte campos comunes BO → DTO
	public static TMaterial entityToTransfer(BOMaterial bo) {
	    TMaterial dto = new TMaterial();
	    dto.setAutor(bo.getAutor());
	    dto.setTipoMaterial(bo.getTipoMaterial());
	    dto.setActivo(bo.getActivo());
	    dto.setID(bo.getID());
	    return dto;  // debe devolver TMaterial
	}


    // Convierte campos comunes DTO → BO
    protected static BOMaterial transferToEntity(TMaterial dto) {
    	BOMaterial bo = new BOMaterial();
        bo.setAutor(dto.getAutor());
        bo.setTipoMaterial(dto.getTipoMaterial());
        bo.setActivo(dto.getActivo());
        bo.setID(dto.getID());
        return bo;
    }
}
