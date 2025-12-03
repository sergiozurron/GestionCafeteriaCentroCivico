package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.negocio.materialJPA.TLibro;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.io.Serializable;

@Entity
@NamedQueries({})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BOAdulto extends BOSocio implements Serializable {

    private static final long serialVersionUID = 0;

    private Boolean miembroPleno;
    public BOAdulto(TAdulto adulto) {
        super(adulto);
        this.miembroPleno=adulto.getMiembroPleno();
    }

    public BOAdulto() {}

    public Boolean getMiembroPleno() {
        return miembroPleno;
    }

    public void setMiembroPleno(Boolean miembroPleno) {
        this.miembroPleno = miembroPleno;
    }
}
