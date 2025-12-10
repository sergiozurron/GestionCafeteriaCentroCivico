package com.grupoms.app.negocio.socioJPA;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQueries({})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOInfantil extends BOSocio implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double reduccion;
    private int edad;

    public BOInfantil(TInfantil infantil) {
        super(infantil);
        this.edad= infantil.getEdad();
        this.reduccion= infantil.getReduccion();
    }

    public BOInfantil() {}

    public int getEdad() {
        return edad;
    }

    public Double getReduccion() {
        return reduccion;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setReduccion(Double reduccion){
        this.reduccion=reduccion;
    }


}
