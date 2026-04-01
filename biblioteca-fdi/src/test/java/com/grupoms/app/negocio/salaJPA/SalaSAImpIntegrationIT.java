package com.grupoms.app.negocio.salaJPA;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.ClaseJPA.TClase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SalaSAImpIntegrationIT {

    private static EntityManagerFactory emfTest;
    private static SalaSAImp servicio;

    @BeforeAll
    public static void setup() {
        emfTest = Persistence.createEntityManagerFactory("CentroCivicoJPA");
        EntityManagerSingleton.setEMF(emfTest);
        servicio = new SalaSAImp();
    }

    @AfterAll
    public static void tearDown() {
        EntityManagerSingleton.reset();
        if (emfTest != null && emfTest.isOpen()) {
            emfTest.close();
        }
    }

    @Test
    public void altaSala_invalida_shouldReturnKO() {
        TSala sala = new TSala();
        sala.setNombre(" ");
        sala.setCapacidad(0);
        sala.setActivo(true);

        Integer id = servicio.altaSala(sala);

        assertThat(id).isEqualTo(-1);
    }

    @Test
    public void altaSala_reactivarSalaInactiva_shouldReactivateAndUpdateCapacidad() {
        Integer idSala = crearSala("Sala_Reactivar_IT", 10, false);

        TSala alta = new TSala();
        alta.setNombre("Sala_Reactivar_IT");
        alta.setCapacidad(33);
        alta.setActivo(true);

        Integer idReactivada = servicio.altaSala(alta);

        assertThat(idReactivada).isEqualTo(idSala);

        TSala mostrada = servicio.mostrarSala(idSala);
        assertThat(mostrada).isNotNull();
        assertThat(mostrada.getActivo()).isTrue();
        assertThat(mostrada.getCapacidad()).isEqualTo(33);
    }

    @Test
    public void bajaSala_conClasesActivas_shouldReturnMenosDos() {
        Integer idSala = crearSala("Sala_Con_Clase_IT", 20, true);
        crearClaseActivaEnSala(idSala, "Yoga_Baja_IT");

        Integer res = servicio.bajaSala(idSala);

        assertThat(res).isEqualTo(-2);

        TSala sala = servicio.mostrarSala(idSala);
        assertThat(sala).isNotNull();
        assertThat(sala.getActivo()).isTrue();
    }

    @Test
    public void mostrarClasesPorSala_inexistente_shouldReturnNull() {
        List<TClase> clases = servicio.mostrarClasesPorSala(999999);

        assertThat(clases).isNull();
    }

    @Test
    public void mostrarClasesPorSala_salaValidaSinClases_shouldReturnListaVacia() {
        Integer idSala = crearSala("Sala_Sin_Clases_IT", 18, true);

        List<TClase> clases = servicio.mostrarClasesPorSala(idSala);

        assertThat(clases).isNotNull();
        assertThat(clases).isEmpty();
    }

    @Test
    public void modificarSala_nombreDuplicadoActivo_shouldReturnKO() {
        Integer idSala1 = crearSala("Sala_Nombre_A_IT", 10, true);
        Integer idSala2 = crearSala("Sala_Nombre_B_IT", 12, true);

        TSala cambio = new TSala();
        cambio.setId(idSala2);
        cambio.setNombre("Sala_Nombre_A_IT");
        cambio.setCapacidad(40);

        Integer res = servicio.modificarSala(cambio);

        assertThat(res).isEqualTo(-1);

        TSala sala2 = servicio.mostrarSala(idSala2);
        assertThat(sala2).isNotNull();
        assertThat(sala2.getNombre()).isEqualTo("Sala_Nombre_B_IT");
    }

    private Integer crearSala(String nombre, int capacidad, boolean activa) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();

        BOSala sala = new BOSala();
        sala.setNombre(nombre);
        sala.setCapacidad(capacidad);
        sala.setActivo(activa);

        em.persist(sala);
        em.flush();
        Integer id = sala.getId();

        em.getTransaction().commit();
        em.close();
        return id;
    }

    private void crearClaseActivaEnSala(Integer idSala, String tipo) {
        EntityManager em = EntityManagerSingleton.getEMF().createEntityManager();
        em.getTransaction().begin();

        BOSala sala = em.find(BOSala.class, idSala);

        BOClase clase = new BOClase();
        clase.setTipo(tipo);
        clase.setFechaInicio(new Date());
        clase.setDuracion(45);
        clase.setActivo(true);
        clase.setSala(sala);

        em.persist(clase);

        em.getTransaction().commit();
        em.close();
    }
}
