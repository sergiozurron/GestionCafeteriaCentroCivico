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
import com.grupoms.app.negocio.salaJPA.BOSala;

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
        
        TClase t = new TClase();
        t.setTipo("Zumba_IT");
        t.setFechaInicio(new java.util.Date());
        t.setDuracion(50);
        t.setActivo(true);

        // crear sala y asignarla
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();
        BOSala sala = new BOSala();
        sala.setNombre("SalaVinc");
        sala.setCapacidad(25);
        sala.setActivo(true);
        em.persist(sala);
        em.flush();
        Integer idSala = sala.getId();
        em.getTransaction().commit();
        em.close();

        t.setIdSala(idSala);

        Integer idClase = servicio.altaClase(t);
        assertThat(idClase).isNotNull().isGreaterThan(-1);


        EntityManager em2 = EntityManagerSingleton.getEMF().createEntityManager();
        em2.getTransaction().begin();
        BOEjemplar ej = new BOEjemplar();
        ej.setActivo(true);
        em2.persist(ej);
        em2.flush();
        Integer idEj = ej.getId();
        em2.getTransaction().commit();
        em2.close();


        int res = servicio.vincularEjemplarAClase(idClase, idEj);
        assertThat(res).isEqualTo(1);

        
        em2 = EntityManagerSingleton.getEMF().createEntityManager();
        BOClase clasePersist = em2.find(BOClase.class, idClase);
        assertThat(clasePersist).isNotNull();
        assertThat(clasePersist).extracting("ejemplares").asList().isNotEmpty();
        em2.close();

        
        int res2 = servicio.desvincularEjemplarDeClase(idClase, idEj);
        assertThat(res2).isEqualTo(1);

        em2 = EntityManagerSingleton.getEMF().createEntityManager();
        BOClase clasePersist2 = em2.find(BOClase.class, idClase);
        assertThat(clasePersist2).isNotNull();
        assertThat(clasePersist2).extracting("ejemplares").asList().isEmpty();
        em2.close();
    }
}
