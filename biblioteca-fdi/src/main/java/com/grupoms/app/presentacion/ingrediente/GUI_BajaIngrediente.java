package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_BajaIngrediente extends JFrame implements IGUI{
    
    private JTextField idEmpleado;
    private JButton baja;

    public GUI_BajaIngrediente() {
        super("Baja Ingrediente");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(Context context) {
    	if (context == null)
    		setVisible(true);
    	else if (context.getEvento() == Evento.BAJA_INGREDIENTE_OK) {
            JOptionPane.showMessageDialog(this, "Empleado dado de baja con éxito");
            idEmpleado.setText("");
        } else if (context.getEvento() == Evento.BAJA_INGREDIENTE_KO) {
            JOptionPane.showMessageDialog(this, "Error al dar de baja al empleado");
        }
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelEmpleado = new JLabel("ID Ingrediente:");
        idEmpleado = new JTextField(10);
        
        baja = new JButton("Baja Ingrediente");
        baja.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idEmpleado.getText().trim());
                TIngrediente empleado = new TIngrediente();
                empleado.setID(id);
                empleado.setActivo(false); // baja lógica

                Context contexto = new Context(Evento.BAJA_INGREDIENTE, empleado);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
            }
        });

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelEmpleado, gbc);
        gbc.gridx = 1;
        panel.add(idEmpleado, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(baja, gbc);

        add(panel, BorderLayout.CENTER);
    }
}
    

