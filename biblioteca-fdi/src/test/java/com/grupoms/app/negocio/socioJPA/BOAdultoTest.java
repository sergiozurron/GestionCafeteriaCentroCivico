package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BOAdultoTest {

    @Test
    void testConstructorVacio() {
        BOAdulto a = new BOAdulto();
        assertNull(a.getMiembroPleno());
        assertNull(a.getId());
        assertNull(a.getNombreYapellido());
    }

    @Test
    void testConstructorConTAdulto() {
        TAdulto t = new TAdulto("Juan Adulto", "12345678A", 0, 50, true);
        BOAdulto bo = new BOAdulto(t);

        assertEquals("Juan Adulto", bo.getNombreYapellido());
        assertEquals("12345678A", bo.getDni());
        assertEquals(0, bo.getTipoSocio());
        assertEquals(50, bo.getCuota());
        assertTrue(bo.getMiembroPleno());
    }

    @Test
    void testConstructorConMiembroPlenoFalse() {
        TAdulto t = new TAdulto("No Pleno", "22222222B", 0, 30, false);
        BOAdulto bo = new BOAdulto(t);
        assertFalse(bo.getMiembroPleno());
    }

    @Test
    void testSetMiembroPleno() {
        BOAdulto a = new BOAdulto();
        a.setMiembroPleno(true);
        assertTrue(a.getMiembroPleno());
        a.setMiembroPleno(false);
        assertFalse(a.getMiembroPleno());
    }

    @Test
    void testHerenciaBoSocio() {
        BOAdulto a = new BOAdulto();
        assertInstanceOf(BOSocio.class, a);
    }

    @Test
    void testSettersCamposHeredados() {
        BOAdulto a = new BOAdulto();
        a.setId(10);
        a.setNombreYapellido("Herencia Test");
        a.setDni("33333333C");
        a.setTipoSocio(0);
        a.setCuota(45);
        a.setActivo(true);
        a.setMiembroPleno(true);

        assertEquals(10, a.getId());
        assertEquals("Herencia Test", a.getNombreYapellido());
        assertEquals("33333333C", a.getDni());
        assertEquals(0, a.getTipoSocio());
        assertEquals(45, a.getCuota());
        assertTrue(a.getActivo());
        assertTrue(a.getMiembroPleno());
    }

    @Test
    void testPromocionesHeredadas() {
        BOAdulto a = new BOAdulto();
        assertNotNull(a.getPromociones());
        assertTrue(a.getPromociones().isEmpty());
    }

    @Test
    void testPrestamosHeredados() {
        BOAdulto a = new BOAdulto();
        assertNotNull(a.getPrestamos());
        assertTrue(a.getPrestamos().isEmpty());
    }
}

