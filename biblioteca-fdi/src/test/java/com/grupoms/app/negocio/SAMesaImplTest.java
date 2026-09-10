package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.mesa.DAOMesaImp;
import com.grupoms.app.negocio.mesa.SAMesaImp;
import com.grupoms.app.negocio.mesa.TMesa;
import com.grupoms.app.negocio.mesa.TMesaSala;
import com.grupoms.app.negocio.mesa.TMesaTerraza;

public class SAMesaImplTest {

    static SAMesaImp saMesa = new SAMesaImp();
    static DAOMesaImp daoMesa = new DAOMesaImp();

    @BeforeAll
    static void setup() throws Exception {
    //    daoMesa.eliminaTodas(); 
    }

    @AfterEach
    void cleanUp() throws Exception {
    //    daoMesa.eliminaTodas();
    }

    @Test
    void testAltaMesaSala_DeberiaCrearYDevolverId() throws Exception {
        
        TMesaSala nueva = new TMesaSala();
        nueva.setUbicacion("Sala Norte");
        nueva.setNumero(5);
        nueva.setCapacidad(4);
        nueva.setPrivacidad("Alta");
        nueva.setReservada(false);

        
        int id = saMesa.altaMesa(nueva);

        
        assertTrue(id > 0);
        TMesa guardada = daoMesa.mostrarMesa(id);
        assertNotNull(guardada);
        assertTrue(guardada.getActivo());
        assertEquals("Sala Norte", guardada.getUbicacion());
    }

    @Test
    void testAltaMesaTerraza_DeberiaCrearYDevolverId() throws Exception {
        
        TMesaTerraza nueva = new TMesaTerraza();
        nueva.setUbicacion("Terraza Sur");
        nueva.setNumero(8);
        nueva.setCapacidad(6);
        nueva.setCubierta(true);
        nueva.setSuplemento(3.5);

        
        int id = saMesa.altaMesa(nueva);

        
        assertTrue(id > 0);
        TMesa guardada = daoMesa.mostrarMesa(id);
        assertNotNull(guardada);
        assertTrue(guardada.getActivo());
        assertEquals("Terraza Sur", guardada.getUbicacion());
    }

    @Test
    void testMostrarMesa_DeberiaDevolverMesaCorrecta() throws Exception {
        
        TMesaSala mesa = new TMesaSala();
        mesa.setUbicacion("Sala Este");
        mesa.setNumero(2);
        mesa.setCapacidad(3);
        mesa.setPrivacidad("Media");
        mesa.setReservada(true);
        int id = saMesa.altaMesa(mesa);

        
        TMesa recuperada = saMesa.mostrarMesa(id);

        
        assertNotNull(recuperada);
        assertEquals("Sala Este", recuperada.getUbicacion());
    }

    @Test
    void testModificarMesaSala_DeberiaActualizarDatos() throws Exception {
        
        TMesaSala mesa = new TMesaSala();
        mesa.setUbicacion("Sala Oeste");
        mesa.setNumero(4);
        mesa.setCapacidad(5);
        mesa.setPrivacidad("Baja");
        mesa.setReservada(false);
        int id = saMesa.altaMesa(mesa);

        mesa.setId(id);
        mesa.setPrivacidad("Alta");
        mesa.setReservada(true);

        
        boolean ok = saMesa.modificarMesa(mesa);

        
        assertTrue(ok);
        TMesaSala actualizada = (TMesaSala) saMesa.mostrarMesa(id);
        assertEquals("Alta", actualizada.getPrivacidad());
        assertTrue(actualizada.getReservada());
    }

    @Test
    void testMostrarListaMesa_DeberiaDevolverSoloActivas() throws Exception {
        
        TMesaSala activa = new TMesaSala();
        activa.setUbicacion("Sala 1");
        activa.setNumero(1);
        activa.setCapacidad(4);
        activa.setPrivacidad("Media");
        activa.setReservada(false);
        saMesa.altaMesa(activa);

        TMesaSala inactiva = new TMesaSala();
        inactiva.setUbicacion("Sala 2");
        inactiva.setNumero(2);
        inactiva.setCapacidad(4);
        inactiva.setPrivacidad("Baja");
        inactiva.setReservada(false);
        int idInactiva = saMesa.altaMesa(inactiva);
        inactiva.setId(idInactiva);
        saMesa.bajaMesa(inactiva);

        
        List<TMesa> lista = saMesa.mostrarListaMesa();

        
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertTrue(lista.stream().allMatch(TMesa::getActivo));
    }

    @Test
    void testBajaMesa_DeberiaDesactivarMesa() throws Exception {
        
        TMesaTerraza mesa = new TMesaTerraza();
        mesa.setUbicacion("Terraza Oeste");
        mesa.setNumero(7);
        mesa.setCapacidad(5);
        mesa.setCubierta(false);
        mesa.setSuplemento(2.0);
        int id = saMesa.altaMesa(mesa);
        mesa.setId(id);

        
        boolean ok = saMesa.bajaMesa(mesa);

        
        assertTrue(ok);
        TMesa desactivada = daoMesa.mostrarMesa(id);
        assertFalse(desactivada.getActivo());
    }
}
