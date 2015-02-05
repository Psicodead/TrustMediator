package trustServer;

import java.util.ArrayList;

import buttons.Casa;
import processing.core.*;

/**
 * Esta clase permite construir jugadores locales (jLocal o Server) y jugador
 * remoto (jRemoto o Client). Sea del lado del cliente o del lado del servidor,
 * jLocal es el jugador que corre en la maquina donde se hospeda el applet y
 * jRemoto es el que se ejecuta en la máquina a la cual esta conectada la
 * maquina huesped. El cliente siempre envia los datos al servidor quien asigna
 * los valores recibidos de los botones del cliente a las variables de la
 * instancia jRemoto del servidor.
 * 
 * @author jsalam
 * 
 */
public class Jugador {
	PApplet app;
	private Casa miCasa;
	private Casa casaQueAdivine;
	private String nombre;
	private String miPregunta;
	private String miRespuesta;
	private float miHonest;
	private float miConvic;
	private float nuestroBalance;
	private String[] datosPrueba;
	public ArrayList<String> misPreguntas;
	public ArrayList<String> misRespuestas;
	public ArrayList<Float> balances;
	public ArrayList<Float> honestidades;
	public ArrayList<Float> confianzas;
	public boolean gano;
	public boolean controlRonda;
	
	public Jugador(PApplet app) {
		this.app = app;
		misPreguntas = new ArrayList<String>();
		misRespuestas = new ArrayList<String>();
		balances = new ArrayList<Float>();
		honestidades = new ArrayList<Float>();
		confianzas = new ArrayList<Float>();
		miCasa = null;
		casaQueAdivine = null;
		miPregunta = null;
		controlRonda = false;
	}

