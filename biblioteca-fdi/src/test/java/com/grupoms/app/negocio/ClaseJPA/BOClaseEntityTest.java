package com.grupoms.app.negocio.ClaseJPA;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;

public class BOClaseEntityTest {

    @Test
    public void anyadirYEliminarEjemplar_noNPE_andListUpdated() {
        BOClase clase = new BOClase();
        BOEjemplar ej = new BOEjemplar();
        ej.setId(10);

        // Add
        clase.anyadirEjemplar(ej);
        assertThat(clase).isNotNull();
        assertThat(clase.getId()).isNull();
        assertThat(clase).extracting("ejemplares").asList().isNotEmpty();

        // Remove
        clase.eliminarEjemplar(ej);
        assertThat(clase).extracting("ejemplares").asList().isEmpty();
    }
}

