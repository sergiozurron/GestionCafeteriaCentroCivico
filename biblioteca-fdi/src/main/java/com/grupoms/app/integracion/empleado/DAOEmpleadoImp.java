package com.grupoms.app.integracion.empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grupoms.app.integracion.Transaction.Transaction;
import com.grupoms.app.integracion.Transaction.TransactionManager;
import com.grupoms.app.negocio.empleado.TEmpleado;

public class DAOEmpleadoImp implements DAOEmpleado {

    private static final String INSERT = "INSERT INTO empleados (nombre, activo, donde_atiende, sueldo) VALUES (?, ?, ?, ?)";
    private static final String READ_BY_ID = "SELECT id, nombre, activo, donde_atiende, sueldo FROM empleados WHERE id = ? AND activo = True FOR UPDATE";
    private static final String READ_ALL = "SELECT id, nombre, activo, donde_atiende, sueldo FROM empleados WHERE activo = True FOR UPDATE";
    private static final String UPDATE = "UPDATE empleados SET nombre = ?, donde_atiende = ?, sueldo = ? WHERE id = ?";
    private static final String UPDATE_ACTIVO = "UPDATE empleados SET activo = ? WHERE id = ?";

    @Override
    public Integer crearEmpleado(TEmpleado empleado) {
        Integer idGenerado = null;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, empleado.getNombre());
                ps.setBoolean(2, empleado.getActivo());
                ps.setString(3, empleado.getDondeAtiende());
                ps.setDouble(4, empleado.getSueldo());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                        empleado.setID(idGenerado);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear empleado", e);
        }

        return idGenerado;
    }

    @Override
    public TEmpleado mostrarEmpleado(Integer id) {
        TEmpleado empleado = null;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(READ_BY_ID)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        empleado = new TEmpleado();
                        empleado.setID(rs.getInt("id"));
                        empleado.setNombre(rs.getString("nombre"));
                        empleado.setActivo(rs.getBoolean("activo"));
                        empleado.setDondeAtiende(rs.getString("donde_atiende"));
                        empleado.setSueldo(rs.getDouble("sueldo"));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar empleado", e);
        }

        return empleado;
    }

    @Override
    public List<TEmpleado> mostrarListaEmpleados() {
        List<TEmpleado> lista = new ArrayList<>();

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement ps = c.prepareStatement(READ_ALL);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    TEmpleado e = new TEmpleado();
                    e.setID(rs.getInt("id"));
                    e.setNombre(rs.getString("nombre"));
                    e.setActivo(rs.getBoolean("activo"));
                    e.setDondeAtiende(rs.getString("donde_atiende"));
                    e.setSueldo(rs.getDouble("sueldo"));
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar empleados", e);
        }

        return lista;
    }

    @Override
    public Boolean modificarEmpleado(TEmpleado empleado) {
        Boolean exito = false;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement st = c.prepareStatement(UPDATE)) {
                st.setString(1, empleado.getNombre());
                st.setBoolean(2, empleado.getActivo());
                st.setString(3, empleado.getDondeAtiende());
                st.setDouble(4, empleado.getSueldo());
                st.setInt(5, empleado.getID());

                int rows = st.executeUpdate();
                exito = rows > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al modificar empleado", e);
        }

        return exito;
    }

    @Override
    public Boolean bajaEmpleado(TEmpleado empleado) {
        Boolean exito = false;

        try {
            Transaction t = TransactionManager.getInstance().getTransaction();
            if (t == null)
                throw new IllegalStateException("No hay transacción activa");

            Connection c = (Connection) t.getResource();

            try (PreparedStatement st = c.prepareStatement(UPDATE_ACTIVO)) {
                st.setBoolean(1, empleado.getActivo());
                st.setInt(2, empleado.getID());

                int rows = st.executeUpdate();
                exito = rows > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al dar de baja empleado", e);
        }

        return exito;
    }
}