	/**
	 * 
	 * @param val
	 */
	public void procesarMensaje(String input) {

		if (input.startsWith("siguientePantalla")
				|| input.startsWith("siguientePantallaNR")) {
			String[] data = input.split(":");
			input = data[1];

		}

		if (input.startsWith("perdí")) {
			if (input.equals("perdí"))
				gano = true;
		}

		PApplet.println("mensaje a procesar: " + input);

		try {
			// Nombre
			if (input.startsWith("nombre")) { // si el dato viene con el tag:
												// nombre
				// rompe la cadena
				String[] data = input.split("/");
				nombre = data[1];
			}
			// Mi Casa y miCasaAdivinada
			if (input.startsWith("casa")) {
				/*
				 * si el dato viene con el tag: casa rompe la cadena y construye
				 * una instancia de la casa escogida por el jugador remoto
				 */
				String[] data = input.split("/");
				if (miCasa == null) {
					if (data[0].equals("casa")) {
						miCasa = new Casa(app, 200, 260, (float) 170, "casa");
					} else if (data[0].equals("casaVentana")) {
						miCasa = new Casa(app, 400, 260, (float) 170.0,
								"casaVentana");
					} else if (data[0].equals("casaPuerta")) {
						miCasa = new Casa(app, 200, 450, (float) 170.0,
								"casaPuerta");
					} else if (data[0].equals("casaPuertaVentana")) {
						miCasa = new Casa(app, 400, 450, (float) 170.0,
								"casaPuertaVentana");
					}
				} else {
					/*
					 * si mi casa ya fue escogida la siguiente casa que jLocal
					 * seleccione es la casa que adivinó
					 */
					if (data[0].equals("casa")) {
						casaQueAdivine = new Casa(app, 200, 260, (float) 170,
								"casa");
					} else if (data[0].equals("casaVentana")) {
						casaQueAdivine = new Casa(app, 400, 260, (float) 170.0,
								"casaVentana");
					} else if (data[0].equals("casaPuerta")) {
						casaQueAdivine = new Casa(app, 200, 450, (float) 170.0,
								"casaPuerta");
					} else if (data[0].equals("casaPuertaVentana")) {
						casaQueAdivine = new Casa(app, 400, 450, (float) 170.0,
								"casaPuertaVentana");
					}

				}
			}
			// Mi Pregunta
			if (input.startsWith("pregunta")) { // si el dato viene con el tag:
												// pregunta
				// rompe la cadena
				String[] data = input.split("/");
				miPregunta = data[1];
			}
			// Mi Honestidad y convicción
			if (input.startsWith("si") || input.startsWith("no")) {
				// rompe la cadena
				String[] data = input.split("/");
				if (data[1].equals("Pantalla_Respuesta al Oponente")) {
					miRespuesta = data[0];
				}

				if (data[1].equals("Pantalla_Autopregunta Honestidad")) {
					if (data[0].equals("si")) {
						miHonest = 1;
					}
				}
				if (data[1].equals("Pantalla_Respuesta Conviccion")) {
					if (data[0].equals("si")) {
						miConvic = 1;
					}
				}
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void printValues(String val) {
		PApplet.println(val);
		PApplet.println("  nombre :" + nombre);
		PApplet.println("  pregunta :" + miPregunta);
		PApplet.println("  honestidad :" + miHonest);
		PApplet.println("  Creencia :" + miConvic);
		try {
			PApplet.println("  Ultimo balance :" + nuestroBalance);
			PApplet.println("  Mi casa : " + miCasa.name);
			PApplet.println("  Mi casa adivinada: " + casaQueAdivine.name);
			if (miCasa.equals(casaQueAdivine)) {
				PApplet.println("  Jugador Local Adivinó su propia casa!!!!");
			}
		} catch (Exception e) {
		}
		for (int i = 0; i < misRespuestas.size(); i++) {
			System.out.println("  Ronda " + i + ": " + misRespuestas.get(i));
		}
		PApplet.println("...");
	}

	public void setCasa(Casa val) {
		miCasa = val;
	}

	public void setNombre(String val) {
		nombre = val;
	}
	public String getNombre() {
		return nombre;
	}


	public void setPregunta(String val) {
		miPregunta = val;
	}

	public void setBalance(float val) {
		nuestroBalance = val;
	}

	public void setCasaQueAdivine(Casa val) {
		casaQueAdivine = val;
	}

	public Casa getCasa() {
		return miCasa;
	}

	public String getPregunta() {
		return miPregunta;
	}

	public float getBalance() {
		return nuestroBalance;
	}

	public void calcularBalance(Jugador remoto) {
		// PApplet.println("jLocalH: "+miHonest + "  jLocalC: "+miConvic +
		// "  jRemotoH: "+remoto.miHonest + "  jRemotoH: "+remoto.miConvic);
		float balance = (miHonest + miConvic + remoto.miHonest + remoto.miConvic) / 4;
		setBalance(balance);
	}

	public String getRespuesta() {
		return miRespuesta;
	}

	public void saveAndReset() {
		balances.add(nuestroBalance);
		misRespuestas.add(miRespuesta);
		misPreguntas.add(miPregunta);
		honestidades.add(miHonest);
		confianzas.add(miConvic);
		nuestroBalance = 0;
		miHonest = 0;
		miConvic = 0;
		for (int i = 0; i < balances.size(); i++) {
			System.out.println("ronda: " + i + "  pregunte: "
					+ misPreguntas.get(i) + " respondi: "
					+ misRespuestas.get(i) + " fui honesto: "
					+ honestidades.get(i) + "  confie: " + confianzas.get(i)
					+ "  balance: " + balances.get(i));
		}
	}

	public boolean verificarSiGano() {
		try {
			if (miCasa.name.equals(casaQueAdivine.name)) {
				gano = true;
			}
		} catch (Exception e) {
			gano = false;
		}
		return gano;
	}

	public boolean verificarResultado() {
		return gano;
	}

	/**
	 * este metodo permite crear el string que sera guardado en un txt, coge la
	 * informacion de los arraylist de las rondas y crea un string compuesto
	 * utilizando la informacion propia y la informacion del usuarioDos, es
	 * decir el cliente, este metodo solo lo llamara el servidor
	 * 
	 * @param ArrayList
	 *            <String>, pregCliente, pregunta del cliente
	 * @param ArrayList
	 *            <String>, respCliente, respuesta del cliente
	 * @param ArrayList
	 *            <String>, confCliente, confianza del cliente
	 * @param ArrayList
	 *            <String>, honestCliente, honestidad del cliente
	 */

	public String[] recopilarDatos(String a, String b,ArrayList<String> pregCliente,
			ArrayList<String> respCliente, ArrayList<Float> confCliente,
			ArrayList<Float> honestCliente) {
		datosPrueba  = new String[balances.size()+2];
		datosPrueba[0]=a;
		datosPrueba[1]=b;
		for (int i = 0; i < balances.size(); i++) {
			datosPrueba[i+2] = ("ronda: " + (i+1) + "\n" + " Servidor= pregunte: "
					+ misPreguntas.get(i) + " respondi: "
					+ misRespuestas.get(i) + " fui honesto: "
					+ honestidades.get(i) + "  confie: " + confianzas.get(i)
					+ "\n" + " Cliente= pregunte: " + pregCliente.get(i)
					+ " respondi: " + respCliente.get(i) + " fui honesto: "
					+ honestCliente.get(i) + "  confie: " + confCliente.get(i)
					+ "\n" + "  balance= " + balances.get(i)+"\n"+"\n");
		}
		return datosPrueba;
	}

	public ArrayList<String> getMisPreguntas() {
		return misPreguntas;
	}

	public void setMisPreguntas(ArrayList<String> misPreguntas) {
		this.misPreguntas = misPreguntas;
	}

	public ArrayList<String> getMisRespuestas() {
		return misRespuestas;
	}

	public void setMisRespuestas(ArrayList<String> misRespuestas) {
		this.misRespuestas = misRespuestas;
	}

	public ArrayList<Float> getHonestidades() {
		return honestidades;
	}

	public void setHonestidades(ArrayList<Float> honestidades) {
		this.honestidades = honestidades;
	}

	public ArrayList<Float> getConfianzas() {
		return confianzas;
	}

	public void setConfianzas(ArrayList<Float> confianzas) {
		this.confianzas = confianzas;
	}
}
