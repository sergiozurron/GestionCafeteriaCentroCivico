package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.negocio.materialJPA.TLibro;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.io.Serializable;

@Entity
@NamedQueries({})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOInfantil extends BOSocio implements Serializable {

    private static final long serialVersionUID = 0;

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
        int e=this.edad;
        if(e<=3){
            reduccion=0.50;
        }
        else if(e<=14){
            reduccion=0.20;
        }
        else if(e<=18){
            reduccion=0.10;
        }
        return reduccion;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setReduccion(Double reduccion){
        this.reduccion=reduccion;
    }


}
