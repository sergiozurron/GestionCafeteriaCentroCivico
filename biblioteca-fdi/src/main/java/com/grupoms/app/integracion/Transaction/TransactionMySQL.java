package com.grupoms.app.integracion.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TransactionMySQL implements Transaction {

	private Connection conexion;

	public TransactionMySQL() throws SQLException {
		conexion = DriverManager.getConnection(System.getenv("MS_DB_URL"), System.getenv("MS_DB_USER"),
				System.getenv("MS_DB_PASSWORD"));
	}

	@Override
	public void start() throws Exception {
		conexion.setAutoCommit(false);
	}

	@Override
	public void commit() throws Exception {
		conexion.commit();
		conexion.close();
		TransactionManager.getInstance().deleteTransaction();
	}

	@Override
	public void rollback() throws Exception {
		conexion.rollback();
	}

	@Override
	public Connection getConnection() {
		return conexion;
	}

	@Override
	public Object getResource() {
		return conexion;
	}
}
