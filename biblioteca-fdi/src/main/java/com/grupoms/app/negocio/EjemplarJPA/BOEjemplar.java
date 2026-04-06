package com.grupoms.app.negocio.EjemplarJPA;

import java.util.List;
import java.util.ArrayList;
import com.grupoms.app.negocio.ClaseJPA.BOClase;
import com.grupoms.app.negocio.materialJPA.BOMaterial;
import com.grupoms.app.negocio.prestamoJPA.BOPrestamo;
import jakarta.persistence.*;

@Entity
@NamedQuery(name = "com.grupoms.app.negocio.EjemplarJPA.BOEjemplar.findAllActivos", query = "SELECT e FROM BOEjemplar e WHERE e.activo = true")
@NamedQuery(name = "com.grupoms.app.negocio.EjemplarJPA.BOEjemplar.findByMaterialId", query = "SELECT e FROM BOEjemplar e WHERE e.material.id = :materialId AND e.activo = true")
@NamedQuery(name = "com.grupoms.app.negocio.EjemplarJPA.BOEjemplar.findByClaseId", query = "SELECT e FROM BOEjemplar e JOIN e.clases c WHERE c.id = :claseId AND e.activo = true")
@NamedQuery(name = "com.grupoms.app.negocio.EjemplarJPA.BOEjemplar.findBySocio", query = "SELECT e FROM BOEjemplar e JOIN e.prestamos p WHERE p.socio.id = :idSocio AND p.activo = true AND p.fechaDevuelto IS NULL")
@NamedQuery(
	    name = "BOEjemplar.findPrestadosPorAdultosPlenosEnFechas",
	    query = "SELECT DISTINCT e FROM BOEjemplar e " +
	            "JOIN e.prestamos p " +
	            "JOIN BOAdulto a ON a.id = p.socio.id " +
	            "WHERE a.miembroPleno = true " +
	            "AND p.fechaInicial BETWEEN :fechaInicio AND :fechaFin " + 
	            "AND e.activo = true"
)

public class BOEjemplar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String estado;
	private Boolean activo;

	@ManyToOne
	@JoinColumn(name = "material_id")
	private BOMaterial material;

	@ManyToMany(mappedBy = "ejemplares")
	private List<BOClase> clases;

	@Version
	private int version;

	@OneToMany(mappedBy = "ejemplar")
	private List<BOPrestamo> prestamos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public BOMaterial getMaterial() {
		return material;
	}

	public void setMaterial(BOMaterial material) {
		this.material = material;
	}

	public List<BOPrestamo> getPrestamos() {
		if (prestamos == null)
			prestamos = new ArrayList<>();
		return prestamos;
	}

	public void setPrestamos(List<BOPrestamo> prestamos) {
		this.prestamos = prestamos;
	}
}