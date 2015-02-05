package trustClient;

import processing.net.Client;
import buttons.Pregunta;
import processing.core.*;

/*
 Este codigo fue originalmente escrito por Alejandro Sanclemente y
 Andrés Bonilla para la clase de Interacción Hombre-Computador 2 bajo 
 supervisión del profesor Juan Salamanca. Universidad Icesi 2014.Todas
 las clases fueron revisadas y re-escritas por Juan Salamanca.

 Juan Salamanca & Alejandro Sanclemente 2015.

 Puede ser modificado y adaptado dando crédito a los autores.
 */

@SuppressWarnings("serial")
public class TrustClient extends PApplet {

	Client c;
	Logica log;
	String input;

	public void setup() {
		size(600, 600);
		textAlign(CENTER);
		smooth();
		// Comunicacion. Inicializa un cliente en el puerto indicado
		c = new Client(this, "localHost", 12345);
		log = new Logica(this, c);
		input = "...";
	}

	public void draw() {
		background(20);

		// Recibe datos del servidor
		if (c.available() > 0) {
			input = c.readString();
			// println("servidor dice: "+input);

		}
		log.show(input, true);
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
