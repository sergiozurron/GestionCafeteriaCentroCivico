package com.grupoms.app.presentacion.proveedor;

import javax.swing.JFrame;

import com.grupoms.app.presentacion.IGUI;
import com.grupoms.app.presentacion.controlador.Context;

public class GUI_ListarProveedores extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	@Override
	public void actualizar(Context context) {
		if (context == null)
    		setVisible(true);
	}

}
