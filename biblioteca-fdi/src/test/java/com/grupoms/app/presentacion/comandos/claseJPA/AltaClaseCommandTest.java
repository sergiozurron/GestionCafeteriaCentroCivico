package com.grupoms.app.presentacion.comandos.claseJPA;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.grupoms.app.negocio.ClaseJPA.TClase;
import com.grupoms.app.negocio.ClaseJPA.ClaseSA;
import com.grupoms.app.negocio.factoria.FactoriaSA;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Evento;

public class AltaClaseCommandTest {

    @AfterEach
    public void cleanup() {
        FactoriaSA.resetInstance();
    }

    @Test
    public void execute_whenAltaOk_returnsOkContext() {
        ClaseSA saMock = mock(ClaseSA.class);
        when(saMock.altaClase(org.mockito.ArgumentMatchers.any(TClase.class))).thenReturn(123);

        FactoriaSA factMock = mock(FactoriaSA.class);
        when(factMock.creaSAClase()).thenReturn(saMock);
        FactoriaSA.setInstance(factMock);

        AltaClaseCommand cmd = new AltaClaseCommand();
        TClase input = new TClase();
        input.setTipo("Zumba");

        Context ctx = cmd.execute(input);
        assertThat(ctx).isNotNull();
        assertThat(ctx.getEvento()).isEqualTo(Evento.ALTA_CLASE_OK);
        assertThat((Integer)ctx.getDatos()).isEqualTo(123);
    }

    @Test
    public void execute_whenAltaFails_returnsKoContext() {
        ClaseSA saMock = mock(ClaseSA.class);
        when(saMock.altaClase(org.mockito.ArgumentMatchers.any(TClase.class))).thenReturn(-1);

        FactoriaSA factMock = mock(FactoriaSA.class);
        when(factMock.creaSAClase()).thenReturn(saMock);
        FactoriaSA.setInstance(factMock);

        AltaClaseCommand cmd = new AltaClaseCommand();
        TClase input = new TClase();

        Context ctx = cmd.execute(input);
        assertThat(ctx.getEvento()).isEqualTo(Evento.ALTA_CLASE_KO);
    }
}
