package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;

public class EjemplarAssembler {

	public static TEjemplar toTransferObject(BOEjemplar boEjemplar) {
		if (boEjemplar == null) {
			return null;
		}

		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(boEjemplar.getId());
		tEjemplar.setEstado(boEjemplar.getEstado());
		tEjemplar.setActivo(boEjemplar.getActivo());
		tEjemplar.setIdMaterial(boEjemplar.getMaterial().getID());

		return tEjemplar;
	}
	
}
