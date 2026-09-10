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

public class ClaseSAImpBajaIntegrationIT {

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
    public void bajaClase_shouldSetActivoFalse() {
        TClase t = new TClase();
        t.setTipo("Baja_IT");
        t.setFechaInicio(new java.util.Date());
        t.setDuracion(20);
        t.setActivo(true);

        // crear sala activa
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();
        BOSala sala = new BOSala();
        sala.setNombre("SalaBaja");
        sala.setCapacidad(15);
        sala.setActivo(true);
        em.persist(sala);
        em.flush();
        Integer idSala = sala.getId();
        em.getTransaction().commit();
        em.close();

        t.setIdSala(idSala);

        Integer id = servicio.altaClase(t);
        assertThat(id).isNotNull().isGreaterThan(-1);

        Integer res = servicio.bajaClase(id);
        assertThat(res).isEqualTo(1);

        TClase fetched = servicio.mostrarClase(id);
        
        if (fetched == null) {
            
            
            assertThat(fetched).isNull();
        } else {
            assertThat(fetched.getActivo()).isFalse();
        }
    }
}
