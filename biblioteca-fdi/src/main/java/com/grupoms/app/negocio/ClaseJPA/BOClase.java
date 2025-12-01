package com.grupoms.app.negocio.ClaseJPA;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Version;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
    @NamedQuery(
        name = "com.grupoms.app.negocio.claseJPA.BOClase.findByTipo",
        query = "SELECT c FROM BOClase c WHERE c.tipo = :tipo"
    ),
    @NamedQuery(
        name = "com.grupoms.app.negocio.claseJPA.BOClase.findAll",
        query = "SELECT c FROM BOClase c"
    ),
    @NamedQuery(
            name = "com.grupoms.app.negocio.claseJPA.BOClase.findBySala",
            query = "SELECT c FROM BOSala s JOIN s.clases c WHERE s.id = :idSala AND c.activo = true"
        )
})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOClase implements Serializable {

    private static final long serialVersionUID = 0L;

    // ---- 1) Fields ----

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String tipo;

    protected Date fechaInicio;

    protected Integer duracion;

    protected Boolean activo;

    @Version
    private int version;

    public BOClase() { }

    public BOClase(TClase clase) {
        this.tipo = clase.getTipo();
        this.fechaInicio = clase.getFechaInicio();
        this.duracion = clase.getDuracion();
        this.activo = clase.getActivo();
    }


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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
