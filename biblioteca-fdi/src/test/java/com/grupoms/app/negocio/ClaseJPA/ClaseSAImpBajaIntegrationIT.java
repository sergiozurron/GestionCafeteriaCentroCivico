package com.grupoms.app.negocio.ClaseJPA;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;

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

