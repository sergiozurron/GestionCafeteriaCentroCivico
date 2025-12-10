package com.grupoms.app.negocio.ClaseJPA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.grupoms.app.negocio.EjemplarJPA.BOEjemplar;
import com.grupoms.app.negocio.salaJPA.BOSala;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Version;


@Entity
@NamedQueries({
	@NamedQuery(
	        name = "com.grupoms.app.negocio.claseJPA.BOClase.findByTipo",
	        query = "SELECT c FROM BOClase c WHERE c.tipo = :tipo"
	    ),
	@NamedQuery(
		    name = "com.grupoms.app.negocio.claseJPA.BOClase.findByTipoAndFecha",
		    query = "SELECT c FROM BOClase c WHERE c.tipo = :tipo AND c.fechaInicio = :fechaInicio"
		),
	    @NamedQuery(
	        name = "com.grupoms.app.negocio.claseJPA.BOClase.findAll",
	        query = "SELECT c FROM BOClase c"
	    ),
	    @NamedQuery(
	        name = "com.grupoms.app.negocio.claseJPA.BOClase.findBySala",
	        query = "SELECT c FROM BOClase c WHERE c.sala.id = :idSala AND c.activo = true"
	    )
	})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private BOSala sala;
    
    @ManyToMany
    private List<BOEjemplar> ejemplares;
    
    public BOClase() {
        this.ejemplares = new ArrayList<>();
    }

    public BOClase(TClase clase) {
        this.tipo = clase.getTipo();
        this.fechaInicio = clase.getFechaInicio();
        this.duracion = clase.getDuracion();
        this.activo = clase.getActivo();
        this.ejemplares = new ArrayList<>();
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
    
    public void anyadirEjemplar(BOEjemplar ejemplar) {
     if (this.ejemplares == null) this.ejemplares = new ArrayList<>();
     ejemplares.add(ejemplar);
    }
    
    public void eliminarEjemplar(BOEjemplar ejemplar) {
     if (this.ejemplares == null) return;
     ejemplares.remove(ejemplar);
    }
    
    public BOSala getSala() {
        return sala;
    }

    public void setSala(BOSala sala) {
        this.sala = sala;
    }

}
