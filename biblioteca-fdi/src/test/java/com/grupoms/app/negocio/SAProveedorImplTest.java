package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.proveedor.DAOProveedorImpl;
import com.grupoms.app.negocio.proveedor.SAProveedorImpl;
import com.grupoms.app.negocio.proveedor.TProveedor;

public class SAProveedorImplTest {
	
	private final SAProveedorImpl saProveedor = new SAProveedorImpl();
	private final DAOProveedorImpl daoProveedor = new DAOProveedorImpl();
	
	@AfterEach
	void cleanUp() {
		daoProveedor.eliminaTodos();
	}

	@Test
    void testAltaProveedor_DeberiaCrearYDevolverId() {
        // GIVEN
        TProveedor proveedor = new TProveedor();
        proveedor.setNombre("Proveedor Nuevo");
        proveedor.setTarifa(8.0);
        proveedor.setTiempoEntrega(5);
        proveedor.setActivo(true);

        // WHEN
        int id = saProveedor.altaProveedor(proveedor);

        // THEN
        assertTrue(id > 0);
        TProveedor guardado = daoProveedor.buscaPorId(id);
        assertNotNull(guardado);
        assertTrue(guardado.getActivo());
    }

    @Test
    void testAltaProveedor_DeberiaDevolverMenosUno_CuandoProveedorExistenteActivo() {
        // GIVEN
        TProveedor existente = new TProveedor();
        existente.setNombre("Proveedor Activo");
        existente.setTarifa(10.0);
        existente.setTiempoEntrega(7);
        existente.setActivo(true);
        daoProveedor.crea(existente);

        TProveedor intento = new TProveedor();
        intento.setNombre("Proveedor Activo");

        // WHEN
        int resultado = saProveedor.altaProveedor(intento);

        // THEN
        assertEquals(-1, resultado);
    }

}
