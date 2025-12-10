package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.testutil.EMFProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SocioSAImpIT {

    private static EntityManagerFactory emf;
    private static final SocioSAImp servicio = new SocioSAImp();

    @BeforeAll
    public static void setup() {
        emf = EMFProvider.createTestEMF();
        EMFProvider.setAsGlobal(emf);
    }

    @AfterAll
    public static void teardown() {
        EMFProvider.resetGlobal();
    }

    @Test
    public void altaMostrarListarVincularDesvincularBaja_flow() throws Exception {
        // Alta un socio adulto
        TAdulto tAdulto = new TAdulto("Juan Perez", "12345678A", 0, 50, true);
        Integer id = servicio.altaSocio(tAdulto);
        assertTrue(id > 0);

        // Mostrar socio
        TSocio mostrado = servicio.mostrarSocio(id);
        assertNotNull(mostrado);
        assertEquals("Juan Perez", mostrado.getNombreYapellido());

        // Listar socios contiene al menos uno
        List<TSocio> lista = servicio.listarSocios();
        assertFalse(lista.isEmpty());

        // Crear promocion en BD
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BOPromocion promo = new BOPromocion();
        promo.setDescuento(10.0);
        promo.setTipo("General");
        promo.setActivo(true);
        em.persist(promo);
        em.getTransaction().commit();
        Integer idPromo = promo.getID();
        em.close();
        assertNotNull(idPromo);

        // Vincular promocion
        int vinc = servicio.vincularPromocionASocio(id, idPromo);
        assertEquals(1, vinc);

        // Mostrar socios por promocion
        List<TSocio> porPromo = servicio.mostrarSociosPorPromocion(idPromo);
        assertFalse(porPromo.isEmpty());

        // Desvincular promocion
        int desv = servicio.desvincularPromocionASocio(id, idPromo);
        assertEquals(1, desv);

        // Baja socio (sin prestamos) -> debe devolver 1
        int baja = servicio.bajaSocio(id);
        assertEquals(1, baja);

        // Mostrar socio ahora debe ser null (inactivo)
        assertNull(servicio.mostrarSocio(id));
    }

    @Test
    public void altaDuplicada_activaYinactiva_behaviour() {
        TAdulto t1 = new TAdulto("Maria Lopez", "87654321B", 0, 40, false);
        Integer id1 = servicio.altaSocio(t1);
        assertTrue(id1 > 0);

        // Try to add another with same name
        TAdulto t2 = new TAdulto("Maria Lopez", "22222222C", 0, 40, false);
        Exception ex = assertThrows(IllegalStateException.class, () -> servicio.altaSocio(t2));
        assertEquals("El socio ya existe", ex.getMessage());
    }

    // Nueva prueba: si un socio estaba inactivo, un alta con mismo nombre lo reactiva y devuelve mismo id
    @Test
    public void alta_reactivaSocioInactivo() throws Exception {
        TAdulto t = new TAdulto("Pedro Inactivo", "99999999X", 0, 20, false);
        Integer id = servicio.altaSocio(t);
        assertTrue(id > 0);

        // Dar de baja
        int baja = servicio.bajaSocio(id);
        assertEquals(1, baja);

        // El socio está inactivo ahora
        assertNull(servicio.mostrarSocio(id));

        // Volver a dar de alta con mismo nombre -> debe reactivar y devolver mismo id
        TAdulto t2 = new TAdulto("Pedro Inactivo", "00000000Y", 0, 20, false);
        Integer id2 = servicio.altaSocio(t2);
        assertEquals(id, id2);

        // Mostrar socio ya activo
        TSocio s = servicio.mostrarSocio(id);
        assertNotNull(s);
        assertEquals("Pedro Inactivo", s.getNombreYapellido());
    }
}
