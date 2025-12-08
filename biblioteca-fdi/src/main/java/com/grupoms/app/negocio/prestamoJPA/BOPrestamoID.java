package com.grupoms.app.negocio.prestamoJPA;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BOPrestamoID implements Serializable {
    private Integer socio;
    private Integer ejemplar;
}
