package com.grupoms.app.integracion.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.grupoms.app.integracion.DBConfig;

public class TransactionMySQL implements Transaction {

	private Connection conexion;

	public TransactionMySQL() {
		try {
            conexion = DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la conexión de la transacción", e);
        }	}

	@Override
	public void start() {
	    try {
	        conexion.setAutoCommit(false);
	    } catch (SQLException e) {
	        throw new RuntimeException("Error iniciando transacción", e);
	    }
	}

	@Override
	public void commit() {
	    try {
	        conexion.commit();
	    } catch (SQLException e) {
	        throw new RuntimeException("Error en commit de transacción", e);
	    } finally {
	        try {
	            conexion.close();
	        } catch (SQLException e) {
	            throw new RuntimeException("Error cerrando conexión tras commit", e);
	        }
	        TransactionManager.getInstance().deleteTransaction();
	    }
	}

	@Override
	public void rollback() {
	    try {
	        conexion.rollback();
	    } catch (SQLException e) {
	        throw new RuntimeException("Error en rollback de transacción", e);
	    } finally {
	        try {
	            conexion.close();
	        } catch (SQLException e) {
	            throw new RuntimeException("Error cerrando conexión tras rollback", e);
	        }
	        TransactionManager.getInstance().deleteTransaction();
	    }
	}

	@Override
	public Object getResource() {
		return conexion;
	}
}
