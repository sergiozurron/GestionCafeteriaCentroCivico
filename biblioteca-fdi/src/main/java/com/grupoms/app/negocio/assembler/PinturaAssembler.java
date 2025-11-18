package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.materialJPA.BOMaterial;
import com.grupoms.app.negocio.materialJPA.BOPintura;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.negocio.materialJPA.TPintura;

public class PinturaAssembler extends MaterialAssembler {
	
	public static TPintura toDTO(BOPintura bo) {
		TPintura dto = new TPintura();
		TMaterial baseDTO = entityToTransfer(bo);   // devuelve TMaterial con campos comunes
        dto.setAutor(baseDTO.getAutor());
        dto.setTipoMaterial(baseDTO.getTipoMaterial());
        dto.setActivo(baseDTO.getActivo());
        dto.setID(baseDTO.getID());
        dto.setFecha(bo.getFecha());
        dto.setNumero(bo.getNumero());
        return dto;
    }

    public static BOPintura toBO(TPintura dto) {
    	BOPintura bo = new BOPintura();
        BOMaterial baseBO = transferToEntity(dto);

    	bo.setAutor(baseBO.getAutor());
        bo.setTipoMaterial(baseBO.getTipoMaterial());
        bo.setActivo(baseBO.getActivo());
        bo.setID(baseBO.getID());
        bo.setFecha(dto.getFecha());
        bo.setNumero(dto.getNumero());
        return bo;
    }
}
