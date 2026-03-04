package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import com.grupoms.app.negocio.materialJPA.MaterialSAImp;
import com.grupoms.app.negocio.materialJPA.TMaterial;
import com.grupoms.app.negocio.materialJPA.BOMaterial;

public class SAMaterialImplTest {

    static MaterialSAImp saMaterial = new SAMesaImp();

    @BeforeAll
    static void setup() throws Exception {
        saMaterial.eliminaTodas();
    }

    @AfterEach
    void cleanUp() throws Exception {
        saMaterial.eliminaTodas();
    }

    @Test
    void testAltaMaterial_DeberiaDarerror() throws Exception {
        // GIVEN
        TMaterial nueva = new TMaterial();
        nueva.autor= "autor";
        nueva.tipoMaterial = 1;
        nueva.activo = true;
        nueva.nombre= "nombre";

        // WHEN
        int id = saMaterial.altaMaterial(nueva);

        // THEN
        assertTrue(id > 0);
        TMaterial guardada = saMaterial.mostrarMaterial(id);
        assertNotNull(guardada);
        assertTrue(guardada.getActivo());
        assertEquals("autor", guardada.getAutor());
        assertEquals("nombre", guardada.getNombre());
    }

    @Test
    void testAltaLibro_DeberiaCrearYDevolverId() throws Exception {
        // GIVEN
        TLibro nueva = new TLibro();
        nueva.autor= "autor";
        nueva.editorial = "editorial";
        nueva.tipoProducto = 1;
        nueva.nombre= "nombre";
        nueva.activo = true;
        nueva.ISBN= 1;

        // WHEN
        int id = saMaterial.altaMaterial(nueva);

        // THEN
        assertTrue(id > 0);
        TLibro guardada = saMaterial.mostrarMaterial(id);
        assertNotNull(guardada);
        assertTrue(guardada.getActivo());
        assertEquals("autor", guardada.getAutor());
        assertEquals("editorial", guardada.getEditorial());
        assertEquals("nombre", guardada.getNombre());
        assertEquals(1, guardada.getISBN());
    }

    @Test
    void testAltaPintura_DeberiaCrearYDevolverId() throws Exception {
        // GIVEN
        TPintura nueva = new TPintura();
        nueva.autor= "autor";
        nueva.tipoProducto = 1;
        nueva.nombre= "nombre";
        nueva.activo = true;
        nueva.numero= 1;
        this.fecha= "1 del 1 del 1111";

        // WHEN
        int id = saMaterial.altaMaterial(nueva);

        // THEN
        assertTrue(id > 0);
        TLibro guardada = saMaterial.mostrarMaterial(id);
        assertNotNull(guardada);
        assertTrue(guardada.getActivo());
        assertEquals("autor", guardada.getAutor());
        assertEquals("nombre", guardada.getNombre());
        assertEquals(1, guardada.getNumero());
        assertEquals("1 del 1 del 1111", guardada.getFecha());
    }

    @Test
    void testbajaMaterial_DeberiaNofuncionar() throws Exception {
        // GIVEN
        TMaterial nueva = new TMaterial();
        nueva.autor= "autor";
        nueva.tipoMaterial = 1;
        nueva.activo = true;
        nueva.nombre= "nombre";
        int id = saMaterial.altaMaterial(nueva); //no se puede crear un material sin saber que es




        boolean ok = saMaterial.bajaMaterial(id);
        // THEN
        assertTrue(ok);
        TMaterial desactivada = saMaterial.mostrarMaterial(id);
        assertNotNull(desactivada);
        assertFalse(desactivada.getActivo());

    }

