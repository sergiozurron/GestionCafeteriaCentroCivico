package com.grupoms.app.negocio.mesa;

import java.util.List;

public interface SAMesa {
	Integer altaMesa(TMesa mesa);

	Boolean bajaMesa(TMesa mesa);

	Boolean modificarMesa(TMesa mesa);

	TMesa mostrarMesa(Integer id);

	public List<TMesa> mostrarListaMesa();

}
