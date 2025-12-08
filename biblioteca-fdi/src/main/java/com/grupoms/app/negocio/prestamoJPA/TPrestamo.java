package com.grupoms.app.negocio.prestamoJPA;

import java.time.LocalDate;

public class TPrestamo {
    private Integer id;
    private Integer idSocio;
    private Integer idEjemplar;
    private Boolean multa=false;
    private Integer precioMulta=0;
    private LocalDate fechaPrevista;
    private LocalDate fechaDevolucion;

    public TPrestamo(Integer idSocio, Integer idEjemplar, Boolean multa, Integer precioMulta, LocalDate fechaPrevista, LocalDate fechaDevolucion) {
        this.idSocio = idSocio;
        this.idEjemplar = idEjemplar;
        this.multa = multa;
        this.precioMulta = precioMulta;
        this.fechaPrevista = fechaPrevista;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Integer getPrecioMulta() {
        return precioMulta;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public Integer getIdEjemplar() {
        return idEjemplar;
    }

    public Boolean getMulta() {
        return multa;
    }

    public LocalDate getFechaPrevista() {
        return fechaPrevista;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public void setIdEjemplar(Integer idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public void setPrecioMulta(Integer precioMulta) {
        this.precioMulta = precioMulta;
    }

    public void setMulta(Boolean multa) {
        this.multa = multa;
    }

    public void setFechaPrevista(LocalDate fechaPrevista) {
        this.fechaPrevista = fechaPrevista;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
