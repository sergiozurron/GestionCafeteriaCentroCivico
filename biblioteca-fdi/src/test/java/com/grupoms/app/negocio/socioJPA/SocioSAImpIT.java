package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.testutil.EMFProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SocioSAImpIT {

    private static EntityManagerFactory emf;
    private static SocioSAImp servicio;

    @BeforeAll
    public static void setup() {
        emf = EMFProvider.createTestEMF();
        EMFProvider.setAsGlobal(emf);
        servicio = new SocioSAImp();
    }

    @AfterAll
    public static void teardown() {
        EMFProvider.resetGlobal();
    }

    @Test
    public void altaAdulto_devuelveIdPositivo() {
        TAdulto t = new TAdulto("Alta Test 1", "AAA11111A", 0, 50, true);
        Integer id = servicio.altaSocio(t);
        assertTrue(id > 0, "El ID generado debe ser positivo");
    }

    @Test
    public void altaInfantil_devuelveIdPositivo() {
        TInfantil t = new TInfantil("Alta Infantil 1", "III11111A", 1, 20, 0.20, 10);
        Integer id = servicio.altaSocio(t);
        assertTrue(id > 0, "El ID generado debe ser positivo");
    }

    @Test
    public void altaDuplicada_socioActivo_devuelveMenosUno() {
        TAdulto t1 = new TAdulto("Duplicado Activo", "DUP11111A", 0, 40, false);
        Integer id1 = servicio.altaSocio(t1);
        assertTrue(id1 > 0);

        // Alta duplicada de socio activo devuelve -1
        TAdulto t2 = new TAdulto("Duplicado Activo", "DUP22222B", 0, 40, false);
        Integer id2 = servicio.altaSocio(t2);
        assertEquals(-1, id2);
    }

    @Test
    public void altaReactivaSocioInactivo() throws Exception {
        TAdulto t = new TAdulto("Reactivar Test", "REA11111A", 0, 20, false);
        Integer id = servicio.altaSocio(t);
        assertTrue(id > 0);

        // Dar de baja
        int baja = servicio.bajaSocio(id);
        assertEquals(1, baja);
        assertNull(servicio.mostrarSocio(id));

        // Reactivar con misma nombre
        TAdulto t2 = new TAdulto("Reactivar Test", "REA22222B", 0, 20, false);
        Integer id2 = servicio.altaSocio(t2);
        assertEquals(id, id2, "Debe reutilizar el mismo ID al reactivar");

        TSocio s = servicio.mostrarSocio(id);
        assertNotNull(s);
        assertEquals("Reactivar Test", s.getNombreYapellido());
    }

    // ==================== MOSTRAR ====================

    @Test
    public void mostrarSocio_existente() {
        TAdulto t = new TAdulto("Mostrar Test", "MOT11111A", 0, 50, true);
        Integer id = servicio.altaSocio(t);

        TSocio mostrado = servicio.mostrarSocio(id);
        assertNotNull(mostrado);
        assertEquals("Mostrar Test", mostrado.getNombreYapellido());
        assertEquals("MOT11111A", mostrado.getDni());
    }

    @Test
    public void mostrarSocio_inexistente_devuelveNull() {
        assertNull(servicio.mostrarSocio(99999));
    }

    @Test
    public void mostrarSocio_idNull_devuelveNull() {
        assertNull(servicio.mostrarSocio(null));
    }

    @Test
    public void mostrarSocio_idNegativo_devuelveNull() {
        assertNull(servicio.mostrarSocio(-1));
    }

    @Test
    public void mostrarAdulto_devuelveTAdulto() {
        TAdulto t = new TAdulto("Adulto Mostrar", "ADM11111A", 0, 45, true);
        Integer id = servicio.altaSocio(t);

        TSocio resultado = servicio.mostrarSocio(id);
        assertNotNull(resultado);
        assertInstanceOf(TAdulto.class, resultado);
        assertTrue(((TAdulto) resultado).getMiembroPleno());
    }

    @Test
    public void mostrarInfantil_devuelveTInfantil() {
        TInfantil t = new TInfantil("Infantil Mostrar", "INM11111A", 1, 20, 0.20, 8);
        Integer id = servicio.altaSocio(t);

        TSocio resultado = servicio.mostrarSocio(id);
        assertNotNull(resultado);
        assertInstanceOf(TInfantil.class, resultado);
        assertEquals(8, ((TInfantil) resultado).getEdad());
    }

    // ==================== LISTAR ====================

    @Test
    public void listarSocios_devuelveListaNoVacia() {
        // Aseguramos que hay al menos uno
        TAdulto t = new TAdulto("Listar Test", "LIS11111A", 0, 30, false);
        servicio.altaSocio(t);

        List<TSocio> lista = servicio.listarSocios();
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    public void listarSocios_preservaTipos() {
        TAdulto ta = new TAdulto("Listar Adulto Tipo", "LAT11111A", 0, 30, true);
        TInfantil ti = new TInfantil("Listar Infantil Tipo", "LIT11111A", 1, 15, 0.20, 5);
        servicio.altaSocio(ta);
        servicio.altaSocio(ti);

        List<TSocio> lista = servicio.listarSocios();
        boolean tieneAdulto = lista.stream().anyMatch(s -> s instanceof TAdulto);
        boolean tieneInfantil = lista.stream().anyMatch(s -> s instanceof TInfantil);
        assertTrue(tieneAdulto, "La lista debe contener al menos un TAdulto");
        assertTrue(tieneInfantil, "La lista debe contener al menos un TInfantil");
    }

    // ==================== MODIFICAR ====================

    @Test
    public void modificarSocio_exitoso() {
        TAdulto t = new TAdulto("Antes Modificar", "MOD11111A", 0, 30, false);
        Integer id = servicio.altaSocio(t);
        assertTrue(id > 0);

        TAdulto mod = new TAdulto("Después Modificar", "MOD11111A", 0, 60, true);
        mod.setId(id);
        Integer resultado = servicio.modificarSocio(mod);
        assertTrue(resultado > 0);

        TSocio verificar = servicio.mostrarSocio(id);
        assertNotNull(verificar);
        assertEquals("Después Modificar", verificar.getNombreYapellido());
        assertEquals(60, verificar.getCuota());
    }

    @Test
    public void modificarSocio_inexistente_devuelveMenosUno() {
        TAdulto t = new TAdulto("No Existe", "NEX11111A", 0, 30, false);
        t.setId(99999);
        Integer resultado = servicio.modificarSocio(t);
        assertEquals(-1, resultado);
    }

    // ==================== BAJA ====================

    @Test
    public void bajaSocio_exitosa() throws Exception {
        TAdulto t = new TAdulto("Baja Test", "BAJ11111A", 0, 25, false);
        Integer id = servicio.altaSocio(t);
        assertTrue(id > 0);

        int resultado = servicio.bajaSocio(id);
        assertEquals(1, resultado);

        // Socio inactivo no se muestra
        assertNull(servicio.mostrarSocio(id));
    }

    @Test
    public void bajaSocio_inexistente_lanzaException() {
        assertThrows(Exception.class, () -> servicio.bajaSocio(99999));
    }

    @Test
    public void bajaSocio_yaInactivo_lanzaException() throws Exception {
        TAdulto t = new TAdulto("Baja Doble", "BAD11111A", 0, 25, false);
        Integer id = servicio.altaSocio(t);
        servicio.bajaSocio(id);

        // Segunda baja debe fallar
        assertThrows(Exception.class, () -> servicio.bajaSocio(id));
    }

    @Test
    public void bajaSocio_limpiaPromociones() throws Exception {
        TAdulto t = new TAdulto("Baja Promo Test", "BPT11111A", 0, 30, true);
        Integer idSocio = servicio.altaSocio(t);

        // Crear promoción
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(25.0);
        promo.setTipo("Test Baja");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();

        // Vincular
        int vinc = servicio.vincularPromocionASocio(idSocio, idPromo);
        assertEquals(1, vinc);

        // Verificar que tiene la promoción
        List<TSocio> antes = servicio.mostrarSociosPorPromocion(idPromo);
        assertFalse(antes.isEmpty());

        // Dar de baja - debe limpiar promociones
        servicio.bajaSocio(idSocio);

        // Verificar que las promociones se limpiaron
        List<TSocio> despues = servicio.mostrarSociosPorPromocion(idPromo);
        assertTrue(despues.stream().noneMatch(s -> s.getId().equals(idSocio)));
    }

    // ==================== VINCULAR / DESVINCULAR PROMOCIÓN ====================

    @Test
    public void vincularPromocion_exitoso() {
        TAdulto t = new TAdulto("Vincular Test", "VIN11111A", 0, 40, true);
        Integer idSocio = servicio.altaSocio(t);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(10.0);
        promo.setTipo("Vincular");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();

        int resultado = servicio.vincularPromocionASocio(idSocio, idPromo);
        assertEquals(1, resultado);
    }

    @Test
    public void vincularPromocion_socioInexistente_devuelveMenosUno() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(5.0);
        promo.setTipo("SinSocio");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();

        int resultado = servicio.vincularPromocionASocio(99999, idPromo);
        assertEquals(-1, resultado);
    }

    @Test
    public void desvincularPromocion_exitoso() {
        TAdulto t = new TAdulto("Desvincular Test", "DVT11111A", 0, 35, true);
        Integer idSocio = servicio.altaSocio(t);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(12.0);
        promo.setTipo("Desvincular");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();

        servicio.vincularPromocionASocio(idSocio, idPromo);
        int resultado = servicio.desvincularPromocionASocio(idSocio, idPromo);
        assertEquals(1, resultado);
    }

    @Test
    public void desvincularPromocion_noVinculada_devuelveMenosUno() {
        TAdulto t = new TAdulto("Desv NoVinc Test", "DNV11111A", 0, 30, false);
        Integer idSocio = servicio.altaSocio(t);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(8.0);
        promo.setTipo("NoVinculada");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();

        // Intentar desvincular sin haber vinculado
        int resultado = servicio.desvincularPromocionASocio(idSocio, idPromo);
        assertEquals(-1, resultado);
    }

    @Test
    public void mostrarSociosPorPromocion_conResultados() {
        TAdulto t = new TAdulto("PorPromo Test", "PPT11111A", 0, 50, true);
        Integer idSocio = servicio.altaSocio(t);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(30.0);
        promo.setTipo("PorPromo");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();

        servicio.vincularPromocionASocio(idSocio, idPromo);

        List<TSocio> resultado = servicio.mostrarSociosPorPromocion(idPromo);
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.stream().anyMatch(s -> s.getNombreYapellido().equals("PorPromo Test")));
    }

    @Test
    public void mostrarSociosPorPromocion_sinResultados() {
        List<TSocio> resultado = servicio.mostrarSociosPorPromocion(99999);
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ==================== FLUJO COMPLETO ====================

    @Test
    public void flujoCompleto_altaMostrarModificarListarVincularDesvincularBaja() throws Exception {
        // 1. Alta
        TAdulto tAdulto = new TAdulto("Flujo Completo", "FLC11111A", 0, 50, true);
        Integer id = servicio.altaSocio(tAdulto);
        assertTrue(id > 0);

        // 2. Mostrar
        TSocio mostrado = servicio.mostrarSocio(id);
        assertNotNull(mostrado);
        assertEquals("Flujo Completo", mostrado.getNombreYapellido());

        // 3. Modificar
        TAdulto mod = new TAdulto("Flujo Modificado", "FLC11111A", 0, 70, false);
        mod.setId(id);
        Integer idMod = servicio.modificarSocio(mod);
        assertTrue(idMod > 0);

        TSocio verificado = servicio.mostrarSocio(id);
        assertEquals("Flujo Modificado", verificado.getNombreYapellido());
        assertEquals(70, verificado.getCuota());

        // 4. Listar
        List<TSocio> lista = servicio.listarSocios();
        assertFalse(lista.isEmpty());

        // 5. Crear promoción y vincular
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(10.0);
        promo.setTipo("Flujo");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();

        int vinc = servicio.vincularPromocionASocio(id, idPromo);
        assertEquals(1, vinc);

        // 6. Mostrar por promoción
        List<TSocio> porPromo = servicio.mostrarSociosPorPromocion(idPromo);
        assertFalse(porPromo.isEmpty());

        // 7. Desvincular
        int desv = servicio.desvincularPromocionASocio(id, idPromo);
        assertEquals(1, desv);

        // 8. Baja
        int baja = servicio.bajaSocio(id);
        assertEquals(1, baja);
        assertNull(servicio.mostrarSocio(id));
    }

    @Test
    public void testToStringAdulto() {
        TAdulto t = new TAdulto("ToString Adulto", "TSA11111A", 0, 40, true);
        t.setId(100);
        String str = t.toString();
        assertTrue(str.contains("ToString Adulto"));
        assertTrue(str.contains("100"));
        assertTrue(str.contains("miembroPleno=true"));
    }

    @Test
    public void testToStringInfantil() {
        TInfantil t = new TInfantil("ToString Infantil", "TSI11111A", 1, 20, 0.20, 8);
        t.setId(200);
        String str = t.toString();
        assertTrue(str.contains("ToString Infantil"));
        assertTrue(str.contains("200"));
        assertTrue(str.contains("edad=8"));
    }
}
