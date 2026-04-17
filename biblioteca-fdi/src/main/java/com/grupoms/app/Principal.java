package com.grupoms.app;

import javax.swing.*;
import java.awt.*;
import com.grupoms.app.presentacion.factoria.FactoriaVistas;
import com.grupoms.app.presentacion.IGUI;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;

	public Principal() {
		setTitle("Gestión Cafetería y Centro Cívico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(15, 15));

		JLabel titulo = new JLabel("Gestión Cafetería y Centro Cívico", SwingConstants.CENTER);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
		titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		add(titulo, BorderLayout.NORTH);
/*
		JPanel panelCafeteria = new JPanel(new GridLayout(2, 3, 20, 20));
		panelCafeteria.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		panelCafeteria.add(crearPanelCategoria("Pedidos",
				new String[][] { 
						{"Alta Pedido", FactoriaVistas.GUI_ALTA_PEDIDO },
						{ "Mostrar Pedido", FactoriaVistas.GUI_MOSTRAR_PEDIDO },
						{ "Devolver Pedido", FactoriaVistas.GUI_DEVOLVER_PEDIDO },
						{ "Modificar Pedido", FactoriaVistas.GUI_MODIFICAR_PEDIDO },
						{ "Listar Pedidos", FactoriaVistas.GUI_MOSTRAR_LISTA_PEDIDOS},
						{ "Listar Pedidos por Empleado", FactoriaVistas.GUI_MOSTRAR_PEDIDOS_EMPLEADO},
						{ "Listar Pedidos por Mesa", FactoriaVistas.GUI_MOSTRAR_PEDIDOS_MESA},
						{ "Quitar Producto", FactoriaVistas.GUI_DESVINCULAR_PRODUCTO_PEDIDO},
						{ "Cerrar Pedido", FactoriaVistas.GUI_CERRAR_PEDIDO},
						{ "Anyadir Producto", FactoriaVistas.GUI_VINCULAR_PRODUCTO_PEDIDO } }));

		panelCafeteria.add(crearPanelCategoria("Mesas", new String[][] { { "Alta Mesa", FactoriaVistas.GUI_ALTA_MESA },
				{ "Baja Mesa", FactoriaVistas.GUI_BAJA_MESA }, { "Modificar Mesa", FactoriaVistas.GUI_MODIFICAR_MESA },
				{ "Mostrar Mesa", FactoriaVistas.GUI_MOSTRAR_MESA },
				{ "Listar Mesas", FactoriaVistas.GUI_LISTAR_MESAS } }));

		panelCafeteria.add(crearPanelCategoria("Proveedores",
				new String[][] { { "Alta Proveedor", FactoriaVistas.GUI_ALTA_PROVEEDOR },
						{ "Baja Proveedor", FactoriaVistas.GUI_BAJA_PROVEEDOR },
						{ "Modificar Proveedor", FactoriaVistas.GUI_MODIFICAR_PROVEEDOR },
						{ "Mostrar Proveedor", FactoriaVistas.GUI_MOSTRAR_PROVEEDOR },
						{ "Listar Proveedores", FactoriaVistas.GUI_LISTAR_PROVEEDORES } }));

		panelCafeteria.add(crearPanelCategoria("Ingredientes",
				new String[][] { { "Alta Ingrediente", FactoriaVistas.GUI_ALTA_INGREDIENTE },
						{ "Baja Ingrediente", FactoriaVistas.GUI_BAJA_INGREDIENTE },
						{ "Modificar Ingrediente", FactoriaVistas.GUI_MODIFICAR_INGREDIENTE },
						{ "Mostrar Ingrediente", FactoriaVistas.GUI_MOSTRAR_INGREDIENTE },
						{ "Ingredientes por Proveedor", FactoriaVistas.GUI_LISTAR_INGREDIENTE_PROVEEDOR},
						{ "Ingredientes por Producto", FactoriaVistas.GUI_LISTAR_INGREDIENTE_PRODUCTO },
						{ "Listar Ingredientes", FactoriaVistas.GUI_LISTAR_INGREDIENTES } }));

		panelCafeteria.add(crearPanelCategoria("Empleados",
				new String[][] { { "Alta Empleado", FactoriaVistas.GUI_ALTA_EMPLEADO },
						{ "Baja Empleado", FactoriaVistas.GUI_BAJA_EMPLEADO },
						{ "Modificar Empleado", FactoriaVistas.GUI_MODIFICAR_EMPLEADO },
						{ "Mostrar Empleado", FactoriaVistas.GUI_MOSTRAR_EMPLEADO },
						{ "Listar Empleados", FactoriaVistas.GUI_LISTAR_EMPLEADOS } }));

		panelCafeteria.add(crearPanelCategoria("Productos",
				new String[][] { { "Alta Producto", FactoriaVistas.GUI_ALTA_PRODUCTO },
						{ "Baja Producto", FactoriaVistas.GUI_BAJA_PRODUCTO },
						{ "Modificar Producto", FactoriaVistas.GUI_MODIFICAR_PRODUCTO },
						{ "Mostrar Producto", FactoriaVistas.GUI_MOSTRAR_PRODUCTO },
						{ "Listar Productos", FactoriaVistas.GUI_LISTAR_PRODUCTOS }, 
						{ "Productos por Proveedor", FactoriaVistas.GUI_PRODUCTOS_POR_PROVEEDOR },
						{"Vincular Ingrediente", FactoriaVistas.GUI_VINCULAR_PRODUCTO_INGREDIENTE},
						{"Desvincular Ingrediente", FactoriaVistas.GUI_DESVINCULAR_PRODUCTO_INGREDIENTE}}));
*/
		JPanel panelCentroCivico = new JPanel();
		panelCentroCivico.setLayout(new GridLayout(2, 3, 20, 20));
		panelCentroCivico.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		panelCentroCivico.add(crearPanelCategoria("Material",
				new String[][] { { "Alta Material", FactoriaVistas.GUI_ALTA_MATERIAL },
						{ "Listar Material", FactoriaVistas.GUI_LISTAR_MATERIAL },
						{ "Baja Material", FactoriaVistas.GUI_BAJA_MATERIAL },
						{ "Mostrar Material", FactoriaVistas.GUI_MOSTRAR_MATERIAL },
						{ "Modificar Material", FactoriaVistas.GUI_MODIFICAR_MATERIAL }
				}));

		panelCentroCivico.add(crearPanelCategoria("Ejemplar",
		        new String[][] {
		            { "Alta Ejemplar", FactoriaVistas.GUI_ALTA_EJEMPLAR },
		            { "Baja Ejemplar", FactoriaVistas.GUI_BAJA_EJEMPLAR },
		            { "Listar Ejemplar", FactoriaVistas.GUI_LISTAR_EJEMPLAR },
		            { "Listar por Material", FactoriaVistas.GUI_LISTAR_EJEMPLARESMATERIAL },
		            { "Listar por Clase", FactoriaVistas.GUI_LISTAR_EJEMPLARES_POR_CLASE },
		            { "Listar Ejemplares Prestados Por Socios", FactoriaVistas.GUI_LISTAR_EJEMPLAR_PREST_SOCIO },
		            { "Mostrar Ejemplar", FactoriaVistas.GUI_MOSTRAR_EJEMPLAR },
		            { "Modificar Ejemplar", FactoriaVistas.GUI_MODIFICAR_EJEMPLAR }
		        }));

		panelCentroCivico.add(crearPanelCategoria("Clase",
				new String[][] { { "Alta Clase", FactoriaVistas.GUI_ALTA_CLASE },
						{ "Baja Clase", FactoriaVistas.GUI_BAJA_CLASE },
						{ "Listar Clase", FactoriaVistas.GUI_LISTAR_CLASE },
						{ "Mostrar Clase", FactoriaVistas.GUI_MOSTRAR_CLASE },
						{ "Modificar Clase", FactoriaVistas.GUI_MODIFICAR_CLASE },
						{ "Vincular Ejemplar", FactoriaVistas.GUI_VINCULAR_EJEMPLAR_CLASE },
						{ "Desvincular Ejemplar", FactoriaVistas.GUI_DESVINCULAR_EJEMPLAR_CLASE },
						{ "Listar Clases por Sala", FactoriaVistas.GUI_LISTAR_CLASES_POR_SALA } }));

		panelCentroCivico.add(crearPanelCategoria("Promoción",
				new String[][] { { "Alta Promoción", FactoriaVistas.GUI_ALTA_PROMOCION },
						{ "Baja Promoción", FactoriaVistas.GUI_BAJA_PROMOCION },
						{ "Listar Promoción", FactoriaVistas.GUI_LISTAR_PROMOCION },
						{ "Mostrar Promoción", FactoriaVistas.GUI_MOSTRAR_PROMOCION },
						{ "Modificar Promoción", FactoriaVistas.GUI_MODIFICAR_PROMOCION },
						{ "Ver Promociones por Socio", FactoriaVistas.GUI_VER_PROMOCIONES_POR_SOCIO } }));

		panelCentroCivico.add(crearPanelCategoria("Sala", new String[][] {
				{ "Alta Sala", FactoriaVistas.GUI_ALTA_SALA }, { "Baja Sala", FactoriaVistas.GUI_BAJA_SALA },
				{ "Listar Sala", FactoriaVistas.GUI_LISTAR_SALA }, { "Mostrar Sala", FactoriaVistas.GUI_MOSTRAR_SALA },
				{ "Modificar Sala", FactoriaVistas.GUI_MODIFICAR_SALA },
				{ "Mostrar Clases por Sala", FactoriaVistas.GUI_MOSTRAR_CLASES_POR_SALA} }));

		panelCentroCivico.add(crearPanelCategoria("Socio",
				new String[][] { { "Alta Socio", FactoriaVistas.GUI_ALTA_SOCIO },
						{ "Baja Socio", FactoriaVistas.GUI_BAJA_SOCIO },
						{ "Listar Socio", FactoriaVistas.GUI_LISTAR_SOCIOS },
						{ "Mostrar Socio", FactoriaVistas.GUI_MOSTRAR_SOCIO },
						{ "Modificar Socio", FactoriaVistas.GUI_MODIFICAR_SOCIO },
						{ "Ver Socios por Promoción", FactoriaVistas.GUI_MOSTRAR_SOCIOS_POR_PROMOCION },
						{ "Vincular Promoción a Socio", FactoriaVistas.GUI_VINCULAR_PROMOCION },
						{ "Desvincular Promoción a Socio", FactoriaVistas.GUI_DESVINCULAR_PROMOCION } }));

		panelCentroCivico.add(crearPanelCategoria("Prestamo",
				new String[][] { { "Alta Préstamo", FactoriaVistas.GUI_ALTA_PRESTAMO },
						{ "Devolución Préstamo", FactoriaVistas.GUI_DEVOLUCION_PRESTAMO },
						{ "Listar Préstamo", FactoriaVistas.GUI_LISTAR_PRESTAMO },
						{ "Mostrar Préstamo", FactoriaVistas.GUI_MOSTRAR_PRESTAMO },
						{ "Modificar Préstamo", FactoriaVistas.GUI_MODIFICAR_PRESTAMO },
						{ "Calcular Precio con Promoción", FactoriaVistas.GUI_CALCULAR_PRECIO_PROMOCION } }));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCentroCivico, null);

		splitPane.setResizeWeight(0.5);
		splitPane.setDividerSize(8);
		splitPane.setContinuousLayout(true);

		add(splitPane, BorderLayout.CENTER);

		JLabel footer = new JLabel("Gestión Cafetería y Centro Cívico - GrupoMS", SwingConstants.CENTER);
		footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		add(footer, BorderLayout.SOUTH);

		setVisible(true);
	}

	private JPanel crearPanelCategoria(String titulo, String[][] opciones) {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1, true), titulo));

		JPanel botonesPanel = new JPanel(new GridLayout(opciones.length, 1, 8, 8));
		for (String[] opcion : opciones) {
			JButton boton = new JButton(opcion[0]);
			boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
			boton.setFocusPainted(false);

			boton.addActionListener(e -> {
				IGUI vista = FactoriaVistas.getInstance().creaVista(opcion[1]);
				if (vista != null) {
					vista.actualizar(null);
				} else {
					JOptionPane.showMessageDialog(this, "No se pudo abrir la vista: " + opcion[0], "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			});

			botonesPanel.add(boton);
		}

		panel.add(botonesPanel, BorderLayout.CENTER);
		return panel;
	}
}
