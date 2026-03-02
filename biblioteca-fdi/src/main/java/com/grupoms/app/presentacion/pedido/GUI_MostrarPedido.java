package com.grupoms.app.presentacion.pedido;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import com.grupoms.app.negocio.pedido.TCarrito;
import com.grupoms.app.negocio.pedido.TLineaPedido;
import com.grupoms.app.negocio.pedido.TPedido;
import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;
import com.grupoms.app.presentacion.controlador.Controlador;
import com.grupoms.app.presentacion.controlador.Evento;

public class GUI_MostrarPedido extends JFrame implements IGUI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoPedido;
    private JButton btnMostrar;

    private JLabel fecha;
    private JLabel total_factura;
    private JLabel estado;
    private JLabel empleado_id;
    private JLabel mesa_id;
    private JLabel activo;

    private JTable tablaLineas;
    private DefaultTableModel modeloTabla;

    public GUI_MostrarPedido() {
        super("Mostrar Pedido");
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

        JLabel labelId = new JLabel("ID Pedido:");
        campoPedido = new JTextField(10);

        btnMostrar = new JButton("Mostrar Pedido");
        btnMostrar.addActionListener(e -> {
            try {
                int idPedido = Integer.parseInt(campoPedido.getText().trim());
                Context contexto = new Context(Evento.MOSTRAR_PEDIDO, idPedido);
                Controlador.getInstance().handle(contexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: el ID debe ser un número entero");
            }
        });

        fecha = new JLabel();
        total_factura = new JLabel();
        estado = new JLabel();
        empleado_id = new JLabel();
        mesa_id = new JLabel();
        activo = new JLabel();

        // TABLA DE LÍNEAS
        modeloTabla = new DefaultTableModel(new Object[]{"Producto", "Cantidad"}, 0);
        tablaLineas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaLineas);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(campoPedido, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(btnMostrar, gbc);

        gbc.gridwidth = 1;

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        panel.add(fecha, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Total Factura:"), gbc);
        gbc.gridx = 1;
        panel.add(total_factura, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        panel.add(estado, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("ID Empleado:"), gbc);
        gbc.gridx = 1;
        panel.add(empleado_id, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("ID Mesa:"), gbc);
        gbc.gridx = 1;
        panel.add(mesa_id, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1;
        panel.add(activo, gbc);

        // TABLA DE LÍNEAS
        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 1;
        panel.add(scrollTabla, gbc);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actualizar(Context context) {
        if (context == null) {
            setVisible(true);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            if (context.getEvento() == Evento.MOSTRAR_PEDIDO_OK) {

                TCarrito carrito = (TCarrito) context.getDatos();

                if (carrito != null) {
                    TPedido pedido = carrito.getPedido();

                    fecha.setText(pedido.getFecha() != null ? pedido.getFecha().toString() : "N/A");
                    total_factura.setText(String.valueOf(pedido.getTotal()));
                    estado.setText(pedido.getEstado());
                    empleado_id.setText(String.valueOf(pedido.getIdEmpleado()));
                    mesa_id.setText(String.valueOf(pedido.getIdMesa()));
                    activo.setText(pedido.getActivo() ? "Sí" : "No");

                    // LIMPIAR TABLA
                    modeloTabla.setRowCount(0);

                    // AÑADIR LÍNEAS
                    for (TLineaPedido lp : carrito.getLineasVenta()) {
                        modeloTabla.addRow(new Object[]{
                                lp.getProductoId(),
                                lp.getCantidad()
                        });
                    }

                } else {
                    limpiarLabels();
                }

            } else if (context.getEvento() == Evento.MOSTRAR_PEDIDO_KO) {
                JOptionPane.showMessageDialog(this, "Pedido no encontrado en la base de datos");
                limpiarLabels();
            }
        });
    }

    private void limpiarLabels() {
        fecha.setText("");
        total_factura.setText("");
        estado.setText("");
        empleado_id.setText("");
        mesa_id.setText("");
        activo.setText("");
        modeloTabla.setRowCount(0);
    }
}
