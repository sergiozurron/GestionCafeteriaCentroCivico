package com.grupoms.app.negocio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.grupoms.app.EntityManagerProvider;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.EjemplarJPA.EjemplarSAImp;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;
import com.grupoms.app.negocio.materialJPA.BOLibro;

import jakarta.persistence.EntityManager;

public class EjemplarSAImpTest {

	EjemplarSAImp ejemplarSA = new EjemplarSAImp();
	EntityManager em = EntityManagerProvider.getEntityManager();
	
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
		
		em.getTransaction().begin();
		em.persist(libro);
		em.getTransaction().commit();
		
		TEjemplar ejemplar = new TEjemplar();
		ejemplar.setIdMaterial(libro.getID());
		ejemplar.setEstado("Nuevo");
		
		// WHEN
		int idEjemplar = ejemplarSA.altaEjemplar(ejemplar);
		
		// THEN
		assertTrue(idEjemplar > 0);
		
		em.getTransaction().begin();
		BOEjemplar boEjemplar = em.find(BOEjemplar.class, idEjemplar);
		em.getTransaction().commit();
		
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
		
		em.getTransaction().begin();
		em.persist(libro);
		em.getTransaction().commit();
		
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
		
		em.getTransaction().begin();
		em.persist(libro);
		em.persist(ejemplar);
		em.getTransaction().commit();
		
		// WHEN
		boolean resultado = ejemplarSA.bajaEjemplar(ejemplar.getId());
		
		// THEN
		assertTrue(resultado);
		
		em.getTransaction().begin();
		ejemplar = em.find(BOEjemplar.class, ejemplar.getId());
		em.getTransaction().commit();
		
		assertNotNull(ejemplar);
		assertTrue(!ejemplar.getActivo());
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
		
		em.getTransaction().begin();
		em.persist(libro);
		em.persist(ejemplar);
		em.getTransaction().commit();
		
		// WHEN
		boolean resultado = ejemplarSA.bajaEjemplar(ejemplar.getId());
		
		// THEN
		assertTrue(!resultado);
	}

}