    @Test
    void testbajaLibro_DeberiaDesactivarLibro() throws Exception {
        // GIVEN
        TLibro nueva = new TLibro();
        nueva.autor= "autor";
        nueva.editorial = "editorial";
        nueva.tipoProducto = 1;
        nueva.nombre= "nombre";
        nueva.activo = true;
        nueva.ISBN= 1;
        int id = saMaterial.altaMaterial(nueva);


        boolean ok = saMaterial.bajaMaterial(id);
        // THEN
        assertTrue(ok);
        TMaterial desactivada = saMaterial.mostrarMaterial(id);
        assertNotNull(desactivada);
        assertFalse(desactivada.getActivo());

    }
    @Test
    void testbajaPintura_DeberiaDesactivarPintura() throws Exception {
        // GIVEN
        // GIVEN
        TPintura nueva = new TPintura();
        nueva.autor= "autor";
        nueva.tipoProducto = 1;
        nueva.nombre= "nombre";
        nueva.activo = true;
        nueva.numero= 1;
        this.fecha= "1 del 1 del 1111";
        int id = saMaterial.altaMaterial(nueva);


        boolean ok = saMaterial.bajaMaterial(id);
        // THEN
        assertTrue(ok);
        TMaterial desactivada = saMaterial.mostrarMaterial(id);
        assertNotNull(desactivada);
        assertFalse(desactivada.getActivo());
    }

    @Test
    void testMostrarLibro_DeberiaDevolverMaterialCorrecto() throws Exception {
        // GIVEN
        TLibro nueva = new TLibro();
        nueva.autor= "autor";
        nueva.editorial = "editorial";
        nueva.tipoProducto = 1;
        nueva.nombre= "nombre";
        nueva.activo = true;
        nueva.ISBN= 1;
        int id = saMaterial.altaMaterial(nueva);

        // WHEN
        TLibro recuperada = saMaterial.mostrarMaterial(id);

        // THEN
        assertNotNull(recuperada);
        assertEquals("nombre", recuperada.getNombre);
    }
    void testMostrarPintura_DeberiaDevolverMaterialCorrecto() throws Exception {
        // GIVEN
        TPintura nueva = new TPintura();
        nueva.autor= "autor";
        nueva.tipoProducto = 1;
        nueva.nombre= "nombre";
        nueva.activo = true;
        nueva.numero= 1;
        this.fecha= "1 del 1 del 1111";
        int id = saMaterial.altaMaterial(nueva);

        // WHEN
        TPintura recuperada = saMaterial.mostrarMaterial(id);

        // THEN
        assertNotNull(recuperada);
        assertEquals("nombre", recuperada.getNombre);
    }
    @Test
    void testModificarLibro_DeberiaActualizarDatos() throws Exception {
        // GIVEN
        TLibro libro = new TLibro();
        libro.autor= "autor";
        libro.editorial = "editorial";
        libro.tipoProducto = 1;
        libro.nombre= "nombre";
        libro.activo = true;
        libro.ISBN= 1;
        int id = saMaterial.altaMaterial(libro);


        libro.setId(id);
        libro.setNombre("nomb");
        libro.setTipo = 2;
        libro.setAutor("aut");
        libro.setISBN(2);
        libro.setEditorial("a");
        // WHEN
        boolean ok = saMaterial.modificarMaterial(material);

        // THEN
        assertTrue(ok);
        TPintura actualizado = (TPintura) saMaterial.mostrarMaterial(id);
        assertEquals("nomb", actualizada.getNombre());
        assertEquals(2, actualizada.getTipo());
        assertEquals("aut", actualizada.getAutor())
        assertEquals("a", actualizada.getEditorial())
        assertEquals(2, actualizada.getISBN())
    }
    @Test
    void testModificarPintura_DeberiaActualizarDatos() throws Exception {
        // GIVEN
        TPintura pintura = new TPintura();
        pintura.autor= "autor";
        pintura.tipoProducto = 1;
        pintura.nombre= "nombre";
        pintura.activo = true;
        pintura.numero= 1;
        this.fecha= "1 del 1 del 1111";
        int id = saMaterial.altaMaterial(pintura);


        pintura.setId(id);
        pintura.setNombre("nomb");
        pintura.setTipo = 2;
        pintura.setAutor("aut");
        pintura.setNumero(2);
        pintura.setFecha("0 del 0 del 0");
        // WHEN
        boolean ok = saMaterial.modificarMaterial(material);

        // THEN
        assertTrue(ok);
        TPintura actualizado = (TPintura) saMaterial.mostrarMaterial(id);
        assertEquals("nomb", actualizada.getNombre());
        assertEquals(2, actualizada.getTipo());
        assertEquals("aut", actualizada.getAutor())
        assertEquals("0 del 0 del 0", actualizada.getFecha())
    }




}
