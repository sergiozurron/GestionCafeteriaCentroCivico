package com.grupoms.app.negocio.prestamoJPA;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		int resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(resultado > 0);
		
		BOPrestamo prestamoPersistido = findPrestamoById(resultado);
		assertNotNull(prestamoPersistido);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverMenosUno_CuandoElSocioNoExiste() {
		// Given
		BOEjemplar ejemplar = new BOEjemplar();
		ejemplar.setEstado("DISPONIBLE");
		ejemplar.setActivo(true);
		persist(ejemplar);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(-1); // Socio no existente
		prestamo.setIdEjemplar(ejemplar.getId());
		
		// When
		int resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(resultado < 0);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverMenosUno_CuandoElSocioNoEstaActivo() {
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
		int resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(resultado < 0);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverMenosUno_CuandoElEjemplarNoExiste() {
		// Given
		BOSocio socio = new BOSocio();
		socio.setActivo(true);
		persist(socio);
		
		TPrestamo prestamo = new TPrestamo();
		prestamo.setIdSocio(socio.getId());
		prestamo.setIdEjemplar(-1); // Ejemplar no existente
		
		// When
		int resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(resultado < 0);
	}
	
	@Test
	void altaEjemplar_DeberiaDevolverMenosUno_CuandoElEjemplarNoEstaActivo() {
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
		int resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(resultado < 0);
	}
	
	@Test
	void altaPrestamo_DeberiaDevolverMenosUno_CuandoElEjemplarNoEstaDisponible() {
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
		int resultado = prestamoSA.altaPrestamo(prestamo);
		
		// Then
		assertTrue(resultado < 0);
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
		int resultado = prestamoSA.devolverPrestamo(prestamo.getId());
		
		// Then
		assertTrue(resultado > 0);
		
		BOPrestamo prestamoPersistido = findPrestamoById(resultado);
		assertNotNull(prestamoPersistido);
		assertTrue(prestamoPersistido.getFechaDevuelto() != null);
	}
	
	@Test
	void devolverPrestamo_DeberiaDevolverMenosUno_CuandoElPrestamoNoExiste() {
		// When
		int resultado = prestamoSA.devolverPrestamo(-1); // ID no existente
		
		// Then
		assertTrue(resultado < 0);
	}
	
	@Test
	void devolverPrestamo_DeberiaDevolverMenosUno_CuandoElPrestamoYaEstaDevuelto() {
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
		
		TPrestamo tprestamo = new TPrestamo();
		tprestamo.setIdSocio(socio.getId());
		tprestamo.setIdEjemplar(ejemplar.getId());
		
		int idPrestamo = prestamoSA.altaPrestamo(tprestamo);
		
		// When
		int resultado = prestamoSA.devolverPrestamo(idPrestamo);
		
		// Then
		assertTrue(resultado < 0);
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
		int resultado = prestamoSA.devolverPrestamo(prestamo.getId());
		
		// Then
		assertTrue(resultado > 0);
		BOPrestamo prestamoPersistido = findPrestamoById(prestamo.getId());
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
		prestamoModificado.setId(prestamo.getId());
		prestamoModificado.setIdSocio(socio.getId());
		prestamoModificado.setIdEjemplar(ejemplar.getId());
		prestamoModificado.setFechaMaxima(new java.util.Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)); // Fecha máxima en el futuro
		
		// When
		int resultado = prestamoSA.modificarPrestamo(prestamoModificado);
		
		// Then
		assertTrue(resultado > 0);
		
		BOPrestamo prestamoPersistido = findPrestamoById(resultado);
		assertNotNull(prestamoPersistido);
		assertTrue(prestamoPersistido.getFechaMaxima().after(new java.util.Date()));
	}
	
	@Test
	void modificarPrestamo_DeberiaDevolverMenosUno_CuandoElPrestamoNoExiste() {
		// Given
		TPrestamo prestamoModificado = new TPrestamo();
		prestamoModificado.setIdSocio(-1); // Socio no existente
		prestamoModificado.setIdEjemplar(-1); // Ejemplar no existente
		prestamoModificado.setFechaMaxima(new java.util.Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)); // Fecha máxima en el futuro
		
		// When
		int resultado = prestamoSA.modificarPrestamo(prestamoModificado);
		
		// Then
		assertTrue(resultado < 0);
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
		TPrestamo resultado = prestamoSA.mostrarPrestamo(prestamo.getId());
		
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
	
	@Test
	void calcularPrecioPromocion_DeberiaAplicarDescuentoAdulto_CuandoSocioAdulto() {
		// Given
		BOPromocion promocion = new BOPromocion();
		promocion.setDescuento(20.0); // 20% de descuento
		promocion.setActivo(true);
		persist(promocion);
		
		BOAdulto socio = new BOAdulto();
		socio.setCuota(50);
		socio.setActivo(true);
		socio.anyadirPromocion(promocion);
		persist(socio);
		
		// When
		Double resultado = prestamoSA.calcularPrecioPromocion(new TCalculoPrecioPromocion(promocion.getID(), socio.getId()));
		
		// Then
		assertTrue(resultado == 40.0); // 20% de descuento sobre 50
	}
	
	@Test
	void calcularPrecioPromocion_DeberiaAplicarDescuentoInfantil_CuandoSocioInfantil() {
		// Given
		BOPromocion promocion = new BOPromocion();
		promocion.setDescuento(30.0); // 30% de descuento
		promocion.setActivo(true);
		persist(promocion);
		
		BOInfantil socio = new BOInfantil();
		socio.setCuota(40);
		socio.setReduccion(50.0);
		socio.setActivo(true);
		socio.anyadirPromocion(promocion);
		persist(socio);
		
		// When
		Double resultado = prestamoSA.calcularPrecioPromocion(new TCalculoPrecioPromocion(promocion.getID(), socio.getId()));
		
		// Then
		assertTrue(resultado == 14.0); // Descuento del 30% sobre 40, luego reducción del 50% sobre el resultado
	}
	
	@Test
	void calcularPrecioPromocion_DeberiaDevolverNulo_CuandoElSocioNoExiste() {
		// Given
		BOPromocion promocion = new BOPromocion();
		promocion.setDescuento(20.0); // 20% de descuento
		promocion.setActivo(true);
		persist(promocion);
		
		TCalculoPrecioPromocion calculo = new TCalculoPrecioPromocion(-1, promocion.getID()); // Socio no existente
		
		// When
		Double resultado = prestamoSA.calcularPrecioPromocion(calculo);
		
		// Then
		assertNull(resultado);
	}
	
	@Test
	void calcularPrecioPromocion_DeberiaDevolverNulo_CuandoElSocioNoEstaActivo() {
		// Given
		BOPromocion promocion = new BOPromocion();
		promocion.setDescuento(20.0); // 20% de descuento
		promocion.setActivo(true);
		persist(promocion);
		
		BOSocio socio = new BOSocio();
		socio.setActivo(false); // No activo
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
		
		TCalculoPrecioPromocion calculo = new TCalculoPrecioPromocion(prestamo.getId(), promocion.getID());
		
		// When
		Double resultado = prestamoSA.calcularPrecioPromocion(calculo);
		
		// Then
		assertNull(resultado);
	}
	
	@Test
	void calcularPrecioPromocion_DeberiaDevolverNulo_CuandoElSocioNoTieneLaPromocion() {
		// Given
		BOPromocion promocion = new BOPromocion();
		promocion.setDescuento(20.0); // 20% de descuento
		promocion.setActivo(true);
		persist(promocion);
		
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
		
		TCalculoPrecioPromocion calculo = new TCalculoPrecioPromocion(prestamo.getId(), promocion.getID());
		
		// When
		Double resultado = prestamoSA.calcularPrecioPromocion(calculo);
		
		// Then
		assertNull(resultado);
	}
	
	void persist(Object entity) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	BOPrestamo findPrestamoById(Integer id) {
		EntityManager em = emf.createEntityManager();
		BOPrestamo prestamo = em.find(BOPrestamo.class, id);
		em.close();
		return prestamo;
	}
	
}
