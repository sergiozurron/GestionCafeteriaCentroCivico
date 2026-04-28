package com.grupoms.app.negocio.assembler;

import static org.junit.jupiter.api.Assertions.*;

import com.grupoms.app.negocio.socioJPA.*;

import org.junit.jupiter.api.Test;

public class SocioAssemblerTest {

    @Test
    public void testEntityToTransfer() {
        BOSocio bo = new BOAdulto();
        bo.setId(1);
        bo.setNombreYapellido("Test Socio");
        bo.setDni("12345678A");
        bo.setTipoSocio(0);
        bo.setCuota(50);
        bo.setActivo(true);
    }

    @Test
    public void testEntityToTransfer_CamposNull() {
        BOSocio bo = new BOAdulto();
    }

    @Test
    public void testAdultoToDTO() {
        BOAdulto bo = new BOAdulto();
        bo.setId(2);
        bo.setNombreYapellido("Ana Gómez");
        bo.setDni("22222222B");
        bo.setTipoSocio(0);
        bo.setCuota(40);
        bo.setActivo(true);
        bo.setMiembroPleno(true);

        TAdulto dto = AdultoAssembler.toDTO(bo);
        assertEquals(2, dto.getId());
        assertEquals("Ana Gómez", dto.getNombreYapellido());
        assertEquals("22222222B", dto.getDni());
        assertEquals(40, dto.getCuota());
        assertTrue(dto.getActivo());
        assertTrue(dto.getMiembroPleno());
    }

    @Test
    public void testAdultoToBO() {
        TAdulto dto = new TAdulto("Pedro Test", "33333333C", 0, 60, false);
        dto.setId(3);
        dto.setActivo(true);

        BOAdulto bo = AdultoAssembler.toBO(dto);
        assertEquals(3, bo.getId());
        assertEquals("Pedro Test", bo.getNombreYapellido());
        assertEquals("33333333C", bo.getDni());
        assertEquals(60, bo.getCuota());
        assertTrue(bo.getActivo());
        assertFalse(bo.getMiembroPleno());
    }

    @Test
    public void testAdultoEntityToDTO_ConConstructorTSocio() {
        TAdulto t = new TAdulto("Ana Gomez", "11111111Z", 0, 30, true);
        BOAdulto bo = new BOAdulto(t);

        TAdulto dto = AdultoAssembler.toDTO(bo);
        assertNotNull(dto);
        assertEquals("Ana Gomez", dto.getNombreYapellido());
        assertTrue(dto.getMiembroPleno());
    }

    @Test
    public void testAdultoIdempotencia() {
        TAdulto original = new TAdulto("Ida Vuelta", "44444444D", 0, 55, true);
        original.setId(7);
        original.setActivo(true);

        BOAdulto bo = AdultoAssembler.toBO(original);
        TAdulto resultado = AdultoAssembler.toDTO(bo);

        assertEquals(original.getId(), resultado.getId());
        assertEquals(original.getNombreYapellido(), resultado.getNombreYapellido());
        assertEquals(original.getDni(), resultado.getDni());
        assertEquals(original.getCuota(), resultado.getCuota());
        assertEquals(original.getMiembroPleno(), resultado.getMiembroPleno());
    }

    @Test
    public void testInfantilToDTO() {
        BOInfantil bo = new BOInfantil();
        bo.setId(4);
        bo.setNombreYapellido("Lucía Niña");
        bo.setDni("55555555E");
        bo.setTipoSocio(1);
        bo.setCuota(20);
        bo.setActivo(true);
        bo.setEdad(10);
        bo.setReduccion(0.20);

        TInfantil dto = InfantilAssembler.toDTO(bo);
        assertEquals(4, dto.getId());
        assertEquals("Lucía Niña", dto.getNombreYapellido());
        assertEquals("55555555E", dto.getDni());
        assertEquals(20, dto.getCuota());
        assertTrue(dto.getActivo());
        assertEquals(10, dto.getEdad());
    }

    @Test
    public void testInfantilToBO() {
        TInfantil dto = new TInfantil("Carlos Niño", "66666666F", 1, 15, 0.20, 8);
        dto.setId(6);
        dto.setActivo(true);

        BOInfantil bo = InfantilAssembler.toBO(dto);
        assertEquals(6, bo.getId());
        assertEquals("Carlos Niño", bo.getNombreYapellido());
        assertEquals("66666666F", bo.getDni());
        assertEquals(15, bo.getCuota());
        assertTrue(bo.getActivo());
        assertEquals(8, bo.getEdad());
    }

    @Test
    public void testInfantilIdempotencia() {
        TInfantil original = new TInfantil("Infantil Ida", "77777777G", 1, 25, 0.20, 12);
        original.setId(8);
        original.setActivo(true);

        BOInfantil bo = InfantilAssembler.toBO(original);
        TInfantil resultado = InfantilAssembler.toDTO(bo);

        assertEquals(original.getId(), resultado.getId());
        assertEquals(original.getNombreYapellido(), resultado.getNombreYapellido());
        assertEquals(original.getDni(), resultado.getDni());
        assertEquals(original.getCuota(), resultado.getCuota());
        assertEquals(original.getEdad(), resultado.getEdad());
    }
}
