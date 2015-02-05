package trustClient;


import processing.core.*;
import processing.net.*;
import trustServer.ControlPantalla;


/**
 * @author jsalam
 *
 */
public class Logica {
	// El Applet
	PApplet app;

	// Jugadores
	Client c;

	// Control de cambio de pantalla
	public ControlPantalla control;

	/**
	 * @param app
	 * @param s
	 * @param c
	 */
	public Logica(PApplet app, Client c) {
		this.app = app;
		
		// Construye controlador de pantallas
		control = new ControlPantalla(app, c);
		control.esCliente = true;
	}

	/**
	 * 
	 */
	public void show(String input, boolean clienteConectado) {
		control.show(input, clienteConectado);
	}

}
