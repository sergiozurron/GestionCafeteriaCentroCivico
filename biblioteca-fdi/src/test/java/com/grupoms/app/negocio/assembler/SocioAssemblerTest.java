package com.grupoms.app.negocio.assembler;

import static org.junit.jupiter.api.Assertions.*;

import com.grupoms.app.negocio.socioJPA.BOAdulto;
import com.grupoms.app.negocio.socioJPA.TAdulto;

import org.junit.jupiter.api.Test;

public class SocioAssemblerTest {

    @Test
    public void adultoEntityToDTO() {
        TAdulto t = new TAdulto("Ana Gomez", "11111111Z", 0, 30, true);
        BOAdulto bo = new BOAdulto(t);
        
        com.grupoms.app.negocio.socioJPA.TSocio dto = com.grupoms.app.negocio.assembler.AdultoAssembler.toDTO(bo);
        assertNotNull(dto);
        assertEquals("Ana Gomez", dto.getNombreYapellido());
    }
}
