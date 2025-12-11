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

public class ClaseSAImpModificarIntegrationIT {

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
    public void modificarClase_shouldUpdateFields() {
        TClase t = new TClase();
        t.setTipo("Mod_IT");
        t.setFechaInicio(new java.util.Date());
        t.setDuracion(30);
        t.setActivo(true);

        // crear sala
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();
        BOSala sala = new BOSala();
        sala.setNombre("SalaMod");
        sala.setCapacidad(10);
        sala.setActivo(true);
        em.persist(sala);
        em.flush();
        Integer idSala = sala.getId();
        em.getTransaction().commit();
        em.close();

        t.setIdSala(idSala);

        Integer id = servicio.altaClase(t);
        assertThat(id).isNotNull().isGreaterThan(-1);

        
        TClase mod = new TClase();
        mod.setId(id);
        mod.setTipo("Modificado_IT");
        mod.setFechaInicio(t.getFechaInicio());
        mod.setDuracion(99);

        Integer res = servicio.modificarClase(mod);
        assertThat(res).isEqualTo(id);

        TClase fetched = servicio.mostrarClase(id);
        assertThat(fetched.getTipo()).isEqualTo("Modificado_IT");
        assertThat(fetched.getDuracion()).isEqualTo(99);
    }
}
