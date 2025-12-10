package com.grupoms.app.negocio.ClaseJPA;

import java.util.Date;

/**
 * Transfer object for Clase.
 * 1) Stores basic data for a class.
 * 2) Used to move data between layers without JPA annotations.
 */
public class TClase {

    // ---- 1) Fields ----
    protected Integer id;
    protected String tipo;
    protected Date fechaInicio;
    protected Integer duracion;
    protected Boolean activo;
    protected Integer idSala;

    // ---- 2) Getters & Setters ----

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

       public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }
}
