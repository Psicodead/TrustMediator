package trustServer;

import java.util.ArrayList;

import buttons.*;
import processing.core.*;

/**
 * Esta clase crea objetos pantalla que contienen todos los botones que se ven
 * en cada pantallazo. Cuando un boton es oprimido el objeto pantalla devuelve
 * un mensaje a controladorPantalla para que lo pase al cliente o el servidor y
 * este lo envíe.
 * 
 * @author jsalam
 *
 */
public class Pantalla {
	public PApplet app;
	private int pantallaID;

	// Botones
	public ArrayList<Button> botones;
	public Inicio inicio;
	public Casa casa, casaV, casaP, casaPV;
	public YNButton si, no; // botones si y no
	public Calcular calc;// boton calcular
	public CalcularPorcentaje calcP;
	public Pregunta preg, nombre;// boton preguntar
	public NuevaRonda nRonda; // boton nueva ronda
	public Adivinar adivinar; // boton adivinar
	public Siguiente sig;

	// Variables de control de iteraciones
	private boolean activeBoton;

	public Pantalla(PApplet app) {
		this.app = app;
		botones = new ArrayList<Button>();
		// construye botones
		sig = new Siguiente(app, app.width / 2, 540, (float) 50, "siguiente");
		sig.setEtiqueta("SiguientePantalla");
		inicio = new Inicio(app, app.width / 2, app.height / 2, (float) 55.0,
				"inicial");
		nombre = new Pregunta(app, 480, 415, (float) 45, "nombre");
		nombre.setEtiqueta("Nombre");
		casa = new Casa(app, 200, 260, (float) 170, "casa");
		casaV = new Casa(app, 400, 260, (float) 170.0, "casaVentana");
		casaP = new Casa(app, 200, 450, (float) 170.0, "casaPuerta");
		casaPV = new Casa(app, 400, 450, (float) 170.0, "casaPuertaVentana");
		preg = new Pregunta(app, 480, 315, (float) 45, "pregunta");
		preg.setEtiqueta("");
		preg.setInterrogante(true);
		si = new YNButton(app, 175, 370, 200, "si");
		no = new YNButton(app, 425, 370, 200, "no");
		calc = new Calcular(app, 300, 300, (float) 200, "calcular");
		calcP = new CalcularPorcentaje(app, 300, 300, (float) 200, "calcularP");
		nRonda = new NuevaRonda(app, 540, 540, (float) 50, "reiniciar");
		adivinar = new Adivinar(app, 60, 540, (float) 50, "adivinar");
		botones.add(sig);
		botones.add(inicio);
		botones.add(nombre);
		botones.add(casa);
		botones.add(casaV);
		botones.add(casaP);
		botones.add(casaPV);
		botones.add(preg);
		botones.add(si);
		botones.add(no);
		botones.add(calc);
		botones.add(calcP);
		botones.add(nRonda);
		botones.add(adivinar);

		// Todos los botones se vuelven invisibles
		for (int i = 0; i < botones.size(); i++) {
			botones.get(i).setVisible(false);
		}
	}

	public void show(boolean val) {
		for (int i = 0; i < botones.size(); i++) {
			botones.get(i).show();
			botones.get(i).show(val);
		}
	}

	public void activarBotones(String[] nombres) {
		if (!activeBoton) {
			for (int i = 0; i < botones.size(); i++) {
				for (int j = 0; j < nombres.length; j++) {
					if (botones.get(i).name.equals(nombres[j])) {
						botones.get(i).setupBoton();
					}
				}
				activeBoton = true;
			}
		}
	}

	public void reset() {
		activeBoton = false;
		for (Button b : botones) {
			b.reset();
			b.setVisible(false);
		}
	}

	/**
	 * Devuelve el mensaje del boton presionado en esta pantalla
	 * 
	 * @return
	 */
	public String getMensaje() {
		String temp = pantallaID + " sin mensaje";
		for (Button b : botones) {
			if (b.getPressed()) {
				temp = b.getMensaje();
				b.setEnabled(false);
			}
		}
		return temp;
	}

	public boolean botonPresionado() {
		boolean temp = false;
		for (Button b : botones) {
			if (b.getPressed()) {
				temp = true;
			}
		}
		return temp;
	}

	public void setPantallaID(int ID) {
		pantallaID = ID;
	}

	public Pregunta getPreguntaActiva() {
		Pregunta temp = null;
		for (Button b : botones) {
			if (b instanceof Pregunta && b.getEnabled()) {
				temp = (Pregunta) b;
			}
		}
		return temp;
	}

	public void setupCalcPorcentaje(float val) {
		calcP.setValueLimit(val);
		calcP.setEnabled(false);
	}

	/**
	 * Devuelve true si el boton fue oprimido
	 * @return
	 */
	public boolean getNuevaRonda() {
		boolean temp = false;
		for (Button s : botones) {
			if (s instanceof NuevaRonda)
				if (s.getPressed())
					temp = true;
		}
		return temp;
	}
	
	/**
	 * Devuelve true si el boton fue oprimido
	 * @return
	 */
	public boolean getAdivinar() {
		boolean temp = false;
		for (Button s : botones) {
			if (s instanceof Adivinar)
				if (s.getPressed())
					temp = true;
		}
		return temp;
	}
}
