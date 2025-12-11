package com.grupoms.app.negocio.ClaseJPA;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.salaJPA.BOSala;

public class ClaseSAImpIntegrationIT {

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
    public void altaYMostrarClase_shouldPersistAndRetrieve() {
        TClase t = new TClase();
        t.setTipo("Pilates_IT");
        t.setFechaInicio(new java.util.Date());
        t.setDuracion(45);
        t.setActivo(true);

        // crear una sala activa y asignar su id a la clase
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();
        BOSala sala = new BOSala();
        sala.setNombre("SalaTest");
        sala.setCapacidad(20);
        sala.setActivo(true);
        em.persist(sala);
        em.flush();
        Integer idSala = sala.getId();
        em.getTransaction().commit();
        em.close();

        t.setIdSala(idSala);

        Integer id = servicio.altaClase(t);
        assertThat(id).isNotNull().isGreaterThan(-1);

        TClase fetched = servicio.mostrarClase(id);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getTipo()).isEqualTo("Pilates_IT");
        assertThat(fetched.getDuracion()).isEqualTo(45);
    }
}
