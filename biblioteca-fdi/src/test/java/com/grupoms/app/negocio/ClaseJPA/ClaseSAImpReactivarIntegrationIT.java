package com.grupoms.app.negocio.ClaseJPA;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;

public class ClaseSAImpReactivarIntegrationIT {

    private static EntityManagerFactory emfTest;
    private static ClaseSAImp servicio;

    @BeforeAll
    public static void setup() {
        emfTest = Persistence.createEntityManagerFactory("CentroCivicoJPA");
        EntityManagerSingleton.setEMF(emfTest);
        servicio = new ClaseSAImp();
    }

    @AfterAll
    public static void tearDown() {
        EntityManagerSingleton.reset();
        if (emfTest != null && emfTest.isOpen()) {
            emfTest.close();
        }
    }

    @Test
    public void alta_reactivaClaseInactiva() {
        // Insertar entidad inactiva
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();
        BOClase bo = new BOClase();
        bo.setTipo("Pilates_IT");
        bo.setFechaInicio(new java.util.Date());
        bo.setDuracion(30);
        bo.setActivo(false);
        em.persist(bo);
        em.flush();
        Integer id = bo.getId();
        em.getTransaction().commit();
        em.close();

        // Intentar alta con mismo tipo+fecha -> debe reactivar y devolver id existente
        TClase t = new TClase();
        t.setTipo("Pilates_IT");
        t.setFechaInicio(bo.getFechaInicio());
        t.setDuracion(30);
        t.setActivo(true);

        Integer res = servicio.altaClase(t);
        assertThat(res).isEqualTo(id);

        // Comprobar activo
        em = EntityManagerSingleton.getEMF().createEntityManager();
        BOClase comprobacion = em.find(BOClase.class, id);
        assertThat(comprobacion.getActivo()).isTrue();
        em.close();
    }
}

