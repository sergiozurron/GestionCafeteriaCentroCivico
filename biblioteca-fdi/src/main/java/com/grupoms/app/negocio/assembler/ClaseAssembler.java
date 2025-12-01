package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.ClaseJPA.TClase;

/**
 * Assembler for Clase.
 * 1) Converts BOClase (entity) to TClase (transfer).
 * 2) Converts TClase (transfer) to BOClase (entity).
 */
public class ClaseAssembler {

    // ---- 1) Entity -> Transfer ----

    public static TClase entityToTransfer(BOClase bo) {
        TClase dto = new TClase();
        dto.setId(bo.getId());
        dto.setTipo(bo.getTipo());
        dto.setFechaInicio(bo.getFechaInicio());
        dto.setDuracion(bo.getDuracion());
        dto.setActivo(bo.getActivo());
        return dto;
    }

    // ---- 2) Transfer -> Entity ----

    protected static BOClase transferToEntity(TClase dto) {
        BOClase bo = new BOClase();
        bo.setId(dto.getId());
        bo.setTipo(dto.getTipo());
        bo.setFechaInicio(dto.getFechaInicio());
        bo.setDuracion(dto.getDuracion());
        bo.setActivo(dto.getActivo());
        return bo;
    }
}
