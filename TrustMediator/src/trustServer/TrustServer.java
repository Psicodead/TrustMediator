package trustServer;

import processing.core.*;
import processing.net.*;
import buttons.*;

/*
 Este codigo fue originalmente escrito por Alejandro Sanclemente y
 Andrés Bonilla para la clase de Interacción Hombre-Computador 2 bajo 
 supervisión del profesor Juan Salamanca. Universidad Icesi 2014.Todas
 las clases fueron revisadas y re-escritas por Juan Salamanca.

 Juan Salamanca & Alejandro Sanclemente 2015.

 Puede ser modificado y adaptado dando crédito a los autores.
 */

@SuppressWarnings("serial")
public class TrustServer extends PApplet {
	Server s;
	Client c;
	Logica log;
	String input;
	boolean clienteConectado;

	public void setup() {
		size(600, 600);
		textAlign(CENTER);
		smooth();
		// Comunicacion. Inicializa un server en el puerto indicado
		s = new Server(this, 12345);
		log = new Logica(this, s, c);
		input = "...";
		clienteConectado = false;
	}

	public void draw() {
		background(20);

		// Recibe datos del cliente
		c = s.available();
		if (c != null) {
			input = c.readString();
			// println("cliente dice: "+input);
			clienteConectado = true;
		}
		log.show(input, clienteConectado);
		input = "...";
	}

	public void keyPressed() {
		// trae el boton tipo Pregunta activo desde la clase pantalla
		try {
			typeText(log.control.pasarPreguntaActiva());
		} catch (Exception e) {
			// No hay boton pregunta seleccionado
		}
	}

	/**
	 * Este metodo crea cadenas cuando el usuario digita el teclado
	 * 
	 * @param but
	 *            El boton que recibe la pregunta digitada
	 */
	public void typeText(Pregunta but) {
		if (keyCode == 32 || keyCode >= 65 && keyCode <= 90) {
			but.teclado(key);
		}

		// Backspace
		if (!but.getPressed()) {
			if (keyCode == 8) {
				but.teclado(8);
			}
		}
	}
	
}
