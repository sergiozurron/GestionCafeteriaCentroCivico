package com.grupoms.app.negocio.assembler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.PromocionJPA.TPromocion;

public class PromocionAssemblerTest {

    @Test
    void testToDTO() {
        BOPromocion bo = new BOPromocion();
        bo.setID(1);
        bo.setDescuento(15.0);
        bo.setTipo("Verano");
        bo.setActivo(true);

        TPromocion dto = PromocionAssembler.toDTO(bo);
        assertEquals(1, dto.getId());
        assertEquals(15.0, dto.getDescuento());
        assertEquals("Verano", dto.getTipo());
        assertTrue(dto.getActivo());
    }

    @Test
    void testToBO() {
        TPromocion dto = new TPromocion(20.0, "Invierno");
        dto.setId(2);
        dto.setActivo(true);

        BOPromocion bo = PromocionAssembler.toBO(dto);
        assertEquals(2, bo.getID());
        assertEquals(20.0, bo.getDescuento());
        assertEquals("Invierno", bo.getTipo());
        assertTrue(bo.getActivo());
    }

    @Test
    void testIdempotencia() {
        BOPromocion original = new BOPromocion();
        original.setID(5);
        original.setDescuento(10.0);
        original.setTipo("General");
        original.setActivo(true);

        TPromocion dto = PromocionAssembler.toDTO(original);
        BOPromocion resultado = PromocionAssembler.toBO(dto);

        assertEquals(original.getID(), resultado.getID());
        assertEquals(original.getDescuento(), resultado.getDescuento());
        assertEquals(original.getTipo(), resultado.getTipo());
        assertEquals(original.getActivo(), resultado.getActivo());
    }

    @Test
    void testToDTO_Inactivo() {
        BOPromocion bo = new BOPromocion();
        bo.setID(3);
        bo.setActivo(false);

        TPromocion dto = PromocionAssembler.toDTO(bo);
        assertFalse(dto.getActivo());
    }
}

