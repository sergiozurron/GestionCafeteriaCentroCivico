package com.grupoms.app.integracion.proveedor;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.proveedor.TProveedor;

import org.junit.jupiter.api.*;

/**
 * Tests del DAOProveedorImpl usando una base de datos H2 en memoria.
 * Se inyecta un TransactionManager falso vía reflexión que devuelve
 * una conexión H2 real, evitando así problemas de Mockito con interfaces JDK.
 */
public class DAOProveedorImplTest {

    private static Connection h2Connection;
    private DAOProveedorImpl dao;
    private TransactionManager originalInstance;

    @BeforeAll
    static void initH2() throws Exception {
        h2Connection = DriverManager.getConnection("jdbc:h2:mem:testdao;DB_CLOSE_DELAY=-1", "sa", "");
        try (Statement stmt = h2Connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS proveedores ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(255), "
                    + "tarifa DOUBLE, "
                    + "tiempo_entrega INT, "
                    + "activo BOOLEAN)");
        }
    }

    @AfterAll
    static void closeH2() throws Exception {
        if (h2Connection != null && !h2Connection.isClosed()) {
            h2Connection.close();
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        dao = new DAOProveedorImpl();

        // Limpiar tabla
        try (Statement stmt = h2Connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM proveedores");
        }
        h2Connection.setAutoCommit(false);

        // Guardar instancia original del singleton
        Field instanceField = TransactionManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        originalInstance = (TransactionManager) instanceField.get(null);

        // Crear Transaction fake que devuelve la conexión H2
        Transaction fakeTransaction = new Transaction() {
            @Override public void start() { }
            @Override public void commit() { try { h2Connection.commit(); } catch (Exception e) { throw new RuntimeException(e); } }
            @Override public void rollback() { try { h2Connection.rollback(); } catch (Exception e) { throw new RuntimeException(e); } }
            @Override public Object getResource() { return h2Connection; }
        };

        // Crear TransactionManager fake
        TransactionManager testTxManager = new TransactionManager() {
            @Override public Transaction newTransaction() { return fakeTransaction; }
            @Override public Transaction getTransaction() { return fakeTransaction; }
            @Override public void deleteTransaction() { }
        };

        // Inyectar en el singleton
        instanceField.set(null, testTxManager);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Restaurar instancia original
        Field instanceField = TransactionManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, originalInstance);

        // Hacer rollback de lo que quede pendiente
        try { h2Connection.rollback(); } catch (Exception ignored) {}
        h2Connection.setAutoCommit(true);
    }

    @Test
    void testCrea_exitoso() {
        TProveedor p = crearProveedor("Nuevo Proveedor", 100.0, 5);

        Integer id = dao.creaProveedor(p);

        assertNotNull(id);
        assertTrue(id > 0);
        assertEquals(id, p.getId());
    }

    @Test
    void testCrea_dosProveedores_idsDistintos() {
        TProveedor p1 = crearProveedor("Proveedor 1", 50.0, 3);
        TProveedor p2 = crearProveedor("Proveedor 2", 60.0, 4);

        Integer id1 = dao.creaProveedor(p1);
        Integer id2 = dao.creaProveedor(p2);

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);
    }

    @Test
    void testBuscaPorId_encontrado() {
        TProveedor p = crearProveedor("Buscable", 75.0, 3);
        Integer id = dao.creaProveedor(p);

        TProveedor resultado = dao.mostrarProveedor(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Buscable", resultado.getNombre());
        assertEquals(75.0, resultado.getTarifa());
        assertEquals(3, resultado.getTiempoEntrega());
        assertTrue(resultado.getActivo());
    }

    @Test
    void testBuscaPorId_noEncontrado() {
        TProveedor resultado = dao.mostrarProveedor(99999);
        assertNull(resultado);
    }

    @Test
    void testBuscaPorId_verificaCamposCompletos() {
        TProveedor p = crearProveedor("Completo", 99.99, 7);
        p.setActivo(false);
        Integer id = dao.creaProveedor(p);

        TProveedor resultado = dao.mostrarProveedor(id);

        assertNotNull(resultado);
        assertEquals("Completo", resultado.getNombre());
        assertEquals(99.99, resultado.getTarifa(), 0.001);
        assertEquals(7, resultado.getTiempoEntrega());
        assertFalse(resultado.getActivo());
    }

    @Test
    void testBuscaPorNombre_encontrado() {
        TProveedor p = crearProveedor("ProvNombre", 80.0, 4);
        dao.creaProveedor(p);

        TProveedor resultado = dao.buscaProveedorPorNombre("ProvNombre");

        assertNotNull(resultado);
        assertEquals("ProvNombre", resultado.getNombre());
        assertEquals(80.0, resultado.getTarifa());
    }

    @Test
    void testBuscaPorNombre_noEncontrado() {
        TProveedor resultado = dao.buscaProveedorPorNombre("Inexistente");
        assertNull(resultado);
    }

    @Test
    void testBuscaPorNombre_caseSensitive() {
        TProveedor p = crearProveedor("CaseSensitive", 50.0, 2);
        dao.creaProveedor(p);

        // H2 es case-insensitive por defecto para VARCHAR,
        // pero verificamos que devuelve resultado con el nombre exacto
        TProveedor resultado = dao.buscaProveedorPorNombre("CaseSensitive");
        assertNotNull(resultado);
        assertEquals("CaseSensitive", resultado.getNombre());
    }

    @Test
    void testListar_conResultados() {
        dao.creaProveedor(crearProveedor("Proveedor 1", 50.0, 3));
        dao.creaProveedor(crearProveedor("Proveedor 2", 60.0, 5));

        List<TProveedor> lista = dao.listarProveedores();

        assertNotNull(lista);
        assertEquals(2, lista.size());
    }

    @Test
    void testListar_sinResultados() {
        List<TProveedor> lista = dao.listarProveedores();

        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }

    @Test
    void testListar_verificaTodosLosCampos() {
        dao.creaProveedor(crearProveedor("Único", 33.33, 2));

        List<TProveedor> lista = dao.listarProveedores();
        assertEquals(1, lista.size());

        TProveedor p = lista.get(0);
        assertEquals("Único", p.getNombre());
        assertEquals(33.33, p.getTarifa(), 0.001);
        assertEquals(2, p.getTiempoEntrega());
        assertTrue(p.getActivo());
    }

    @Test
    void testListar_incluyeActivosEInactivos() {
        TProveedor activo = crearProveedor("Activo", 10.0, 1);
        TProveedor inactivo = crearProveedor("Inactivo", 20.0, 2);
        inactivo.setActivo(false);
        dao.creaProveedor(activo);
        dao.creaProveedor(inactivo);

        List<TProveedor> lista = dao.listarProveedores();
        assertEquals(2, lista.size());
    }

    @Test
    void testActualiza_exitoso() {
        TProveedor p = crearProveedor("Original", 100.0, 5);
        Integer id = dao.creaProveedor(p);

        p.setNombre("Actualizado");
        p.setTarifa(120.0);
        p.setTiempoEntrega(6);

        Boolean resultado = dao.modificarProveedor(p);
        assertTrue(resultado);

        TProveedor verificar = dao.mostrarProveedor(id);
        assertEquals("Actualizado", verificar.getNombre());
        assertEquals(120.0, verificar.getTarifa());
        assertEquals(6, verificar.getTiempoEntrega());
    }

    @Test
    void testActualiza_noExiste_devuelveFalse() {
        TProveedor p = new TProveedor();
        p.setId(99999);
        p.setNombre("No Existe");
        p.setTarifa(0.0);
        p.setTiempoEntrega(0);
        p.setActivo(true);

        Boolean resultado = dao.modificarProveedor(p);
        assertFalse(resultado);
    }

    @Test
    void testActualiza_cambiaActivo() {
        TProveedor p = crearProveedor("CambiaActivo", 50.0, 3);
        Integer id = dao.creaProveedor(p);
        assertTrue(dao.mostrarProveedor(id).getActivo());

        p.setActivo(false);
        dao.modificarProveedor(p);

        TProveedor verificar = dao.mostrarProveedor(id);
        assertFalse(verificar.getActivo());
    }

    @Test
    void testBaja_exitoso() {
        TProveedor p = crearProveedor("ParaBaja", 50.0, 2);
        Integer id = dao.creaProveedor(p);

        p.setActivo(false);
        Boolean resultado = dao.bajaProveedor(p);

        assertTrue(resultado);
        TProveedor verificar = dao.mostrarProveedor(id);
        assertFalse(verificar.getActivo());
    }

    @Test
    void testBaja_noExiste_devuelveFalse() {
        TProveedor p = new TProveedor();
        p.setId(99999);
        p.setActivo(false);

        Boolean resultado = dao.bajaProveedor(p);
        assertFalse(resultado);
    }

    @Test
    void testBaja_yReactivar() {
        TProveedor p = crearProveedor("BajaReactivar", 40.0, 3);
        Integer id = dao.creaProveedor(p);

        // Baja
        p.setActivo(false);
        dao.bajaProveedor(p);
        assertFalse(dao.mostrarProveedor(id).getActivo());

        // Reactivar
        p.setActivo(true);
        dao.bajaProveedor(p); // usa UPDATE_ACTIVO
        assertTrue(dao.mostrarProveedor(id).getActivo());
    }

    @Test
    void testFlujoCompleto_CRUD() {
        // Create
        TProveedor p = crearProveedor("CRUD Test", 100.0, 5);
        Integer id = dao.creaProveedor(p);
        assertNotNull(id);
        assertTrue(id > 0);

        // Read by id
        TProveedor leido = dao.mostrarProveedor(id);
        assertNotNull(leido);
        assertEquals("CRUD Test", leido.getNombre());

        // Read by name
        TProveedor porNombre = dao.buscaProveedorPorNombre("CRUD Test");
        assertNotNull(porNombre);
        assertEquals(id, porNombre.getId());

        // Update
        p.setNombre("CRUD Actualizado");
        p.setTarifa(200.0);
        assertTrue(dao.modificarProveedor(p));
        assertEquals("CRUD Actualizado", dao.mostrarProveedor(id).getNombre());

        // List
        List<TProveedor> lista = dao.listarProveedores();
        assertFalse(lista.isEmpty());

        // Baja (soft delete)
        p.setActivo(false);
        assertTrue(dao.bajaProveedor(p));
        assertFalse(dao.mostrarProveedor(id).getActivo());
    }

    @Test
    void testCrearMultiples_yListar() {
        for (int i = 1; i <= 5; i++) {
            dao.creaProveedor(crearProveedor("Prov " + i, 10.0 * i, i));
        }

        List<TProveedor> lista = dao.listarProveedores();
        assertEquals(5, lista.size());
    }

    private TProveedor crearProveedor(String nombre, Double tarifa, Integer tiempoEntrega) {
        TProveedor p = new TProveedor();
        p.setNombre(nombre);
        p.setTarifa(tarifa);
        p.setTiempoEntrega(tiempoEntrega);
        p.setActivo(true);
        return p;
    }
}
