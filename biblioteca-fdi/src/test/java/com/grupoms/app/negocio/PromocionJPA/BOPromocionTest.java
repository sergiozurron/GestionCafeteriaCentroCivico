package com.grupoms.app.negocio.PromocionJPA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.grupoms.app.negocio.socioJPA.BOSocio;

import org.junit.jupiter.api.Test;

public class BOPromocionTest {

    @Test
    void testConstructorVacio() {
        BOPromocion p = new BOPromocion();
        assertNull(p.getID());
        assertNull(p.getDescuento());
        assertNull(p.getTipo());
    }

    @Test
    void testConstructorConTPromocion() {
        TPromocion t = new TPromocion(25.0, "General");
        t.setId(5);
        t.setActivo(true);

        BOPromocion bo = new BOPromocion(t);
        assertEquals(5, bo.getID());
        assertEquals(25.0, bo.getDescuento());
        assertEquals("General", bo.getTipo());
        assertTrue(bo.getActivo());
    }

    @Test
    void testGettersSetters() {
        BOPromocion p = new BOPromocion();
        p.setID(10);
        p.setDescuento(30.0);
        p.setTipo("Navidad");
        p.setActivo(true);

        assertEquals(10, p.getID());
        assertEquals(30.0, p.getDescuento());
        assertEquals("Navidad", p.getTipo());
        assertTrue(p.getActivo());
    }

    @Test
    void testActivoToggle() {
        BOPromocion p = new BOPromocion();
        p.setActivo(true);
        assertTrue(p.getActivo());
        p.setActivo(false);
        assertFalse(p.getActivo());
    }

    @Test
    void testGetSocios() {
        BOPromocion p = new BOPromocion();
        // getSocios puede devolver null (no inicializado en BOPromocion)
        List<BOSocio> socios = p.getSocios();
        // Dependiendo de la inicialización JPA, puede ser null
        // En uso fuera de JPA (new), será null
        assertNull(socios);
    }

    @Test
    void testConstructorConTPromocion_SinId() {
        TPromocion t = new TPromocion(10.0, "SinId");
        BOPromocion bo = new BOPromocion(t);
        assertNull(bo.getID()); // TPromocion sin setId
        assertEquals(10.0, bo.getDescuento());
        assertEquals("SinId", bo.getTipo());
    }
}

