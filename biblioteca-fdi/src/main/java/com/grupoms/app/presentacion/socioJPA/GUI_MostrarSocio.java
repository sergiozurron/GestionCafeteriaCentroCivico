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

public class GUI_MostrarSocio extends JFrame implements IGUI {
    private static final long serialVersionUID = 1L;

    private JTextField campoID;
    private JButton mostrar;

    private JLabel nombreLabel;
    private JLabel dniLabel;
    private JLabel cuotaLabel;
    private JLabel mPLabel;
    private JLabel reduccionLabel;

    public GUI_MostrarSocio(){
        super("Mostrar Socio");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo ID
        JLabel labelID = new JLabel("ID Socio:");
        campoID = new JTextField(10);

        mostrar = new JButton("Mostrar Socio");
        mostrar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoID.getText());
                Context contexto = new Context(Evento.MOSTRAR_SOCIO, id);
                Controlador.getInstance().handle(contexto);


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser numérico");
            }
        });
        nombreLabel = new JLabel();
        dniLabel=new JLabel();
        cuotaLabel= new JLabel();
        mPLabel= new JLabel();
        reduccionLabel= new JLabel();

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelID, gbc);
        gbc.gridx = 1;
        panel.add(campoID, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(mostrar, gbc);


        y++;
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Nombre y Apellidos:"), gbc);
        gbc.gridx = 1;
        panel.add(nombreLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        panel.add(dniLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Cuota:"), gbc);
        gbc.gridx = 1;
        panel.add(cuotaLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Miembro Pleno/Edad:"), gbc);
        gbc.gridx = 1;
        panel.add(mPLabel, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Reducción(Infanril):"), gbc);
        gbc.gridx = 1;
        panel.add(reduccionLabel, gbc);
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        switch(context.getEvento()) {
            case Evento.MOSTRAR_SOCIO_OK:
                TSocio s = (TSocio) context.getDatos();
                if(s!=null) {
                    nombreLabel.setText(s.getNombreYapellido());
                    dniLabel.setText(s.getDni());
                    cuotaLabel.setText(s.getCuota().toString());;
                    if(s.getTipoSocio()==0) {
                        TAdulto a = (TAdulto) s;
                        if(a.getMiembroPleno()){
                            mPLabel.setText("Sí");
                        }else{
                            mPLabel.setText("No");
                        }
                    }
                    else {
                        TInfantil i = (TInfantil) s;
                        mPLabel.setText(String.valueOf(i.getEdad()));
                        Double r=i.getReduccion()*100;
                        reduccionLabel.setText(String.valueOf(r)+"%");
                    }
                }
                break;
            case Evento.MOSTRAR_SOCIO_KO:
                JOptionPane.showMessageDialog(this, "Socio no encontrado en la base de datos");
                nombreLabel.setText("");
                dniLabel.setText("");
                cuotaLabel.setText("");
                mPLabel.setText("");
                reduccionLabel.setText("");
                break;
        }
    }
}
