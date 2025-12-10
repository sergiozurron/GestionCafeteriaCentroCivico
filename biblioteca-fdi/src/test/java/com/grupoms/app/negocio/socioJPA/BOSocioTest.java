package com.grupoms.app.negocio.socioJPA;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;

import org.junit.jupiter.api.Test;

public class BOSocioTest {

    @Test
    public void anyadirYEliminarPromocion() {
        BOSocio s = new BOSocio();
        BOPromocion p = new BOPromocion();
        p.setID(1);
        // Añadir
        s.anyadirPromocion(p);
        List<BOPromocion> promos = s.getPromocion();
        assertNotNull(promos);
        assertEquals(1, promos.size());
        // Eliminar
        s.eliminarPromocion(p);
        assertEquals(0, s.getPromocion().size());
    }
}
