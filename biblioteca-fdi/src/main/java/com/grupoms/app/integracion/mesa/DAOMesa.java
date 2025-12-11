package com.grupoms.app.integracion.mesa;

import java.util.List;

import com.grupoms.app.negocio.mesa.TMesa;

public interface DAOMesa {
	Integer altaMesa(TMesa mesa);

	TMesa mostrarMesa(Integer id);

	Boolean modificarMesa(TMesa mesa);

	List<TMesa> mostrarListaMesa();

	Boolean bajaMesa(TMesa mesa);
}
