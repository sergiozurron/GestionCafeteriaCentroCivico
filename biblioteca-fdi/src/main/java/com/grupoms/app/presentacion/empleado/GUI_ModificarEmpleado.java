package com.grupoms.app.presentacion.empleado;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ModificarEmpleado extends JFrame implements IGUI {

    private JTextField id;
    private JTextField nombre;
    private JTextField dondeAtiende;
    private JTextField sueldo;
    private JCheckBox activo;

    private JButton modificar;

    public GUI_ModificarEmpleado() {
        super("Modificar Empleado");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null)
    		setVisible(true);
    	else if (context.getEvento() == Evento.MODIFICAR_EMPLEADO_OK) {
            JOptionPane.showMessageDialog(this, "Empleado modificado con éxito");
            // limpiar campos
            id.setText("");
            nombre.setText("");
            dondeAtiende.setText("");
            sueldo.setText("");
            activo.setSelected(true);
        } else if (context.getEvento() == Evento.MODIFICAR_EMPLEADO_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido modificar el empleado");
        }
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes
        JLabel labelId = new JLabel("ID Empleado:");
        id = new JTextField(10);

        JLabel labelNombre = new JLabel("Nombre Empleado:");
        nombre = new JTextField(12);

        JLabel labelDonde = new JLabel("Donde atiende:");
        dondeAtiende = new JTextField(12);

        JLabel labelSueldo = new JLabel("Sueldo:");
        sueldo = new JTextField(10);

        JLabel labelActivo = new JLabel("Activo:");
        activo = new JCheckBox();
        activo.setSelected(true);

        modificar = new JButton("Modificar Empleado");
        modificar.addActionListener(e -> {
            try {
                Integer idEmp = Integer.parseInt(id.getText().trim());
                String nombreEmp = nombre.getText().trim();
                String dondeEmp = dondeAtiende.getText().trim();
                Double sueldoEmp = Double.parseDouble(sueldo.getText().trim());
                Boolean activoEmp = activo.isSelected();

                if (idEmp <= 0) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser mayor que 0");
                    return;
                }
                if (nombreEmp.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                    return;
                }
                if (sueldoEmp < 0) {
                    JOptionPane.showMessageDialog(this, "El sueldo no puede ser negativo");
                    return;
                }

                TEmpleado emp = new TEmpleado();
                emp.setID(idEmp);
                emp.setNombre(nombreEmp);
                emp.setDondeAtiende(dondeEmp);
                emp.setSueldo(sueldoEmp);
                emp.setActivo(activoEmp);

                Context contexto = new Context(Evento.MODIFICAR_EMPLEADO, emp);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: ID y sueldo deben ser numéricos válidos");
            }
        });

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(id, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelNombre, gbc);
        gbc.gridx = 1;
        panel.add(nombre, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelDonde, gbc);
        gbc.gridx = 1;
        panel.add(dondeAtiende, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelSueldo, gbc);
        gbc.gridx = 1;
        panel.add(sueldo, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelActivo, gbc);
        gbc.gridx = 1;
        panel.add(activo, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(modificar, gbc);

        add(panel, BorderLayout.CENTER);
    }
}
