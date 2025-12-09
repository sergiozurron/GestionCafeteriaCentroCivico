package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.socioJPA.BOAdulto;
import com.grupoms.app.negocio.socioJPA.TAdulto;

public class AdultoAssembler extends SocioAssembler {

    public static TAdulto toDTO(BOAdulto bo) {
        TAdulto dto = new TAdulto();
        // Mapeo de campos comunes
        dto.setId(bo.getId());
        dto.setNombreYapellido(bo.getNombreYapellido());
        dto.setDni(bo.getDni());
        dto.setTipoSocio(bo.getTipoSocio());
        dto.setCuota(bo.getCuota());
        dto.setActivo(bo.getActivo());
        
        // Mapeo de campos específicos
        dto.setMiembroPleno(bo.getMiembroPleno());
        return dto;
    }

    public static BOAdulto toBO(TAdulto dto) {
        BOAdulto bo = new BOAdulto();
        // Mapeo de campos comunes
        bo.setId(dto.getId());
        bo.setNombreYapellido(dto.getNombreYapellido());
        bo.setDni(dto.getDni());
        bo.setTipoSocio(dto.getTipoSocio());
        bo.setCuota(dto.getCuota());
        bo.setActivo(dto.getActivo());
        
        // Mapeo de campos específicos
        bo.setMiembroPleno(dto.getMiembroPleno());
        return bo;
    }
}