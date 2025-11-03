package com.grupoms.app.negocio.mesa;

import java.util.List;

public interface SAMesa {
	int altaMesa (TMesa mesa);
	void bajaMesa(Integer id);
	void modificarMesa(TMesa mesa);
	TMesa mostrarMesa(Integer id);
	public List<TMesa> mostrarMesas();

}
