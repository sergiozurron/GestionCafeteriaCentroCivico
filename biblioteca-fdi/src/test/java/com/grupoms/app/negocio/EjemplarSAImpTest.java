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

}
