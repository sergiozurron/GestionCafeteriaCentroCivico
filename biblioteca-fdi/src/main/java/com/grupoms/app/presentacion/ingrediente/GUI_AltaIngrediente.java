package com.grupoms.app.presentacion.ingrediente;

import javax.swing.*;
import java.awt.*;

import com.grupoms.app.negocio.ingrediente.TIngrediente;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_AltaIngrediente extends JFrame implements IGUI{

	private JTextField nombre;
    private JTextField precio;
    private JTextField prov;

    
    private JButton crear;

    public GUI_AltaIngrediente(){
       super("Alta Ingrediente");
       initGUI(); //iniciamos el front por asi ddecirlo
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //destruye la ventana sin cerrar la app
       pack(); //ajusta
       setLocationRelativeTo(null); //centra
       setVisible(true); //es visible
    }
    @Override
    public void actualizar(Context context) {
          if (context.getEvento() == Evento.ALTA_INGREDIENTE) {
            // Muestra mensaje de éxito
            JOptionPane.showMessageDialog(this, "Ingrediente creado con éxito");
            // Limpia los campos para la siguiente entrada
            nombre.setText("");
            precio.setText("");
        }
    }
    
    public void initGUI(){
        setLayout(new BorderLayout()); //layout general

        JPanel panel = new JPanel(new GridBagLayout()); //panel principal

        JLabel labelNombre = new JLabel("Nombre Ingrediente:");
        nombre = new JTextField(10);

        JLabel labelPrecio = new JLabel("Precio:");
        precio = new JTextField(10);

        JLabel labelProv = new JLabel("ID proveedor:");
        prov = new JTextField(10);

        crear = new JButton("Crear Ingrediente");
        crear.addActionListener(e -> {
            try {
                String nombrerI = nombre.getText();
                Double precioI = Double.parseDouble(precio.getText());
                Integer provI = Integer.parseInt(prov.getText());
                // Crear el TPedido directamente aquí
                TIngrediente ingr = new TIngrediente();
                ingr.setNombre(nombrerI);
                ingr.setPrecio(precioI);
                ingr.setIDProveedor(provI);
                // Enviar al controlador
                Context contexto = new Context(Evento.ALTA_INGREDIENTE, ingr);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: los campos numéricos no son válidos");
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);        // Espaciado alrededor de los componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Cada componente se expande horizontalmente

        // Colocación de los componentes en la cuadrícula
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelNombre, gbc);

        gbc.gridx = 1;
        panel.add(labelPrecio, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(crear, gbc);

        // Añade el panel principal al centro del JFrame
        add(panel, BorderLayout.CENTER);
    }
    
}
