package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.socioJPA.BOSocio;
import com.grupoms.app.negocio.socioJPA.TSocio;

public class SocioAssembler {

    // Convierte campos comunes BO → DTO
    public static TSocio entityToTransfer(BOSocio bo) {
        TSocio dto = new TSocio();
        dto.setId(bo.getId());
        dto.setNombreYapellido(bo.getNombreYapellido());
        dto.setDni(bo.getDni());
        dto.setTipoSocio(bo.getTipoSocio());
        dto.setCuota(bo.getCuota());
        dto.setActivo(bo.getActivo());
        return dto;  // debe devolver TSocio
    }


    // Convierte campos comunes DTO → BO
    public static BOSocio transferToEntity(TSocio dto) {
        BOSocio bo = new BOSocio();
        bo.setID(dto.getId());
        bo.setNombreYapellido(dto.getNombreYapellido());
        bo.setDni(dto.getDni());
        bo.setTipoSocio(dto.getTipoSocio());
        bo.setCuota(dto.getCuota());
        bo.setActivo(dto.getActivo());
        return bo;
    }
    
}
