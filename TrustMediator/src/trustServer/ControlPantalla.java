package trustServer;

import java.io.File;

import buttons.Pregunta;
import buttons.GanoPerdio;
import processing.core.*;
import processing.net.Server;
import processing.net.Client;

public class ControlPantalla {
	public boolean esCliente;
	// control para guardar datos una sola vez por ronda
	// private boolean dataRecorded;
	// Las pantallas
	private Pantalla pantalla;
	private GanoPerdio resultado;
	// Control de pantallas
	private boolean sent;
	private boolean controlReset;
	private boolean adivinarInactivo;
	public PApplet app;
	public int pantallaActual;
	public int totalPantallas;
	String nombrePantalla;
	Server s;
	Client c;
	// Fuentes
	public PFont estandar;

	// botonesActivos
	String[] botonesActivos;
	/*
	 * arreglo de dos enteros que controlan el cambio de pantallaActual cuando
	 * son iguales. posicion [0] para jLocal y [1] para jRemoto
	 */
	public int[] camPant;

	// Comunicacion
	public SimboloComunicacion simbolo; // boton envio
	public int estadoSimbolo;

	// Jugadores
	public Jugador jLocal, jRemoto;

	// Recopilacion de datos
	private final File folder = new File("../bin/data/datosPruebas");
	private int numeroArchivos = 0;
	private boolean guardo = false;

	/**
	 * Constructor para el servidor
	 * 
	 * @param app
	 * @param botones
	 * @param s
	 * @param c
	 */
	public ControlPantalla(PApplet app, Server s, Client c) {
		this.app = app;
		this.s = s;
		this.c = c;
		esCliente = false;
		setupControl();
	}

	/**
	 * Constructor para el cliente
	 * 
	 * @param app
	 * @param botones
	 * @param c
	 */
	public ControlPantalla(PApplet app, Client c) {
		this.app = app;
		this.c = c;
		esCliente = true;
		setupControl();
	}

	/**
	 * Metodo invocado por ambos constructores para iniciar los elementos de
	 * control y las pantallas
	 */
	public void setupControl() {
		sent = false;
		camPant = new int[2];
		simbolo = new SimboloComunicacion(app, 565, 40);
		estadoSimbolo = 0;
		controlReset = false;
		pantalla = new Pantalla(app);
		pantallaActual = 0;
		totalPantallas = 10;
		nombrePantalla = "";
		estandar = app.loadFont("Roboto-Thin-16.vlw");
		// construye jugadores
		jLocal = new Jugador(app);
		jRemoto = new Jugador(app);
		adivinarInactivo = false;
		resultado = new GanoPerdio(app);
	}

