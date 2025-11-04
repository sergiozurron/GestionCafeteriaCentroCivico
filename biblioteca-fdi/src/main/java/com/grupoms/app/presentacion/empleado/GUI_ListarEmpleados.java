package com.grupoms.app.presentacion.empleado;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import com.grupoms.app.negocio.empleado.TEmpleado;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_ListarEmpleados extends JFrame implements IGUI {

    private JTextArea textArea;
    private JButton mostrar;

    public GUI_ListarEmpleados() {
        super("Mostrar Lista Empleados");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initGUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);

        mostrar = new JButton("Mostrar Empleados");
        mostrar.addActionListener(e -> {
            Context contexto = new Context();
            contexto.setEvento(Evento.MOSTRAR_EMPLEADOS);
            contexto.setDato(null); // no se necesita dato
            Controlador.getInstance().handle(contexto);
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(mostrar, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.MOSTRAR_EMPLEADOS_OK) {
            Set<TEmpleado> empleados = (Set<TEmpleado>) context.getDatos();
            if (empleados == null || empleados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay empleados activos en la base de datos");
                textArea.setText("");
            } else {
                StringBuilder sb = new StringBuilder();
                for (TEmpleado emp : empleados) {
                    sb.append("ID: ").append(emp.getID())
                      .append(", Nombre: ").append(emp.getNombre())
                      .append(", Donde atiende: ").append(emp.getDondeAtiende())
                      .append(", Sueldo: ").append(emp.getSueldo())
                      .append(", Activo: ").append(emp.getActivo() != null && emp.getActivo() ? "Sí" : "No")
                      .append("\n");
                }
                textArea.setText(sb.toString());
            }
        } else if (context.getEvento() == Evento.MOSTRAR_EMPLEADOS_KO) {
            JOptionPane.showMessageDialog(this, "Error al cargar los empleados");
        }
    }
}
