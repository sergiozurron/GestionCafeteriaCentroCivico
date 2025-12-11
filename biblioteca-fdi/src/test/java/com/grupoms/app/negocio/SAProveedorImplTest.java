package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.proveedor.DAOProveedorImpl;
import com.grupoms.app.negocio.proveedor.SAProveedorImpl;
import com.grupoms.app.negocio.proveedor.TProveedor;

public class SAProveedorImplTest {
	
	static SAProveedorImpl saProveedor = new SAProveedorImpl();
	static DAOProveedorImpl daoProveedor = new DAOProveedorImpl();
	
	@BeforeAll
	static void setup() throws Exception {
		daoProveedor.eliminaTodos();
	}
	
	@AfterEach
	void cleanUp() throws Exception {
		daoProveedor.eliminaTodos();
	}

	@Test
    void testAltaProveedor_DeberiaCrearYDevolverId() throws Exception {
        
        TProveedor nuevo = new TProveedor();
        nuevo.setNombre("Proveedor Nuevo");
        nuevo.setTarifa(100.0);
        nuevo.setTiempoEntrega(5);
        nuevo.setActivo(true);

        
        int id = saProveedor.altaProveedor(nuevo);

        
        assertTrue(id > 0);
        TProveedor guardado = daoProveedor.buscaPorId(id);
        assertNotNull(guardado);
        assertTrue(guardado.getActivo());
    }

    @Test
    void testAltaProveedor_DeberiaDevolverMenosUno_ProveedorExistenteActivo() {
        
        TProveedor activo = new TProveedor();
        activo.setNombre("Proveedor Activo");
        activo.setTarifa(150.0);
        activo.setTiempoEntrega(3);
        activo.setActivo(true);
        daoProveedor.crea(activo);

        
        TProveedor intento = new TProveedor();
        intento.setNombre("Proveedor Activo");
        intento.setTarifa(180.0);
        intento.setTiempoEntrega(4);
        int resultado = saProveedor.altaProveedor(intento);

        
        assertEquals(-1, resultado);

        TProveedor persistido = daoProveedor.buscaPorNombre("Proveedor Activo");
        assertNotNull(persistido);
        assertTrue(persistido.getActivo());
    }

    @Test
    void testAltaProveedor_ProveedorExistenteInactivo_DeberiaReactivarse() {
        
        TProveedor inactivo = new TProveedor();
        inactivo.setNombre("Proveedor Inactivo");
        inactivo.setTarifa(200.0);
        inactivo.setTiempoEntrega(7);
        inactivo.setActivo(false);
        daoProveedor.crea(inactivo);

        
        TProveedor intento = new TProveedor();
        intento.setNombre("Proveedor Inactivo");
        intento.setTarifa(220.0);
        intento.setTiempoEntrega(6);
        int id = saProveedor.altaProveedor(intento);

        
        assertEquals(inactivo.getId(), id);
        TProveedor reactivado = daoProveedor.buscaPorNombre("Proveedor Inactivo");
        assertNotNull(reactivado);
        assertTrue(reactivado.getActivo());
    }
    
}
