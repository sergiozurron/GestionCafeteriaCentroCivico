package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSAImp;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.materialJPA.BOLibro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EjemplarSAImpTest {

	EjemplarSAImp ejemplarSA = new EjemplarSAImp();
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("CentroCivicoJPA-test");
	EntityManager em;
	
	@BeforeEach
	void setUp() {
		EntityManagerSingleton.setEMF(emf);
		em = emf.createEntityManager();
	}
	
	@Test
	void altaEjemplar_DeberiaCrearEjemplarYDevolverId() {
		
		BOLibro libro = new BOLibro();
		libro.setISBN(123456);
		libro.setNombre("Titulo de Prueba");
		libro.setAutor("Autor de Prueba");
		libro.setEditorial("Editorial de Prueba");
		libro.setTipoMaterial(1);
		libro.setActivo(true);
		
		persist(libro);
		
		TEjemplar ejemplar = new TEjemplar();
		ejemplar.setIdMaterial(libro.getID());
		ejemplar.setEstado("Nuevo");
		
		
		int idEjemplar = ejemplarSA.altaEjemplar(ejemplar);
		
		
		assertTrue(idEjemplar > 0);
		
		BOEjemplar boEjemplar = buscarEjemplarPorId(idEjemplar);
		
		assertNotNull(boEjemplar);
	}
	
	@Test
	void altaEjemplar_DeberiaDevolverMenosUno_CuandoMaterialNoExiste() {
		
		TEjemplar ejemplar = new TEjemplar();
		ejemplar.setIdMaterial(-1); 
		ejemplar.setEstado("Nuevo");
		
		
		int idEjemplar = ejemplarSA.altaEjemplar(ejemplar);
		
		
		assertTrue(idEjemplar == -1);
	}
	
	@Test
	void altaEjemplar_DeberiaDevolverMenosUno_CuandoMaterialNoActivo() {
		
		BOLibro libro = new BOLibro();
		libro.setISBN(111111);
		libro.setNombre("Titulo Inactivo");
		libro.setAutor("Autor Inactivo");
		libro.setEditorial("Editorial Inactivo");
		libro.setTipoMaterial(1);
		libro.setActivo(false);
		
		persist(libro);
		
		TEjemplar ejemplar = new TEjemplar();
		ejemplar.setIdMaterial(libro.getID());
		ejemplar.setEstado("Nuevo");
		
		
		int idEjemplar = ejemplarSA.altaEjemplar(ejemplar);
		
		
		assertTrue(idEjemplar == -1);
	}
	
	@Test
	void bajaEjemplar_DeberiaEliminarEjemplarYDevolverTrue() {
		
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
		
		persist(libro);
		persist(ejemplar);
		
		boolean resultado = ejemplarSA.bajaEjemplar(ejemplar.getId());
		
		
		assertTrue(resultado);
		
		BOEjemplar ejemplarDesactivado = buscarEjemplarPorId(ejemplar.getId());
		
		assertNotNull(ejemplarDesactivado);
		assertTrue(!ejemplarDesactivado.getActivo());
	}
	
	@Test
	void bajaEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoExiste() {
		
		boolean resultado = ejemplarSA.bajaEjemplar(-1); 
		
		
		assertTrue(!resultado);
	}
	
	@Test
	void bajaEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoActivo() {
		
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
		
		persist(libro);
		persist(ejemplar);
		
		
		boolean resultado = ejemplarSA.bajaEjemplar(ejemplar.getId());
		
		
		assertTrue(!resultado);
	}
	
	@Test
	void bajaEjemplar_DeberiaDevolverFalse_CuandoEjemplarTieneClasesAsociadas() {
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("Bueno");
		ejemplar.setActivo(true);
		
		BOClase clase = new BOClase();
		clase.setTipo("Clase de Prueba");
		clase.setFechaInicio(new Date());
		clase.setDuracion(2);
		clase.setActivo(true);
		clase.anyadirEjemplar(ejemplar);
		
		persist(clase);
		
		boolean resultado = ejemplarSA.bajaEjemplar(ejemplar.getId());
		
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaModificarEjemplarYDevolverTrue() {
		
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
		
		persist(libro);
		persist(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(libro.getID());
		tEjemplar.setEstado("Excelente");
		
		
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		
		assertTrue(resultado);
		
		BOEjemplar ejemplarModificado = buscarEjemplarPorId(ejemplar.getId());
		
		assertNotNull(ejemplarModificado);
		assertTrue(ejemplarModificado.getEstado().equals("Excelente"));
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoExiste() {
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(-1); 
		tEjemplar.setIdMaterial(1);
		tEjemplar.setEstado("Regular");
		
		
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoEjemplarNoActivo() {
		
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
		
		persist(libro);
		persist(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(libro.getID());
		tEjemplar.setEstado("Regular");
		
		
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoMaterialNoExiste() {
		
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
		
		persist(libro);
		persist(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(-1); 
		tEjemplar.setEstado("Bueno");
		
		
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		
		assertTrue(!resultado);
	}
	
	@Test
	void modificarEjemplar_DeberiaDevolverFalse_CuandoMaterialNoActivo() {
		
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
		
		persist(libroActivo);
		persist(libroInactivo);
		persist(ejemplar);
		
		TEjemplar tEjemplar = new TEjemplar();
		tEjemplar.setId(ejemplar.getId());
		tEjemplar.setIdMaterial(libroInactivo.getID()); 
		tEjemplar.setEstado("Bueno");
		
		
		boolean resultado = ejemplarSA.modificarEjemplar(tEjemplar);
		
		
		assertTrue(!resultado);
	}
	
	void persist(Object entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	BOEjemplar buscarEjemplarPorId(int id) {
		BOEjemplar ejemplar = null;
		em.getTransaction().begin();
		ejemplar = em.find(BOEjemplar.class, id);
		em.getTransaction().commit();
		em.close();
		return ejemplar;
	}

}
