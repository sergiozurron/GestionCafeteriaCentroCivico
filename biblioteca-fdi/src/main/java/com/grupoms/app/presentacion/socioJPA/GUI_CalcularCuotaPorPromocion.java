package com.grupoms.app.presentacion.socioJPA;

import com.grupoms.app.negocio.socioJPA.TAdulto;
import com.grupoms.app.negocio.socioJPA.TInfantil;
import com.grupoms.app.negocio.socioJPA.TSocio;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUI_CalcularCuotaPorPromocion extends JFrame implements IGUI {

    private static final long serialVersionUID = 1L;

    private JTextField campoIdPromocion;
    private JButton btnAplicar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public GUI_CalcularCuotaPorPromocion() {
    	setTitle("Calcular nuevas cuotas de socios por promocion");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelId = new JLabel("ID Promoción:");
        campoIdPromocion = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        panelTop.add(labelId, gbc);

        gbc.gridx = 1;
        panelTop.add(campoIdPromocion, gbc);

        btnAplicar = new JButton("Establecer nuevas cuotas");
        btnAplicar.addActionListener(e -> aplicarPromocion());

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panelTop.add(btnAplicar, gbc);

        add(panelTop, BorderLayout.NORTH);

        // TABLA
        String[] columnas = {
                "ID", "Nombre", "Tipo",
                "Miembro Pleno", "Reducción", "Cuota Nueva"
        };

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void aplicarPromocion() {
        try {
            int idPromocion = Integer.parseInt(campoIdPromocion.getText());

            Context contexto = new Context(Evento.APLICAR_PROMOCION, idPromocion);
            Controlador.getInstance().handle(contexto);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID de promoción inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
        	limpiarFormulario();
            setVisible(true);
            return;
        }

        switch (context.getEvento()) {

            case Evento.APLICAR_PROMOCION_OK:

                List<TSocio> datos = (List<TSocio>) context.getDatos();

                modelo.setRowCount(0);

                for (TSocio s : datos) {

                    String tipo = (s.getTipoSocio() == 0) ? "Adulto" : "Infantil";

                    String miembroPleno = "-";
                    String reduccion = "-";

                    if (s instanceof TAdulto) {
                        miembroPleno = ((TAdulto) s).getMiembroPleno() ? "Sí" : "No";
                    } 
                    else if (s instanceof TInfantil) {
                        reduccion = String.valueOf(((TInfantil) s).getReduccion());
                    }

                    modelo.addRow(new Object[]{
                        s.getId(),
                        s.getNombreYapellido(),
                        tipo,
                        miembroPleno,
                        reduccion,
                        s.getCuota()
                    });
                }

                JOptionPane.showMessageDialog(this,
                        "Promoción aplicada correctamente");
                
                limpiarFormulario();

                break;

            case Evento.APLICAR_PROMOCION_KO:

                JOptionPane.showMessageDialog(this,
                        "Error al aplicar la promoción",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;

            default:
                break;
        }
    }
    
    private void limpiarFormulario() {
		campoIdPromocion.setText("");
		btnAplicar.setSelected(false);

		pack();
	}
}