package com.grupoms.app.negocio.assembler;

import com.grupoms.app.negocio.socioJPA.BOInfantil;
import com.grupoms.app.negocio.socioJPA.TInfantil;

public class InfantilAssembler extends SocioAssembler {

    public static TInfantil toDTO(BOInfantil bo) {
        TInfantil dto = new TInfantil();
        // Mapeo de campos comunes
        dto.setId(bo.getId());
        dto.setNombreYapellido(bo.getNombreYapellido());
        dto.setDni(bo.getDni());
        dto.setTipoSocio(bo.getTipoSocio());
        dto.setCuota(bo.getCuota());
        dto.setActivo(bo.getActivo());
        
        // Mapeo de campos específicos
        dto.setEdad(bo.getEdad());
        dto.setReduccion(bo.getReduccion());
        return dto;
    }

    public static BOInfantil toBO(TInfantil dto) {
        BOInfantil bo = new BOInfantil();
        // Mapeo de campos comunes
        bo.setId(dto.getId());
        bo.setNombreYapellido(dto.getNombreYapellido());
        bo.setDni(dto.getDni());
        bo.setTipoSocio(dto.getTipoSocio());
        bo.setCuota(dto.getCuota());
        bo.setActivo(dto.getActivo());
        
        // Mapeo de campos específicos
        bo.setEdad(dto.getEdad());
        bo.setReduccion(dto.getReduccion());
        return bo;
    }
}