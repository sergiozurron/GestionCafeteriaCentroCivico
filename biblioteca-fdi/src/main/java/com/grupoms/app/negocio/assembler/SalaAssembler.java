package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.salaJPA.BOSala;
import com.grupoms.app.negocio.salaJPA.TSala;

public class SalaAssembler {
	public static TSala entityToTransfer(BOSala bo) {
		TSala dto = new TSala();
		dto.setId(bo.getId());
		dto.setNombre(bo.getNombre());
		dto.setCapacidad(bo.getCapacidad());
		dto.setActivo(bo.getActivo());

		return dto;
	}

	protected static BOSala transferToEntity(TSala dto) {
		BOSala bo = new BOSala();
		bo.setId(dto.getId());
		bo.setNombre(dto.getNombre());
		bo.setCapacidad(dto.getCapacidad());
		bo.setActivo(dto.getActivo());

		return bo;
	}
}
