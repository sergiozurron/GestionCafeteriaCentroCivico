package com.grupoms.app.negocio.proveedor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TProveedorTest {

    @Test
    void testGettersSetters() {
        TProveedor p = new TProveedor();
        p.setId(1);
        p.setNombre("Proveedor Test");
        p.setTarifa(99.5);
        p.setTiempoEntrega(7);
        p.setActivo(true);

        assertEquals(1, p.getId());
        assertEquals("Proveedor Test", p.getNombre());
        assertEquals(99.5, p.getTarifa());
        assertEquals(7, p.getTiempoEntrega());
        assertTrue(p.getActivo());
    }

    @Test
    void testValoresIniciales() {
        TProveedor p = new TProveedor();
        assertNull(p.getId());
        assertNull(p.getNombre());
        assertNull(p.getTarifa());
        assertNull(p.getTiempoEntrega());
        assertNull(p.getActivo());
    }

    @Test
    void testSetActivo_FalseYTrue() {
        TProveedor p = new TProveedor();
        p.setActivo(false);
        assertFalse(p.getActivo());

        p.setActivo(true);
        assertTrue(p.getActivo());
    }

    @Test
    void testModificacionDeCampos() {
        TProveedor p = new TProveedor();
        p.setNombre("Original");
        p.setTarifa(10.0);
        p.setTiempoEntrega(3);

        p.setNombre("Modificado");
        p.setTarifa(20.0);
        p.setTiempoEntrega(5);

        assertEquals("Modificado", p.getNombre());
        assertEquals(20.0, p.getTarifa());
        assertEquals(5, p.getTiempoEntrega());
    }
}

