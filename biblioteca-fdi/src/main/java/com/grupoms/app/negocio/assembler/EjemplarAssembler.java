package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.materialJPA.MaterialSAImp;

public class EjemplarAssembler {
	private static MaterialSAImp matImp;

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

	public static BOEjemplar transferToEntity(TEjemplar tEjemplar) {
		BOEjemplar bo = new BOEjemplar();

		bo.setId(tEjemplar.getId());
		bo.setEstado(tEjemplar.getEstado());
		bo.setActivo(tEjemplar.getActivo());
		bo.setMaterial(MaterialAssembler.transferToEntity(matImp.mostrarMaterial(tEjemplar.getIdMaterial())));

		return bo;
	}

}
