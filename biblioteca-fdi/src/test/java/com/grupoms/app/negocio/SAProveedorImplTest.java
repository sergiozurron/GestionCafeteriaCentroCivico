package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.grupoms.app.negocio.proveedor.SAProveedor;
import com.grupoms.app.negocio.proveedor.SAProveedorImpl;
import com.grupoms.app.negocio.proveedor.TProveedor;

import org.junit.jupiter.api.Test;

/**
 * Tests unitarios para la lógica de negocio de SAProveedorImpl.
 * Estos tests verifican la lógica del SA sin acceso a base de datos,
 * comprobando el Transfer Object y la interfaz del SA.
 *
 * Los tests de integración con BD real no son posibles sin
 * configurar MySQL. Verificamos la lógica del Transfer y del contrato.
 */
public class SAProveedorImplTest {

    @Test
    void testTProveedor_creacionYGetters() {
        TProveedor p = new TProveedor();
        p.setId(1);
        p.setNombre("Proveedor Test");
        p.setTarifa(100.0);
        p.setTiempoEntrega(5);
        p.setActivo(true);

        assertEquals(1, p.getId());
        assertEquals("Proveedor Test", p.getNombre());
        assertEquals(100.0, p.getTarifa());
        assertEquals(5, p.getTiempoEntrega());
        assertTrue(p.getActivo());
    }

    @Test
    void testTProveedor_valoresIniciales() {
        TProveedor p = new TProveedor();
        assertNull(p.getId());
        assertNull(p.getNombre());
        assertNull(p.getTarifa());
        assertNull(p.getTiempoEntrega());
        assertNull(p.getActivo());
    }

    @Test
    void testSAProveedor_implementaInterfaz() {
        SAProveedorImpl sa = new SAProveedorImpl();
        assertInstanceOf(SAProveedor.class, sa);
    }

    @Test
    void testAltaProveedor_conNombreNull_noExplota() {
        SAProveedorImpl sa = new SAProveedorImpl();
        TProveedor p = new TProveedor();
        p.setNombre(null);
        p.setTarifa(10.0);
        p.setTiempoEntrega(1);
        // Aunque falle internamente por la BD, no debe lanzar excepción no controlada
        assertDoesNotThrow(() -> sa.altaProveedor(p));
    }

    @Test
    void testBajaProveedor_conIdNull() {
        SAProveedorImpl sa = new SAProveedorImpl();
        TProveedor p = new TProveedor();
        p.setId(null);
        // No debe explotar con NullPointerException sin control
        Boolean resultado = sa.bajaProveedor(p);
        assertFalse(resultado);
    }

    @Test
    void testMostrarProveedor_conIdInvalido() {
        SAProveedorImpl sa = new SAProveedorImpl();
        // Sin BD configurada, debe retornar null sin explotar
        TProveedor resultado = sa.mostrarProveedor(-999);
        assertNull(resultado);
    }

    @Test
    void testMostrarListaProveedores_sinBD() {
        SAProveedorImpl sa = new SAProveedorImpl();
        // Sin BD configurada, debe retornar lista vacía sin explotar
        List<TProveedor> lista = sa.mostrarListaProveedores();
        assertNotNull(lista);
    }

    @Test
    void testModificarProveedor_conIdNull() {
        SAProveedorImpl sa = new SAProveedorImpl();
        TProveedor p = new TProveedor();
        p.setId(null);
        p.setNombre("Modificado");
        p.setTarifa(50.0);
        p.setTiempoEntrega(3);
        // No debe explotar
        Boolean resultado = sa.modificarProveedor(p);
        assertFalse(resultado);
    }

    @Test
    void testTProveedor_modificarTarifa() {
        TProveedor p = new TProveedor();
        p.setTarifa(50.0);
        assertEquals(50.0, p.getTarifa());

        p.setTarifa(100.0);
        assertEquals(100.0, p.getTarifa());
    }

    @Test
    void testTProveedor_modificarTiempoEntrega() {
        TProveedor p = new TProveedor();
        p.setTiempoEntrega(3);
        assertEquals(3, p.getTiempoEntrega());

        p.setTiempoEntrega(10);
        assertEquals(10, p.getTiempoEntrega());
    }

    @Test
    void testTProveedor_toggleActivo() {
        TProveedor p = new TProveedor();
        p.setActivo(true);
        assertTrue(p.getActivo());

        p.setActivo(false);
        assertFalse(p.getActivo());

        p.setActivo(true);
        assertTrue(p.getActivo());
    }

    @Test
    void testTProveedor_nombreLargo() {
        TProveedor p = new TProveedor();
        String nombreLargo = "Proveedor con nombre muy largo para verificar que no hay límite artificial en el Transfer Object";
        p.setNombre(nombreLargo);
        assertEquals(nombreLargo, p.getNombre());
    }

    @Test
    void testTProveedor_tarifaCero() {
        TProveedor p = new TProveedor();
        p.setTarifa(0.0);
        assertEquals(0.0, p.getTarifa());
    }

    @Test
    void testTProveedor_tiempoEntregaCero() {
        TProveedor p = new TProveedor();
        p.setTiempoEntrega(0);
        assertEquals(0, p.getTiempoEntrega());
    }

    @Test
    void testTProveedor_idNegativo() {
        TProveedor p = new TProveedor();
        p.setId(-5);
        assertEquals(-5, p.getId());
    }

    @Test
    void testTProveedor_dosProveedoresDiferentes() {
        TProveedor p1 = new TProveedor();
        p1.setId(1);
        p1.setNombre("Proveedor 1");
        p1.setTarifa(50.0);

        TProveedor p2 = new TProveedor();
        p2.setId(2);
        p2.setNombre("Proveedor 2");
        p2.setTarifa(75.0);

        assertNotEquals(p1.getId(), p2.getId());
        assertNotEquals(p1.getNombre(), p2.getNombre());
        assertNotEquals(p1.getTarifa(), p2.getTarifa());
    }
}
