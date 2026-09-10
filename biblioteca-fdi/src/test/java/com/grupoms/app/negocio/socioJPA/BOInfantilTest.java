package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BOInfantilTest {

    @Test
    void testConstructorVacio() {
        BOInfantil i = new BOInfantil();
        assertNull(i.getReduccion());
        assertEquals(0, i.getEdad());
        assertNull(i.getId());
    }

    @Test
    void testConstructorConTInfantil() {
        TInfantil t = new TInfantil("Lucía Niña", "11111111A", 1, 20, 0.20, 10);
        BOInfantil bo = new BOInfantil(t);

        assertEquals("Lucía Niña", bo.getNombreYapellido());
        assertEquals("11111111A", bo.getDni());
        assertEquals(1, bo.getTipoSocio());
        assertEquals(20, bo.getCuota());
        assertEquals(10, bo.getEdad());
        assertNotNull(bo.getReduccion());
    }

    @Test
    void testSetEdad() {
        BOInfantil i = new BOInfantil();
        i.setEdad(5);
        assertEquals(5, i.getEdad());
    }

    @Test
    void testSetReduccion() {
        BOInfantil i = new BOInfantil();
        i.setReduccion(0.30);
        assertEquals(0.30, i.getReduccion());
    }

    @Test
    void testHerenciaBoSocio() {
        BOInfantil i = new BOInfantil();
        assertInstanceOf(BOSocio.class, i);
    }

    @Test
    void testSettersCamposHeredados() {
        BOInfantil i = new BOInfantil();
        i.setId(5);
        i.setNombreYapellido("Infantil Test");
        i.setDni("22222222B");
        i.setTipoSocio(1);
        i.setCuota(15);
        i.setActivo(true);
        i.setEdad(7);
        i.setReduccion(0.20);

        assertEquals(5, i.getId());
        assertEquals("Infantil Test", i.getNombreYapellido());
        assertEquals("22222222B", i.getDni());
        assertEquals(1, i.getTipoSocio());
        assertEquals(15, i.getCuota());
        assertTrue(i.getActivo());
        assertEquals(7, i.getEdad());
        assertEquals(0.20, i.getReduccion());
    }

    @Test
    void testPromocionesHeredadas() {
        BOInfantil i = new BOInfantil();
        assertNotNull(i.getPromociones());
        assertTrue(i.getPromociones().isEmpty());
    }

    @Test
    void testPrestamosHeredados() {
        BOInfantil i = new BOInfantil();
        assertNotNull(i.getPrestamos());
        assertTrue(i.getPrestamos().isEmpty());
    }

    @Test
    void testReduccionSeCalculaEnTInfantil_NoEnBO() {
        // BOInfantil almacena reducción directamente (viene de BD)
        // TInfantil la calcula según edad
        BOInfantil bo = new BOInfantil();
        bo.setEdad(10);
        bo.setReduccion(0.20);
        // BOInfantil.getReduccion() devuelve el valor almacenado, no lo recalcula
        assertEquals(0.20, bo.getReduccion());

        // TInfantil sí recalcula al llamar getReduccion()
        TInfantil t = new TInfantil();
        t.setEdad(10);
        assertEquals(0.20, t.getReduccion());
    }
}

