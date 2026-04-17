package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;
import com.grupoms.app.negocio.prestamoJPA.TPrestamo;

public class PrestamoAssembler {

	public static TPrestamo toDTO(BOPrestamo bo) {
		if (bo == null)
			return null;
		TPrestamo dto = new TPrestamo();
		dto.setId(bo.getId());

		if (bo.getSocio() != null) {
			dto.setIdSocio(bo.getSocio().getId());
		}
		if (bo.getEjemplar() != null) {
			dto.setIdEjemplar(bo.getEjemplar().getId());
		}

		dto.setFechaInicial(bo.getFechaInicial());
		dto.setFechaMaxima(bo.getFechaMaxima());
		dto.setFechaDevuelto(bo.getFechaDevuelto());
		dto.setPrecioMulta(bo.getPrecioMulta());
		return dto;
	}

}