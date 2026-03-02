package com.grupoms.app.integracion.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.EjemplarJPA.TEjemplar;

public class ejemplaresSociosPlenoPorFecha implements Query {

    @Override
    public Object execute(Object ob) {
        Object[] params = (Object[]) ob;
        Boolean miembroPleno = (Boolean) params[0];
        java.util.Date fechaInicio = (java.util.Date) params[1];
        java.util.Date fechaFin = (java.util.Date) params[2];

        List<TEjemplar> ejemplares = new ArrayList<>();

        try {
            TransactionManager tm = TransactionManager.getInstance();
            Transaction t = tm.getTransaction();
            Connection c = (Connection) t.getResource();

            String consulta = "SELECT E.* FROM Ejemplares E " +
                              "JOIN Prestamos P ON E.id = P.id_ejemplar " +
                              "JOIN Socios S ON P.id_socio = S.id " +
                              "WHERE S.miembro_pleno = ? " +
                              "AND P.fecha_inicial >= ? AND P.fecha_inicial <= ?";

            PreparedStatement ps = c.prepareStatement(consulta);
            ps.setBoolean(1, miembroPleno);
            ps.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            ps.setDate(3, new java.sql.Date(fechaFin.getTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TEjemplar tEjemplar = new TEjemplar();
                tEjemplar.setId(rs.getInt("id"));
                tEjemplar.setEstado(rs.getString("estado"));
                tEjemplar.setActivo(rs.getBoolean("activo"));
                tEjemplar.setIdMaterial(rs.getInt("idMaterial"));
                // Añadir más campos de Ejemplar si los tienes
                ejemplares.add(tEjemplar);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ejemplares;
    }
}
