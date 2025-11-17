package com.grupoms.app.integracion.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;

public class ejemplaresSociosPlenoPorFecha implements Query{

	private static final String consulta;
	@Override
	public Object execute(Object ob) {
		Integer id = (Integer) ob;
		List<TEjemplar> listaEjemplares = new ArrayList<TEjemplar>();ç
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement(consulta);
			ResultSet r = s.executeQuery();
			while(r.next()) {
				TEjemplar ejemplar = new TEjemplar();
				// hacer todos los sets de ejemplar
				
			}
			
		}
	}

}
