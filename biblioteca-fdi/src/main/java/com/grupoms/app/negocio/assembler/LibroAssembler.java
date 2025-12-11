package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.materialJPA.BOLibro;
import com.grupoms.app.negocio.materialJPA.BOMaterial;
import com.grupoms.app.negocio.materialJPA.TLibro;
import com.grupoms.app.negocio.materialJPA.TMaterial;

public class LibroAssembler extends MaterialAssembler {

	public static TLibro toDTO(BOLibro bo) {
		TLibro dto = new TLibro();
		TMaterial baseDTO = entityToTransfer(bo);
		dto.setAutor(baseDTO.getAutor());
		dto.setTipoMaterial(baseDTO.getTipoMaterial());
		dto.setActivo(baseDTO.getActivo());
		dto.setID(baseDTO.getID());
		dto.setISBN(bo.getISBN());
		dto.setEditorial(bo.getEditorial());
		dto.setNombre(baseDTO.getNombre());
		return dto;
	}

	public static BOLibro toBO(TLibro dto) {
		BOLibro bo = new BOLibro();
		BOMaterial baseBO = transferToEntity(dto);
		bo.setAutor(baseBO.getAutor());
		bo.setTipoMaterial(baseBO.getTipoMaterial());
		bo.setActivo(baseBO.getActivo());
		bo.setID(baseBO.getID());
		bo.setISBN(dto.getISBN());
		bo.setEditorial(dto.getEditorial());
		bo.setNombre(baseBO.getNombre());
		return bo;
	}
}
