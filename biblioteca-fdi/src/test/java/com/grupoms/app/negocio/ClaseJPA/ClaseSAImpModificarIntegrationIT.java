package com.grupoms.app.negocio.ClaseJPA;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;

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
