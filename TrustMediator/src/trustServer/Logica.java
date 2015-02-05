package trustServer;

import processing.core.*;
import processing.net.*;

/**
 * @author jsalam
 *
 */
public class Logica {
	// El Applet
	PApplet app;
	Client c;
	Server s;

	// Control de cambio de pantalla
	public ControlPantalla control;

	/**
	 * @param app
	 * @param s
	 * @param c
	 */
	public Logica(PApplet app, Server s, Client c) {
		this.app = app;

		// Construye controlador de pantallas
		control = new ControlPantalla(app, s, c);
	}

	/**
	 * 
	 */
	public void show(String input, boolean clienteConectado) {
		control.show(input, clienteConectado);
	}
}
