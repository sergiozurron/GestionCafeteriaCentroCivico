package com.grupoms.app.integracion.Transaction;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class TransactionMySQL implements Transaction {

    private Connection conexion;

    public TransactionMySQL() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config/dbconfig.properties"));

        String host = prop.getProperty("host");
        String port = prop.getProperty("port");
        String db = prop.getProperty("dbname");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user
                + "&password=" + password + "&useSSL=false&serverTimezone=Europe/Madrid";

        conexion = DriverManager.getConnection(url);
    }

    @Override
    public void start() throws Exception {
        conexion.setAutoCommit(false);
    }

    @Override
    public void commit() throws Exception {
        conexion.commit();
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
