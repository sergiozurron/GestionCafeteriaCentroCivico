package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSAImp;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.materialJPA.BOLibro;
import com.grupoms.app.negocio.materialJPA.BOMaterial;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EjemplarSAImpTest {

	EjemplarSAImp ejemplarSA = new EjemplarSAImp();
	EntityManagerFactory emf = EntityManagerSingleton.getEMF();
	
	@Test
	void altaEjemplar_DeberiaCrearEjemplarYDevolverId() {
		// GIVEN
		BOLibro libro = new BOLibro();
		libro.setISBN(123456);
		libro.setNombre("Titulo de Prueba");
		libro.setAutor("Autor de Prueba");
		libro.setEditorial("Editorial de Prueba");
		libro.setTipoMaterial(1);
		libro.setActivo(true);
		
		crearMaterialPrueba(libro);
		
		TEjemplar ejemplar = new TEjemplar();
		ejemplar.setIdMaterial(libro.getID());
		ejemplar.setEstado("Nuevo");
		
		// WHEN
		int idEjemplar = ejemplarSA.altaEjemplar(ejemplar);
		
		// THEN
		assertTrue(idEjemplar > 0);
		
		BOEjemplar boEjemplar = buscarEjemplarPorId(idEjemplar);
		
		assertNotNull(boEjemplar);
	}
	
	@Test
	void altaEjemplar_DeberiaDevolverMenosUno_CuandoMaterialNoExiste() {
		// GIVEN
		TEjemplar ejemplar = new TEjemplar();
		ejemplar.setIdMaterial(-1); // ID de material inexistente
		ejemplar.setEstado("Nuevo");
		
		// WHEN
		int idEjemplar = ejemplarSA.altaEjemplar(ejemplar);
		
		// THEN
		assertTrue(idEjemplar == -1);
	}
	
	@Test
	void altaEjemplar_DeberiaDevolverMenosUno_CuandoMaterialNoActivo() {
		// GIVEN
		BOLibro libro = new BOLibro();
		libro.setISBN(111111);
		libro.setNombre("Titulo Inactivo");
		libro.setAutor("Autor Inactivo");
		libro.setEditorial("Editorial Inactivo");
		libro.setTipoMaterial(1);
		libro.setActivo(false);
		
		crearMaterialPrueba(libro);
		
		TEjemplar ejemplar = new TEjemplar();
		ejemplar.setIdMaterial(libro.getID());
		ejemplar.setEstado("Nuevo");
		
		// WHEN
		int idEjemplar = ejemplarSA.altaEjemplar(ejemplar);
		
		// THEN
		assertTrue(idEjemplar == -1);
	}
	
	@Test
	void bajaEjemplar_DeberiaEliminarEjemplarYDevolverTrue() {
		// GIVEN
		BOLibro libro = new BOLibro();
		libro.setISBN(654321);
		libro.setNombre("Titulo de Prueba Baja");
		libro.setAutor("Autor de Prueba Baja");
		libro.setEditorial("Editorial de Prueba Baja");
		libro.setTipoMaterial(1);
		libro.setActivo(true);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("Usado");
		ejemplar.setActivo(true);
		ejemplar.setMaterial(libro);
		
		crearMaterialPrueba(libro);
		crearEjemplarPrueba(ejemplar);
		
		// WHEN
		boolean resultado = ejemplarSA.bajaEjemplar(ejemplar.getId());
		
		// THEN
		assertTrue(resultado);
		
		BOEjemplar ejemplarDesactivado = buscarEjemplarPorId(ejemplar.getId());
		
		assertNotNull(ejemplarDesactivado);
		assertTrue(!ejemplarDesactivado.getActivo());
	}
	
	@Test
	void bajaEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoExiste() {
		// WHEN
		boolean resultado = ejemplarSA.bajaEjemplar(-1); // ID de ejemplar inexistente
		
		// THEN
		assertTrue(!resultado);
	}
	
	@Test
	void bajaEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoActivo() {
		// GIVEN
		BOLibro libro = new BOLibro();
		libro.setISBN(222222);
		libro.setNombre("Titulo Inactivo Baja");
		libro.setAutor("Autor Inactivo Baja");
		libro.setEditorial("Editorial Inactivo Baja");
		libro.setTipoMaterial(1);
		libro.setActivo(true);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("Dañado");
		ejemplar.setActivo(false);
		ejemplar.setMaterial(libro);
		
		crearMaterialPrueba(libro);
		crearEjemplarPrueba(ejemplar);
		
		// WHEN
		boolean resultado = ejemplarSA.bajaEjemplar(ejemplar.getId());
		
		// THEN
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaModificarEjemplarYDevolverTrue() {
		// GIVEN
		BOLibro libro = new BOLibro();
		libro.setISBN(333333);
		libro.setNombre("Titulo Modificar");
		libro.setAutor("Autor Modificar");
		libro.setEditorial("Editorial Modificar");
		libro.setTipoMaterial(1);
		libro.setActivo(true);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("Bueno");
		ejemplar.setActivo(true);
		ejemplar.setMaterial(libro);
		
		crearMaterialPrueba(libro);
		crearEjemplarPrueba(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(libro.getID());
		tEjemplar.setEstado("Excelente");
		
		// WHEN
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		// THEN
		assertTrue(resultado);
		
		BOEjemplar ejemplarModificado = buscarEjemplarPorId(ejemplar.getId());
		
		assertNotNull(ejemplarModificado);
		assertTrue(ejemplarModificado.getEstado().equals("Excelente"));
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoExiste() {
		// GIVEN
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(-1); // ID de ejemplar inexistente
		tEjemplar.setIdMaterial(1);
		tEjemplar.setEstado("Regular");
		
		// WHEN
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		// THEN
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoActivo() {
		// GIVEN
		BOLibro libro = new BOLibro();
		libro.setISBN(444444);
		libro.setNombre("Titulo Inactivo Modificar");
		libro.setAutor("Autor Inactivo Modificar");
		libro.setEditorial("Editorial Inactivo Modificar");
		libro.setTipoMaterial(1);
		libro.setActivo(true);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("Malo");
		ejemplar.setActivo(false);
		ejemplar.setMaterial(libro);
		
		crearMaterialPrueba(libro);
		crearEjemplarPrueba(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(libro.getID());
		tEjemplar.setEstado("Regular");
		
		// WHEN
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		// THEN
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoMaterialNoExiste() {
		// GIVEN
		BOLibro libro = new BOLibro();
		libro.setISBN(555555);
		libro.setNombre("Titulo Material No Existe");
		libro.setAutor("Autor Material No Existe");
		libro.setEditorial("Editorial Material No Existe");
		libro.setTipoMaterial(1);
		libro.setActivo(true);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("Aceptable");
		ejemplar.setActivo(true);
		ejemplar.setMaterial(libro);
		
		crearMaterialPrueba(libro);
		crearEjemplarPrueba(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(-1); // ID de material inexistente
		tEjemplar.setEstado("Bueno");
		
		// WHEN
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		// THEN
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoMaterialNoActivo() {
		// GIVEN
		BOLibro libroActivo = new BOLibro();
		libroActivo.setISBN(666666);
		libroActivo.setNombre("Titulo Material Inactivo");
		libroActivo.setAutor("Autor Material Inactivo");
		libroActivo.setEditorial("Editorial Material Inactivo");
		libroActivo.setTipoMaterial(1);
		libroActivo.setActivo(true);
		
		BOLibro libroInactivo = new BOLibro();
		libroInactivo.setISBN(777777);
		libroInactivo.setNombre("Titulo Material Inactivo 2");
		libroInactivo.setAutor("Autor Material Inactivo 2");
		libroInactivo.setEditorial("Editorial Material Inactivo 2");
		libroInactivo.setTipoMaterial(1);
		libroInactivo.setActivo(false);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("Regular");
		ejemplar.setActivo(true);
		ejemplar.setMaterial(libroActivo);
		
		crearMaterialPrueba(libroActivo);
		crearMaterialPrueba(libroInactivo);
		crearEjemplarPrueba(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(libroInactivo.getID()); // Material inactivo
		tEjemplar.setEstado("Bueno");
		
		// WHEN
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		// THEN
		assertTrue(!resultado);
	}
	
	void crearMaterialPrueba(BOMaterial material) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(material);
		em.getTransaction().commit();
		em.close();
	}
	
	void crearEjemplarPrueba(BOEjemplar ejemplar) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(ejemplar);
		em.getTransaction().commit();
		em.close();
	}
	
	BOEjemplar buscarEjemplarPorId(int id) {
		EntityManager em = emf.createEntityManager();
		BOEjemplar ejemplar = null;
		em.getTransaction().begin();
		ejemplar = em.find(BOEjemplar.class, id);
		em.getTransaction().commit();
		em.close();
		return ejemplar;
	}

}
