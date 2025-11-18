package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.PromocionJPA.TPromocion;

public class PromocionAssembler {
    public static TPromocion toDTO(BOPromocion bo) {
        TPromocion dto = new TPromocion();
        dto.setDescuento(bo.getDescuento());
        dto.setTipo(bo.getTipo());
        dto.setActivo(bo.getActivo());
        dto.setId(bo.getID());
        return dto;  // debe devolver TPromocion
    }

    public static BOPromocion toBO(TPromocion dto) {
        BOPromocion bo = new BOPromocion();
        bo.setDescuento(dto.getDescuento());
        bo.setTipo(dto.getTipo());
        bo.setActivo(dto.getActivo());
        bo.setID(dto.getId());
        return bo;  // debe devolver BOPromocion
    }
}