	/**
	 * Metodo que muestra las pantallas a través de un switch case
	 * 
	 * @param sig
	 */
	public void show(String input, boolean clienteConectado) {

		// Procesa datos enviados por el cliente
		procesarInput(input);
		// Muestra el simbolo de comunicacion de acuerdo a su estado
		simbolo.show(estadoSimbolo);
		/*
		 * Controla el cambio de pantalla cuando jRemoto y jLocal hayan oprimido
		 * un boton
		 */
		cntrlPantalla();
		reset();
		pantalla.setPantallaID(pantallaActual);
		app.fill(255);
		app.textFont(estandar, 16);

		switch (pantallaActual) {

		/*
		 * Se tiene una sola pantalla con todos los botones desactivados e
		 * invisibles. Para cada caso se activan los botones de esta pantalla.
		 * Los nombres de los botones son: siguiente, nombre casa, casaVentana,
		 * casaPuerta, casaPuertaVentana, pregunta, si, no, calcular, reiniciar,
		 * adivinar Para indicar cuales botones activar debe pasar al metodo
		 * separarCadena un parametro usando "/" para los botones asi:
		 * botonesActivos = separarCadena("boton1/boton2/boton...")
		 */

		case 0: // ******************** PANTALLA DE INSTRUCCIONES
			nombrePantalla = "Instrucciones";
			app.text(
					"Escriba las instrucciones del juego aqui. Puede escribir varios renglones.",
					150, 200, 300, 300);
			botonesActivos = separarCadena("siguiente");

			break;
		case 1: // ******************** PANTALLA DE REGISTRO
			nombrePantalla = "Registro";
			botonesActivos = separarCadena("nombre");
			break;

		case 2: // ******************** PANTALLA DE SELECCION DE CASA
			nombrePantalla = "Seleccion de Casa";
			app.text(
					"Selecciones su casa y memorice cuantas ventanas y puertas tiene",
					150, 80, 300, 300);
			botonesActivos = separarCadena("casa/casaVentana/casaPuerta/casaPuertaVentana");
			break;

		case 3: // ******************* PANTALLA DE PREGUNTA AL OPONENTE
			nombrePantalla = "Pregunta Oponente";
			// cuando hay una b¡vueva ronda se resetea el valor del balance de
			// confianza
			pantalla.calcP.valueReset();
			app.text(
					"Haga una pregunta de respuesta SI o NO a su oponente. "
							+ "Tip: Las preguntas que incluyen una negación son mas dificlies de interpretar. Evite formularlas",
					150, 80, 300, 300);
			botonesActivos = separarCadena("pregunta");
			break;

		case 4: // ******************* PANTALLA DE RESPUESTA AL OPONENTE
			nombrePantalla = "Respuesta al Oponente";
			app.text("Tu oponente pregunta: " + jRemoto.getPregunta() + " ?",
					150, 80, 300, 300);
			botonesActivos = separarCadena("si/no");
			break;

		case 5: // ***************************** PANTALLA DE HONESTIDAD
			nombrePantalla = "Autopregunta Honestidad";
			app.text("Fuiste honesto con tu respuesta?", 150, 80, 300, 300);
			botonesActivos = separarCadena("si/no");
			break;

		case 6: // ***************************** PANTALLA DE CONVICCIÓN
			nombrePantalla = "Respuesta Conviccion";
			app.text("Preguntaste " + jLocal.getPregunta()
					+ ". Tu oponente respondió:" + jRemoto.getRespuesta()
					+ ". Crees que esta siendo honesto?", 150, 80, 300, 300);
			botonesActivos = separarCadena("si/no");
			break;

		case 7: // ***************************** PANTALLA DE CALCULO DE BALANCE
			jLocal.calcularBalance(jRemoto);
			nombrePantalla = "Calculo de Balance 1";
			app.text("Calcular el balance de confianza", 150, 80, 300, 300);
			botonesActivos = separarCadena("calcular");
			break;

		case 8: // ***************************** PANTALLA DE CALCULO DE BALANCE
			nombrePantalla = "Calculo de Balance 2";
			/*
			 * Formatea boton Calc para que gire la rueda con el vlaor del
			 * balance almacenado en el jLocal
			 */
			pantalla.setupCalcPorcentaje(360 * jLocal.getBalance());
			app.text("El balance de confianza y persuación mutuo es de:", 150,
					80, 300, 300);
			botonesActivos = separarCadena("calcularP/reiniciar/adivinar");

			// *** si pide una nueva ronda
			if (pantalla.getNuevaRonda()) {
				pantallaActual = 2;// este valor se incrementa en cntrlPantalla
				jLocal.saveAndReset();
				jRemoto.saveAndReset();
				// inhabilita adivinar de jLocal
				pantalla.adivinar.setEnabled(false);
			}

			// *** si quiere adivinar
			if (pantalla.getAdivinar()) {
				// graba los datos en los jugadores
				jLocal.saveAndReset();
				jRemoto.saveAndReset();
				// inhabilita nueva ronda de jLocal
				pantalla.nRonda.setEnabled(false);
				// camPant[0] = 1;
				// camPant[1] = 1;

			}

			// si recibio el mensaje de cambiar de simbolo a estado 3: oponente
			// quiere una nueva ronda
			if (estadoSimbolo == 3) {
				// inhabilita el boton adivinar en jRemoto
				pantalla.adivinar.setEnabled(false);
			}

			// si recibio el mensaje de cambiar de simbolo a estado 4: Oponente
			// quiere Adivinar
			if (estadoSimbolo == 4) {
				// inhabilita el boton nuevaRonda en jRemoto
				pantalla.nRonda.setEnabled(false);
				// camPant[0] = 1;
				// camPant[1] = 1;
				adivinarInactivo = true;
			}

			break;

		case 9: // ***************************** PANTALLA DE ADIVINAR
			nombrePantalla = "Adivinar";
			if (!adivinarInactivo) {
				botonesActivos = separarCadena("casa/casaVentana/casaPuerta/casaPuertaVentana");
			} else {
				botonesActivos = separarCadena("siguiente");
				app.text("Tu oponente decidió adivinar. Espera su selección",
						150, 80, 300, 300);
			}
			break;

		case 10: // ***************************** PANTALLA DE RESULTADO
			nombrePantalla = "Resultado";
			/////////bug de doble guardado
			if (guardo == false) {
				finalizarJuego();
				guardo = true;
				System.out.println("GUARDEEEEEEEEEEEEEEEEE");
			}
			botonesActivos = separarCadena("");
			// si jLocal estaba adivinando
			if (!adivinarInactivo) {
				// si jlocal Ganó
				if (jLocal.verificarSiGano()) {
					// muestra que gano
					resultado.show(true);
					// le avisa a jremoto que jLocal gano
					if (!sent) {
						if (esCliente) {
							c.write("resultadoOponente:gané");
						} else {
							s.write("resultadoOponente:gané");
						}
						sent = true;
					}
				} else {
					// muestra que perdió
					resultado.show(false);
					// le avisa a jremoto que jLocal perdió
					if (!sent) {
						if (esCliente) {
							c.write("resultadoOponente:perdí");
						} else {
							s.write("resultadoOponente:perdí");
						}
						sent = true;
					}
				}
			}
			// si no estaba adivinando
			else {
				resultado.show(jRemoto.verificarResultado());
			}

		}

		app.textFont(estandar, 16);
		app.fill(255);
		if (esCliente) {
			// muestra mensaje en pantalla
			app.text("pantalla Cliente", 130, 70);
		} else {
			// muestra mensaje en pantalla
			app.text("pantalla Servidor", 130, 70);
		}

		// Se activan los botones en la pantalla
		pantalla.activarBotones(botonesActivos);
		// Se muestra la pantalla
		if (clienteConectado) {
			pantalla.show(jRemotoListo());
		} else {
			app.text("Esperando a que se conecte el oponente", app.width / 2,
					540);
		}
		// Envia un mensaje si algun boton fue presionado
		if (pantalla.botonPresionado()) {
			enviarMensaje(pantalla.getMensaje());
		}
	}

