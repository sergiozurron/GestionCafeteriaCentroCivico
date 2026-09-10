package com.grupoms.app.negocio.assembler;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.salaJPA.BOSala;

public class ClaseAssembler {

	public static TClase entityToTransfer(BOClase bo) {
		if (bo == null)
			return null;

		TClase dto = new TClase();
		dto.setId(bo.getId());
		dto.setTipo(bo.getTipo());
		dto.setFechaInicio(bo.getFechaInicio());
		dto.setDuracion(bo.getDuracion());
		dto.setActivo(bo.getActivo());

		if (bo.getSala() != null) {
			dto.setIdSala(bo.getSala().getId());
		}

		List<Integer> idsEjemplares = new ArrayList<>();

		if (bo.getEjemplares() != null) {

			for (BOEjemplar ejemplar : bo.getEjemplares()) {
				idsEjemplares.add(ejemplar.getId());
			}
		}

		dto.setEjemplares(idsEjemplares);

		return dto;
	}

	public static BOClase transferToEntity(TClase dto) {
		BOClase bo = new BOClase();
		bo.setId(dto.getId());
		bo.setTipo(dto.getTipo());
		bo.setFechaInicio(dto.getFechaInicio());
		bo.setDuracion(dto.getDuracion());
		bo.setActivo(dto.getActivo());

		if (dto.getIdSala() != null) {
			BOSala salaProxy = new BOSala();
			salaProxy.setId(dto.getIdSala());
			bo.setSala(salaProxy);
		}

		return bo;
	}
}