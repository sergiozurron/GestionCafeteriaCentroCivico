package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TAdultoTest {

    @Test
    void testConstructorConParametros() {
        TAdulto a = new TAdulto("Carlos Ruiz", "11111111A", 0, 50, true);
        assertEquals("Carlos Ruiz", a.getNombreYapellido());
        assertEquals("11111111A", a.getDni());
        assertEquals(0, a.getTipoSocio());
        assertEquals(50, a.getCuota());
        assertTrue(a.getActivo());
        assertTrue(a.getMiembroPleno());
    }

    @Test
    void testConstructorVacio() {
        TAdulto a = new TAdulto();
        assertNull(a.getMiembroPleno());
        assertNull(a.getId());
    }

    @Test
    void testMiembroPlenoFalse() {
        TAdulto a = new TAdulto("Ana Test", "22222222B", 0, 30, false);
        assertFalse(a.getMiembroPleno());
    }

    @Test
    void testSetMiembroPleno() {
        TAdulto a = new TAdulto();
        a.setMiembroPleno(true);
        assertTrue(a.getMiembroPleno());
        a.setMiembroPleno(false);
        assertFalse(a.getMiembroPleno());
    }

    @Test
    void testHerenciaDeTSocio() {
        TAdulto a = new TAdulto("Herencia Test", "33333333C", 0, 40, true);
        // Verificar que hereda correctamente de TSocio
        assertInstanceOf(TSocio.class, a);
        assertEquals("Herencia Test", a.getNombreYapellido());
    }

    @Test
    void testToString() {
        TAdulto a = new TAdulto("ToString Test", "44444444D", 0, 60, true);
        a.setId(7);
        String str = a.toString();
        assertTrue(str.contains("ToString Test"));
        assertTrue(str.contains("miembroPleno=true"));
        assertTrue(str.contains("7"));
    }

    @Test
    void testToStringMiembroPlenoFalse() {
        TAdulto a = new TAdulto("No Pleno", "55555555E", 0, 25, false);
        String str = a.toString();
        assertTrue(str.contains("miembroPleno=false"));
    }
}