	/**
	 * El protocolo de comunicación esta definido asi:
	 * "siguientePantalla/boton/contenido1/...". Todos los mensajes recibidos se
	 * dividen en un arreglo de dos o mas posiciones de acuerdo al numero de "/"
	 * que traiga el mensaje. La pos [1] es la etiqueta que identifica el boton
	 * que disparó la accion de enviar el mensaje. Las demas posiciones guardan
	 * el contenido. Procesa datos recibidos del cliente. El mensaje puede traer
	 * etiquetas para el control de turnos o datos de los botones oprimidos.
	 */
	private void procesarInput(String val) {
		// rompe la cadena en dos ":"
		String[] data = val.split(":");
		if (data[0].equals("siguientePantalla")) {
			// Asigna el entero 1 a la posicion [1]
			// del arreglo de control de pantalla
			camPant[1] = 1;
			// Muestra el mesaje de jRemoto listo
			estadoSimbolo = 2;
		}
		if (data[0].equals("siguientePantallaNR")) {
			camPant[1] = 1;
			// Muestra el mesaje de jRemoto pidiendo nueva ronda
			estadoSimbolo = 3;
		}
		if (data[0].equals("siguientePantallaAD")) {
			camPant[1] = 1;
			// Muestra el mesaje de jRemoto adivinando
			estadoSimbolo = 4;
		}

		/*
		 * le pasa el mensaje al jugador para que este se encargue de almacenar
		 * las variables que recibe
		 */
		if (data.length > 1)
			jRemoto.procesarMensaje(data[1]);
	}

