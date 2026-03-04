package com.grupoms.app.negocio.PromocionJPA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TPromocionTest {

    @Test
    void testConstructorConParametros() {
        TPromocion p = new TPromocion(15.0, "Verano");
        assertEquals(15.0, p.getDescuento());
        assertEquals("Verano", p.getTipo());
        assertNull(p.getId());
    }

    @Test
    void testConstructorVacio() {
        TPromocion p = new TPromocion();
        assertNull(p.getId());
        assertNull(p.getDescuento());
        assertNull(p.getTipo());
        assertFalse(p.getActivo()); // boolean primitivo, default false
    }

    @Test
    void testGettersSetters() {
        TPromocion p = new TPromocion();
        p.setId(1);
        p.setDescuento(20.0);
        p.setTipo("Invierno");
        p.setActivo(true);

        assertEquals(1, p.getId());
        assertEquals(20.0, p.getDescuento());
        assertEquals("Invierno", p.getTipo());
        assertTrue(p.getActivo());
    }

    @Test
    void testActivoToggle() {
        TPromocion p = new TPromocion();
        p.setActivo(true);
        assertTrue(p.getActivo());
        p.setActivo(false);
        assertFalse(p.getActivo());
    }
}

