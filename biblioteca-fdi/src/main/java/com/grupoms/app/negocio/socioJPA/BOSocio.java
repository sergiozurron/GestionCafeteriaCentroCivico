package com.grupoms.app.negocio.socioJPA;

import com.grupoms.app.negocio.PromocionJPA.BOPromocion;
import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "com.grupoms.app.negocio.socioJPA.BOSocio.findByName", query = "SELECT s FROM BOSocio s WHERE s.nombreYapellido = :nombre"),
		@NamedQuery(name = "com.grupoms.app.negocio.socioJPA.BOSocio.findByType", query = "SELECT s FROM BOSocio s WHERE s.tipoSocio = :tipo"),
		@NamedQuery(name = "com.grupoms.app.negocio.socioJPA.BOSocio.findAll", query = "SELECT s FROM BOSocio s"),
		@NamedQuery(name = "com.grupoms.app.negocio.socioJPA.BOSocio.findByPromocion", query = "SELECT s FROM BOSocio s JOIN s.promociones p WHERE p.id = :idPromocion") })
public class BOSocio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String nombreYapellido;
	protected String dni;
	protected int tipoSocio;
	protected Integer cuota;
	protected Boolean activo;

	@Version
	private int version;

	@OneToMany(mappedBy = "socio")
	private List<BOPrestamo> prestamos;

	@ManyToMany
	private List<BOPromocion> promociones;

	public BOSocio() {
	}

	public BOSocio(TSocio socio) {
		this.nombreYapellido = socio.getNombreYapellido();
		this.dni = socio.getDni();
		this.tipoSocio = socio.getTipoSocio();
		this.cuota = socio.getCuota();
		this.activo = socio.getActivo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreYapellido() {
		return nombreYapellido;
	}

	public void setNombreYapellido(String nombreYapellido) {
		this.nombreYapellido = nombreYapellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getTipoSocio() {
		return tipoSocio;
	}

	public void setTipoSocio(int tipoSocio) {
		this.tipoSocio = tipoSocio;
	}

	public Integer getCuota() {
		return cuota;
	}

	public void setCuota(Integer cuota) {
		this.cuota = cuota;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<BOPrestamo> getPrestamos() {
		if (prestamos == null) {
			prestamos = new ArrayList<>();
		}
		return prestamos;
	}

	public void setPrestamos(List<BOPrestamo> prestamos) {
		this.prestamos = prestamos;
	}

	public List<BOPromocion> getPromocion() {
		if (promociones == null) {
			promociones = new ArrayList<>();
		}
		return promociones;
	}

	public void setPromocion(List<BOPromocion> promocions) {
		this.promociones = promocions;
	}

	public void anyadirPromocion(BOPromocion promocion) {
		if (this.promociones == null) {
			this.promociones = new ArrayList<>();
		}
		this.promociones.add(promocion);
	}

	public void eliminarPromocion(BOPromocion promocion) {
		if (this.promociones != null) {
			this.promociones.remove(promocion);
		}
	}
}