	/**
	 * Verifica si algun boton ha sido presionado. Cambia el simbolo si el
	 * contrincante ya jugó y esta listo. Verifica si ambos jugadores
	 * oprimieron sus botones. Utiliza camPant[], arreglo de dos enteros que
	 * controlan el cambio de pantallaActual cuando son iguales. posicion [0]
	 * para jLocal y [1] para jRemoto
	 */
	public void cntrlPantalla() {

		// Verifica si ambos jugadores oprimieron sus botones
		if (camPant[0] != 0 && camPant[0] == camPant[1]) {
			pantallaActual++;
			camPant[0] = 0; // jLocal
			camPant[1] = 0; // jRemoto
			controlReset = false;
		}
	}

	public boolean jRemotoListo() {
		boolean temp = false;
		if (camPant[1] == 1)
			temp = true;
		return temp;
	}

	/**
	 * Verifica si alguno de los botones ha sido oprimido y se envia mensaje al
	 * jugador remoto. Se desabilita el boton que ha sido oprimido
	 */
	private void enviarMensaje(String msg) {
		if (!sent) {
			if (esCliente) {
				enviarMensajeCliente(msg);
			} else {
				enviarMensajeServidor(msg);
			}
			sent = true;
			estadoSimbolo = 1;
			camPant[0] = 1; // jLocal

		}
	}

	/**
	 * El servidor envia el mensaje asociado al boton que recibe de parametro
	 * 
	 * @param but
	 */
	private void enviarMensajeServidor(String msg) {
		// Envia un mensaje al cliente
		s.write(msg + "/Pantalla_" + nombrePantalla + "/ID_" + pantallaActual);
		jLocal.procesarMensaje(msg + "/Pantalla_" + nombrePantalla + "/ID_"
				+ pantallaActual);
		// jLocal.printValues("JLOCAL EN SERVIDOR");
		// jRemoto.printValues("JREMOTO EN SERVIDOR");
	}

	/**
	 * El servidor envia el mensaje asociado al boton que recibe de parametro
	 * 
	 * @param but
	 */
	private void enviarMensajeCliente(String msg) {
		// Envia un mensaje al servidor
		c.write(msg + "/Pantalla_" + nombrePantalla + "/ID_" + pantallaActual);
		jLocal.procesarMensaje(msg + "/Pantalla_" + nombrePantalla + "/ID_"
				+ pantallaActual);
		jLocal.printValues("JLOCAL EN CLIENTE");
		jRemoto.printValues("JREMOTO EN CLIENTE");
	}

	/**
	 * Devuelve todos los botones al estado como fueron creados y el estado del
	 * simbolo. Todos los botones son desabilitados y el simbolo de control de
	 * comunicación vuelve a la posicion 0: el simbolo no se muestra.
	 */
	private void reset() {
		if (!controlReset) {
			pantalla.reset();
			sent = false;
			estadoSimbolo = 0;
			controlReset = true;
		}
	}

	private String[] separarCadena(String val) {
		String[] temp = val.split("/");
		return temp;
	}

	public Pregunta pasarPreguntaActiva() {
		return pantalla.getPreguntaActiva();
	}

	public void finalizarJuego() {
		String base = "Jugadores: cliente " + jLocal.getNombre()
				+ "  VS  servidor " + jRemoto.getNombre();
		String infoCasas = "Casas seleccionadas:  " + jLocal.getNombre() + ": "
				+ jLocal.getCasa().name + "  " + jRemoto.getNombre() + ": "
				+ jRemoto.getCasa().name;
		
		String[] datosOrganizados = jLocal.recopilarDatos(base,infoCasas,jRemoto.getMisPreguntas(),
				jRemoto.getMisRespuestas(), jRemoto.getConfianzas(),
				jRemoto.getHonestidades());
		
		
		listFilesForFolder(folder);
		app.saveStrings(folder + "/prueba_" + numeroArchivos + ".txt",
				datosOrganizados);
	}

	public void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				numeroArchivos++;
			}
		}
	}
}
