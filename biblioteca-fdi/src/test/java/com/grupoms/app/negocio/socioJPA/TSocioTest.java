package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TSocioTest {

    @Test
    void testConstructorConParametros() {
        TSocio s = new TSocio("Ana García", "12345678A", 0, 50);
        assertEquals("Ana García", s.getNombreYapellido());
        assertEquals("12345678A", s.getDni());
        assertEquals(0, s.getTipoSocio());
        assertEquals(50, s.getCuota());
        assertTrue(s.getActivo()); // constructor pone activo = true
        assertNull(s.getId());
    }

    @Test
    void testConstructorVacio() {
        TSocio s = new TSocio();
        assertNull(s.getId());
        assertNull(s.getNombreYapellido());
        assertNull(s.getDni());
        assertEquals(0, s.getTipoSocio()); // int primitivo, default 0
        assertNull(s.getCuota());
        assertNull(s.getActivo());
    }

    @Test
    void testGettersSetters() {
        TSocio s = new TSocio();
        s.setId(10);
        s.setNombreYapellido("Pedro López");
        s.setDni("87654321B");
        s.setTipoSocio(1);
        s.setCuota(30);
        s.setActivo(true);

        assertEquals(10, s.getId());
        assertEquals("Pedro López", s.getNombreYapellido());
        assertEquals("87654321B", s.getDni());
        assertEquals(1, s.getTipoSocio());
        assertEquals(30, s.getCuota());
        assertTrue(s.getActivo());
    }

    @Test
    void testSetActivo() {
        TSocio s = new TSocio("Test", "11111111A", 0, 20);
        assertTrue(s.getActivo());
        s.setActivo(false);
        assertFalse(s.getActivo());
    }

    @Test
    void testToString() {
        TSocio s = new TSocio("Juan Test", "99999999Z", 0, 40);
        s.setId(5);
        String str = s.toString();
        assertTrue(str.contains("Juan Test"));
        assertTrue(str.contains("99999999Z"));
        assertTrue(str.contains("5"));
    }

    @Test
    void testModificacionDeCampos() {
        TSocio s = new TSocio("Original", "00000000A", 0, 10);
        s.setNombreYapellido("Modificado");
        s.setCuota(99);
        s.setTipoSocio(1);
        assertEquals("Modificado", s.getNombreYapellido());
        assertEquals(99, s.getCuota());
        assertEquals(1, s.getTipoSocio());
    }
}

