package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;

import org.junit.jupiter.api.Test;

public class BOSocioTest {

    @Test
    public void testConstructorVacio() {
        BOSocio s = new BOSocio();
        assertNull(s.getId());
        assertNull(s.getNombreYapellido());
        assertNull(s.getDni());
        assertNull(s.getCuota());
        assertNull(s.getActivo());
    }

    @Test
    public void testConstructorConTSocio() {
        TSocio t = new TSocio("Juan Test", "12345678A", 0, 50);
        t.setActivo(true);
        BOSocio bo = new BOSocio(t);

        assertEquals("Juan Test", bo.getNombreYapellido());
        assertEquals("12345678A", bo.getDni());
        assertEquals(0, bo.getTipoSocio());
        assertEquals(50, bo.getCuota());
        assertTrue(bo.getActivo());
    }

    @Test
    public void testGettersSetters() {
        BOSocio s = new BOSocio();
        s.setId(1);
        s.setNombreYapellido("Ana García");
        s.setDni("87654321B");
        s.setTipoSocio(1);
        s.setCuota(30);
        s.setActivo(true);

        assertEquals(1, s.getId());
        assertEquals("Ana García", s.getNombreYapellido());
        assertEquals("87654321B", s.getDni());
        assertEquals(1, s.getTipoSocio());
        assertEquals(30, s.getCuota());
        assertTrue(s.getActivo());
    }

    @Test
    public void testAnyadirPromocion() {
        BOSocio s = new BOSocio();
        BOPromocion p = new BOPromocion();
        p.setID(1);

        s.anyadirPromocion(p);
        List<BOPromocion> promos = s.getPromociones();
        assertNotNull(promos);
        assertEquals(1, promos.size());
        assertEquals(1, promos.get(0).getID());
    }

    @Test
    public void testEliminarPromocion() {
        BOSocio s = new BOSocio();
        BOPromocion p = new BOPromocion();
        p.setID(1);

        s.anyadirPromocion(p);
        assertEquals(1, s.getPromociones().size());

        s.eliminarPromocion(p);
        assertEquals(0, s.getPromociones().size());
    }

    @Test
    public void testAnyadirMultiplesPromociones() {
        BOSocio s = new BOSocio();
        BOPromocion p1 = new BOPromocion();
        p1.setID(1);
        BOPromocion p2 = new BOPromocion();
        p2.setID(2);
        BOPromocion p3 = new BOPromocion();
        p3.setID(3);

        s.anyadirPromocion(p1);
        s.anyadirPromocion(p2);
        s.anyadirPromocion(p3);

        assertEquals(3, s.getPromociones().size());
    }

    @Test
    public void testEliminarPromocionInexistente_NoFalla() {
        BOSocio s = new BOSocio();
        BOPromocion p1 = new BOPromocion();
        p1.setID(1);
        BOPromocion p2 = new BOPromocion();
        p2.setID(2);

        s.anyadirPromocion(p1);
        s.eliminarPromocion(p2); // p2 no está en la lista
        assertEquals(1, s.getPromociones().size());
    }

    @Test
    public void testEliminarPromocionConListaNull_NoFalla() {
        BOSocio s = new BOSocio();
        BOPromocion p = new BOPromocion();
        p.setID(1);
        // No se ha añadido ninguna promoción, la lista interna es null
        assertDoesNotThrow(() -> s.eliminarPromocion(p));
    }

    @Test
    public void testGetPromociones_InicializaListaVacia() {
        BOSocio s = new BOSocio();
        List<BOPromocion> promos = s.getPromociones();
        assertNotNull(promos);
        assertTrue(promos.isEmpty());
    }

    @Test
    public void testSetPromociones() {
        BOSocio s = new BOSocio();
        List<BOPromocion> lista = new ArrayList<>();
        BOPromocion p = new BOPromocion();
        p.setID(5);
        lista.add(p);

        s.setPromociones(lista);
        assertEquals(1, s.getPromociones().size());
        assertEquals(5, s.getPromociones().get(0).getID());
    }

    @Test
    public void testGetPrestamos_InicializaListaVacia() {
        BOSocio s = new BOSocio();
        List<BOPrestamo> prestamos = s.getPrestamos();
        assertNotNull(prestamos);
        assertTrue(prestamos.isEmpty());
    }

    @Test
    public void testSetPrestamos() {
        BOSocio s = new BOSocio();
        List<BOPrestamo> lista = new ArrayList<>();
        lista.add(new BOPrestamo());
        s.setPrestamos(lista);
        assertEquals(1, s.getPrestamos().size());
    }

    @Test
    public void testActivoToggle() {
        BOSocio s = new BOSocio();
        s.setActivo(true);
        assertTrue(s.getActivo());
        s.setActivo(false);
        assertFalse(s.getActivo());
    }

    @Test
    public void testAnyadirYEliminarPromocionCiclo() {
        BOSocio s = new BOSocio();
        BOPromocion p = new BOPromocion();
        p.setID(1);

        s.anyadirPromocion(p);
        List<BOPromocion> promos = s.getPromociones();
        assertNotNull(promos);
        assertEquals(1, promos.size());

        s.eliminarPromocion(p);
        assertEquals(0, s.getPromociones().size());
    }
}
