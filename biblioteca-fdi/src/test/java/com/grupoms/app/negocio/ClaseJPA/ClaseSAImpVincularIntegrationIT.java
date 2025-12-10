package com.grupoms.app.negocio.ClaseJPA;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;

public class ClaseSAImpVincularIntegrationIT {

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
    public void vincularYDesvincularEjemplar_shouldUpdateRelation() {
        // Crear clase
        TClase t = new TClase();
        t.setTipo("Zumba_IT");
        t.setFechaInicio(new java.util.Date());
        t.setDuracion(50);
        t.setActivo(true);

        Integer idClase = servicio.altaClase(t);
        assertThat(idClase).isNotNull().isGreaterThan(-1);

        // Crear ejemplar directamente
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();
        BOEjemplar ej = new BOEjemplar();
        ej.setActivo(true);
        em.persist(ej);
        em.flush();
        Integer idEj = ej.getId();
        em.getTransaction().commit();
        em.close();

        // Vincular
        int res = servicio.vincularEjemplarAClase(idClase, idEj);
        assertThat(res).isEqualTo(1);

        // Comprobar relación
        em = EntityManagerSingleton.getEMF().createEntityManager();
        BOClase clasePersist = em.find(BOClase.class, idClase);
        assertThat(clasePersist).isNotNull();
        assertThat(clasePersist).extracting("ejemplares").asList().isNotEmpty();
        em.close();

        // Desvincular
        int res2 = servicio.desvincularEjemplarDeClase(idClase, idEj);
        assertThat(res2).isEqualTo(1);

        em = EntityManagerSingleton.getEMF().createEntityManager();
        BOClase clasePersist2 = em.find(BOClase.class, idClase);
        assertThat(clasePersist2).isNotNull();
        assertThat(clasePersist2).extracting("ejemplares").asList().isEmpty();
        em.close();
    }
}

