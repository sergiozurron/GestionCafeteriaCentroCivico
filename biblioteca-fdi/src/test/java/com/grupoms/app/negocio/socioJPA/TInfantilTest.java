package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TInfantilTest {

    @Test
    void testConstructorConParametros() {
        TInfantil i = new TInfantil("Lucía Niña", "11111111A", 1, 20, 0.20, 10);
        assertEquals("Lucía Niña", i.getNombreYapellido());
        assertEquals("11111111A", i.getDni());
        assertEquals(1, i.getTipoSocio());
        assertEquals(20, i.getCuota());
        assertEquals(10, i.getEdad());
        assertTrue(i.getActivo());
    }

    @Test
    void testConstructorVacio() {
        TInfantil i = new TInfantil();
        assertEquals(0, i.getEdad()); // int primitivo
        assertNull(i.getId());
    }

    @Test
    void testReduccionPorEdad_MenorDe3() {
        TInfantil i = new TInfantil();
        i.setEdad(2);
        assertEquals(0.50, i.getReduccion());
    }

    @Test
    void testReduccionPorEdad_Igual3() {
        TInfantil i = new TInfantil();
        i.setEdad(3);
        assertEquals(0.50, i.getReduccion());
    }

    @Test
    void testReduccionPorEdad_Entre4y14() {
        TInfantil i = new TInfantil();
        i.setEdad(10);
        assertEquals(0.20, i.getReduccion());
    }

    @Test
    void testReduccionPorEdad_Igual14() {
        TInfantil i = new TInfantil();
        i.setEdad(14);
        assertEquals(0.20, i.getReduccion());
    }

    @Test
    void testReduccionPorEdad_Entre15y18() {
        TInfantil i = new TInfantil();
        i.setEdad(16);
        assertEquals(0.10, i.getReduccion());
    }

    @Test
    void testReduccionPorEdad_Igual18() {
        TInfantil i = new TInfantil();
        i.setEdad(18);
        assertEquals(0.10, i.getReduccion());
    }

    @Test
    void testReduccionPorEdad_Mayor18() {
        TInfantil i = new TInfantil();
        i.setEdad(19);
        assertEquals(0.0, i.getReduccion());
    }

    @Test
    void testReduccionPorEdad_Edad0() {
        TInfantil i = new TInfantil();
        i.setEdad(0);
        assertEquals(0.50, i.getReduccion());
    }

    @Test
    void testSetEdad() {
        TInfantil i = new TInfantil();
        i.setEdad(5);
        assertEquals(5, i.getEdad());
    }

    @Test
    void testSetReduccion() {
        TInfantil i = new TInfantil();
        i.setReduccion(0.75);
        // Nota: setReduccion pone un valor manual, pero getReduccion lo recalcula según edad
        // Si edad == 0 (default), getReduccion devolverá 0.50
        assertEquals(0.50, i.getReduccion());
    }

    @Test
    void testHerenciaDeTSocio() {
        TInfantil i = new TInfantil("Herencia", "22222222B", 1, 15, 0.20, 8);
        assertInstanceOf(TSocio.class, i);
    }

    @Test
    void testToString() {
        TInfantil i = new TInfantil("ToString Test", "33333333C", 1, 25, 0.20, 12);
        i.setId(3);
        String str = i.toString();
        assertTrue(str.contains("ToString Test"));
        assertTrue(str.contains("edad=12"));
        assertTrue(str.contains("3"));
    }

    @Test
    void testCambioEdadCambiaReduccion() {
        TInfantil i = new TInfantil();
        i.setEdad(2);
        assertEquals(0.50, i.getReduccion());

        i.setEdad(10);
        assertEquals(0.20, i.getReduccion());

        i.setEdad(16);
        assertEquals(0.10, i.getReduccion());

        i.setEdad(25);
        assertEquals(0.0, i.getReduccion());
    }
}

