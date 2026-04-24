package com.grupoms.app.negocio.prestamoJPA;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grupoms.app.integracion.factoria.EntityManagerSingleton;
import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.socioJPA.BOAdulto;
import com.grupoms.app.negocio.socioJPA.BOInfantil;
import com.grupoms.app.negocio.socioJPA.BOSocio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PrestamoSAImpTest {

	PrestamoSAImp prestamoSA = new PrestamoSAImp();
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("CentroCivicoJPA");
	
	@BeforeEach
	void setUp() {
		EntityManagerSingleton.setEMF(emf);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM BOPrestamo").executeUpdate();
		em.createQuery("DELETE FROM BOClase").executeUpdate();
		em.createQuery("DELETE FROM BOEjemplar").executeUpdate();
		em.createQuery("DELETE FROM BOMaterial").executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	void altaPrestamo_DeberiaDarDeAltaUnPrestamo() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(socio.getId());
		prestamo.setIdEjemplar(ejemplar.getId());
		
		// When
		Boolean resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(resultado);
		
		BOPrestamo prestamoPersistido = findPrestamoById(new PrestamoId(socio.getId(), ejemplar.getId(), new Date()));
		assertNotNull(prestamoPersistido);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverFalse_CuandoElSocioNoExiste() {
		// Given
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(-1); // Socio no existente
		prestamo.setIdEjemplar(ejemplar.getId());
		
		// When
		Boolean resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverFalse_CuandoElSocioNoEstaActivo() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(false); // No activo
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(socio.getId());
		prestamo.setIdEjemplar(ejemplar.getId());
		
		// When
		Boolean resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverFalse_CuandoElEjemplarNoExiste() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(socio.getId());
		prestamo.setIdEjemplar(-1); // Ejemplar no existente
		
		// When
		Boolean resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void altaEjemplar_DeberiaDevolverFalse_CuandoElEjemplarNoEstaActivo() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(false); // No activo
		persist(ejemplar);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(socio.getId());
		prestamo.setIdEjemplar(ejemplar.getId());
		
		// When
		Boolean resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverFalse_CuandoElEjemplarNoEstaDisponible() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("PRESTADO"); // No disponible
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(socio.getId());
		prestamo.setIdEjemplar(ejemplar.getId());
		
		// When
		Boolean resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void devolverPrestamo_DeberiaDevolverUnPrestamo() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		BOPrestamo prestamo = new BOPrestamo();
		prestamo.setSocio(socio);
		prestamo.setEjemplar(ejemplar);
		prestamo.setFechaInicial(new java.util.Date());
		prestamo.setFechaMaxima(new java.util.Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)); // Fecha máxima en el futuro
		persist(prestamo);
		
		// When
		Boolean resultado = prestamoSA.devolverPrestamo(new PrestamoId(socio.getId(), ejemplar.getId(), prestamo.getFechaInicial()));
		
		// Then
		assertTrue(resultado);
		
		BOPrestamo prestamoPersistido = findPrestamoById(new PrestamoId(socio.getId(), ejemplar.getId(), prestamo.getFechaInicial()));
		assertNotNull(prestamoPersistido);
		assertTrue(prestamoPersistido.getFechaDevuelto() != null);
	}
	
	@Test
	void devolverPrestamo_DeberiaDevolverFalse_CuandoElPrestamoNoExiste() {
		// When
		Boolean resultado = prestamoSA.devolverPrestamo(new PrestamoId()); // ID no existente
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void devolverPrestamo_DeberiaDevolverFalse_CuandoElPrestamoYaEstaDevuelto() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		BOPrestamo prestamo = new BOPrestamo();
		prestamo.setSocio(socio);
		prestamo.setEjemplar(ejemplar);
		prestamo.setFechaInicial(new java.util.Date());
		prestamo.setFechaMaxima(new java.util.Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)); // Fecha máxima en el futuro
		prestamo.setFechaDevuelto(new java.util.Date()); // Ya devuelto
		persist(prestamo);
		
		// When
		Boolean resultado = prestamoSA.devolverPrestamo(new PrestamoId(socio.getId(), ejemplar.getId(), prestamo.getFechaInicial()));
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void devolverPrestamo_DeberiaAplicarMulta_CuandoSeDevuelveTarde() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		BOPrestamo prestamo = new BOPrestamo();
		prestamo.setSocio(socio);
		prestamo.setEjemplar(ejemplar);
		prestamo.setFechaInicial(new java.util.Date(System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000)); // Hace 10 días
		prestamo.setFechaMaxima(new java.util.Date(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000)); // Hace 5 días
		persist(prestamo);
		
		// When
		Boolean resultado = prestamoSA.devolverPrestamo(new PrestamoId(socio.getId(), ejemplar.getId(), prestamo.getFechaInicial()));
		
		// Then
		assertTrue(resultado);
		BOPrestamo prestamoPersistido = findPrestamoById(new PrestamoId(socio.getId(), ejemplar.getId(), prestamo.getFechaInicial()));
		assertNotNull(prestamoPersistido);
		assertTrue(prestamoPersistido.getPrecioMulta() > 0);
	}
	
	@Test
	void modificarPrestamo_DeberiaModificarPrestamo() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		BOPrestamo prestamo = new BOPrestamo();	
		prestamo.setSocio(socio);
		prestamo.setEjemplar(ejemplar);
		persist(prestamo);
		
		TPrestamo prestamoModificado = new TPrestamo();
		prestamoModificado.setIdSocio(socio.getId());
		prestamoModificado.setIdEjemplar(ejemplar.getId());
		prestamoModificado.setFechaMaxima(new java.util.Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)); // Fecha máxima en el futuro
		
		// When
		Boolean resultado = prestamoSA.modificarPrestamo(prestamoModificado);
		
		// Then
		assertTrue(resultado);
		
		BOPrestamo prestamoPersistido = findPrestamoById(new PrestamoId(socio.getId(), ejemplar.getId(), prestamo.getFechaInicial()));
		assertNotNull(prestamoPersistido);
		assertTrue(prestamoPersistido.getFechaMaxima().after(new java.util.Date()));
	}
	
	@Test
	void modificarPrestamo_DeberiaDevolverFalse_CuandoElPrestamoNoExiste() {
		// Given
		TPrestamo prestamoModificado = new TPrestamo();
		prestamoModificado.setIdSocio(-1); // Socio no existente
		prestamoModificado.setIdEjemplar(-1); // Ejemplar no existente
		prestamoModificado.setFechaMaxima(new java.util.Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)); // Fecha máxima en el futuro
		
		// When
		Boolean resultado = prestamoSA.modificarPrestamo(prestamoModificado);
		
		// Then
		assertTrue(!resultado);
	}
	
	@Test
	void mostrarPrestamo_DeberiaMostrarPrestamo() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		BOPrestamo prestamo = new BOPrestamo();	
		prestamo.setSocio(socio);
		prestamo.setEjemplar(ejemplar);
		persist(prestamo);
		
		// When
		TPrestamo resultado = prestamoSA.mostrarPrestamo(new PrestamoId(socio.getId(), ejemplar.getId(), prestamo.getFechaInicial()));
		
		// Then
		assertNotNull(resultado);
		assertTrue(resultado.getIdSocio() == socio.getId());
		assertTrue(resultado.getIdEjemplar() == ejemplar.getId());
	}
	
	@Test
	void listarPrestamos_DeberiaListarPrestamos() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		BOPrestamo prestamo1 = new BOPrestamo();	
		prestamo1.setSocio(socio);
		prestamo1.setEjemplar(ejemplar);
		persist(prestamo1);
		
		BOPrestamo prestamo2 = new BOPrestamo();	
		prestamo2.setSocio(socio);
		prestamo2.setEjemplar(ejemplar);
		persist(prestamo2);
		
		// When
		java.util.List<TPrestamo> resultado = prestamoSA.listarPrestamo();
		
		// Then
		assertNotNull(resultado);
		assertTrue(resultado.size() >= 2); // Al menos los dos préstamos creados
	}
	
	void persist(Object entity) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	BOPrestamo findPrestamoById(PrestamoId id) {
		EntityManager em = emf.createEntityManager();
		BOPrestamo prestamo = em.find(BOPrestamo.class, id);
		em.close();
		return prestamo;
	}
	
}
