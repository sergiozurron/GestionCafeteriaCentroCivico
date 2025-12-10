package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.negocio.socioJPA.TAdulto;
import com.grupoms.app.negocio.socioJPA.TInfantil;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import java.awt.*;

public class GUI_ModificarSocio extends JFrame implements IGUI {
    private static final long serialVersionUID = 1L;
    private JTextField id;
    private JTextField nombre;
    private JTextField dni;
    private JTextField cuota;

    private JTextField edad;
    private JLabel reduccion;

    private JComboBox<String> tipoCombo,miembroPlenoCombo;

    private JButton modificar;

    public GUI_ModificarSocio(){
        super("Modificar Socio");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panel = new JPanel(new GridBagLayout());

        JLabel labelId = new JLabel("ID Socio: ");
        id = new JTextField(10);

        JLabel labelNombre = new JLabel("Nombre Y Apellidos: ");
        nombre = new JTextField(10);

        JLabel labelDni = new JLabel("DNI: ");
        dni = new JTextField(10);

        JLabel labelCuota = new JLabel("Cuota: ");
        cuota = new JTextField(10);

        JLabel labelTipo = new JLabel("Tipo: ");
        tipoCombo = new JComboBox<>(new String[]{"Adulto", "Infantil"});
        tipoCombo.addActionListener(e -> actualizarCamposTipo());

        // Campos Adulto
        JLabel labelMP = new JLabel("Miembro Pleno: ");
        miembroPlenoCombo = new JComboBox<>(new String[]{"Si", "No"});

        // Campos Infantil
        JLabel labelEdad = new JLabel("Edad: ");
        edad = new JTextField(10);
        JLabel labelReduccion = new JLabel("Reducción: ");
        int ed=Integer.parseInt(edad.getText());
        if (ed <= 3) {
            reduccion = new JLabel("50%");
        } else if (ed <= 14) {
            reduccion = new JLabel("20%");
        } else if (ed <= 18) {
            reduccion = new JLabel("10%");
        } else {
            reduccion = new JLabel("0%");;
        }

        modificar = new JButton("Modificar Socio");
        modificar.addActionListener(e -> {
            try {
                int idI = Integer.parseInt(id.getText());
                String nombreI = nombre.getText();
                String dniI=dni.getText();
                int cuotaI = Integer.parseInt(cuota.getText());

                TSocio s = null;
                if (tipoCombo.getSelectedItem().equals("Adulto")) {
                    TAdulto adulto = new TAdulto();
                    adulto.setTipoSocio(0);
                    if(miembroPlenoCombo.getSelectedItem().equals("Si")){
                        adulto.setMiembroPleno(true);
                    }
                    else if(miembroPlenoCombo.getSelectedItem().equals("No")){
                        adulto.setMiembroPleno(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Debe rellenar SI/NO");
                        return;
                    }
                    s=adulto;
                } else if(tipoCombo.getSelectedItem().equals("Infantil")){
                    TInfantil infantil = new TInfantil();
                    infantil.setTipoSocio(1);
                    infantil.setEdad(Integer.parseInt(edad.getText()));
                    infantil.setReduccion(infantil.getReduccion());
                    s = infantil;
                }

                s.setId(idI);
                s.setNombreYapellido(nombreI);
                s.setDni(dniI);
                s.setCuota(cuotaI);

                Context contexto = new Context(Evento.MODIFICAR_SOCIO, s);
                Controlador.getInstance().handle(contexto);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: campos numéricos no válidos");
            }
        });

        // Posicionamiento
        gbc.gridx = 0; gbc.gridy = 0; panel.add(labelId, gbc);
        gbc.gridx = 1; panel.add(id, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(labelNombre, gbc);
        gbc.gridx = 1; panel.add(nombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(labelDni, gbc);
        gbc.gridx = 1; panel.add(dni, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(labelCuota, gbc);
        gbc.gridx = 1; panel.add(cuota, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(labelTipo, gbc);
        gbc.gridx = 1; panel.add(tipoCombo, gbc);

        // Campos Adulto
        gbc.gridx = 0; gbc.gridy = 5; panel.add(labelMP, gbc);
        gbc.gridx = 1; panel.add(miembroPlenoCombo, gbc);

        // Campos Infantil
        gbc.gridx = 0; gbc.gridy = 6; panel.add(labelEdad, gbc);
        gbc.gridx = 1; panel.add(edad, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panel.add(labelReduccion, gbc);
        gbc.gridx = 1; panel.add(reduccion, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        panel.add(modificar, gbc);

        add(panel, BorderLayout.CENTER);

        actualizarCamposTipo(); // Oculta campos según tipo inicial
    }

    private void actualizarCamposTipo() {
        Boolean isAdulto=tipoCombo.getSelectedItem().equals("Adulto");
        miembroPlenoCombo.setEnabled(isAdulto);
        edad.setEnabled(!isAdulto);
        reduccion.setEnabled(!isAdulto);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
        } else if (context.getEvento() == Evento.MODIFICAR_SOCIO_OK) {
            JOptionPane.showMessageDialog(this, "Socio modificado con éxito");
            // Limpiar campos
            id.setText("");
            nombre.setText("");
            dni.setText("");
            cuota.setText("");
            edad.setText("");
            reduccion.setText("");
        } else if (context.getEvento() == Evento.MODIFICAR_SOCIO_KO) {
            JOptionPane.showMessageDialog(this, "No se ha podido modificar el socio");
        }
    }
}
