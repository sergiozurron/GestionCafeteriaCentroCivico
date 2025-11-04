package com.grupoms.app.integracion.mesa;

import java.util.List;

import com.grupoms.app.negocio.mesa.TMesa;

public interface DAOMesa {
	Integer crea(TMesa mesa);
	TMesa buscaPorNumero(Integer numero);
	TMesa buscarPorId(Integer id);
	void eliminaTodos();
	void update(TMesa mesa);
	List<TMesa> mostrarTodos();
}
