package com.grupoms.app.negocio.ClaseJPA;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.grupoms.app.negocio.assembler.ClaseAssembler;

public class ClaseAssemblerTest {

    @Test
    public void entityToTransfer_shouldMapFields() {
        BOClase bo = new BOClase();
        bo.setId(1);
        bo.setTipo("Yoga");
        Date now = new Date();
        bo.setFechaInicio(now);
        bo.setDuracion(60);
        bo.setActivo(true);

        TClase t = ClaseAssembler.entityToTransfer(bo);

        assertThat(t).isNotNull();
        assertThat(t.getId()).isEqualTo(1);
        assertThat(t.getTipo()).isEqualTo("Yoga");
        assertThat(t.getFechaInicio()).isEqualTo(now);
        assertThat(t.getDuracion()).isEqualTo(60);
        assertThat(t.getActivo()).isTrue();
    }
}
