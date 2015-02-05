package buttons;

import processing.core.*;

/**
 * @author jsalam
 *
 */

public class Pregunta extends Button {
	PApplet app;
	PVector posText;
	String texto;
	String signoPreguntaInicial;
	String signoPreguntaFinal;
	String pregunta;
	String etiqueta;
	boolean activo;
	boolean interrogante; // activa desactiva signo de pregunta

	// Cursor parpadeante
	boolean barraVertical;
	String barra;
	int contadorParpadeoCursor;

	/**
	 * Constructor
	 * 
	 * @param x pos X
	 * @param y pos Y
	 * @param diam button diameter
	 * @param name label
	 */
	public Pregunta(PApplet app, int x, int y, float diam, String name) {
		super(app, x, y, diam);
		super.name = name;
		this.app = app;
		posText = new PVector(x - 330, y + 7);
		signoPreguntaFinal = "?";
		signoPreguntaInicial = "¿";
		texto = "";
		pregunta = "pregunta/";
		etiqueta = "Use setEtiqueta() para cambiar este texto";
		interrogante = false;
	}

	/**
	 * Displays the button and the attached labels
	 * 
	 * @param signoPregunta
	 *            true include question marks in the text field
	 */
	public void show() {
		if(isVisible()){
		app.noFill();
		app.stroke(255);
		app.strokeWeight(3);
		app.strokeJoin(PConstants.ROUND);
		app.beginShape();
		app.vertex(orig.x - 3, orig.y - 8);
		app.vertex(orig.x + 7, orig.y);
		app.vertex(orig.x - 3, orig.y + 8);
		app.endShape();
		// Dibuja el arco dinámico
		showArc(0, 255, 0, 160, 3);
		// Dibuja campo de texto
		preguntar(interrogante);
		if (getPressed()) {
			if (!texto.equals("")) {
				pregunta = texto;
				texto = "";
			}
		}
		}
	}

	/**
	 * @param signoPregunta
	 *            true include question marks in the text field
	 */
	private void preguntar(boolean signoPregunta) {
		// forma del campo de texto
		app.noFill();
		app.stroke(50);
		app.strokeWeight(40);
		app.strokeJoin(PConstants.ROUND);
		app.beginShape();
		app.vertex(posText.x, posText.y - 8);
		app.vertex(posText.x + 280, posText.y - 8);
		app.endShape();
		app.fill(255);
		app.textAlign(PConstants.LEFT);
		app.textFont(super.robotoLight, 16);
		if (signoPregunta) {
			app.text("¿" + texto + "?", posText.x - 4, posText.y - 2);
		} else {
			app.text(texto, posText.x - 4, posText.y - 2);
		}
		// Cursor parpadeante
		contadorParpadeoCursor++;
		if (contadorParpadeoCursor % 30 == 0) {
			barraVertical = !barraVertical;
		}

		if (barraVertical) {
			barra = "|";
		} else if (!barraVertical) {
			barra = "";
		}
		app.text(barra, posText.x + app.textWidth(texto) - 3, posText.y - 2);
		app.textFont(robotoLight, 16);
		app.text(etiqueta, posText.x - 4, posText.y + 33);
	}

	/**
	 * Builds a String as it is typed
	 * 
	 * @param tecla
	 *            the received char
	 */
	public void teclado(char tecla) {
		if (!getPressed()) {
			if (texto.length() < 36) {
				texto = texto + tecla;
			}
		}
	}

	/**
	 * Deletes the last character in the string
	 * 
	 * @param borrar
	 *            char to be deleted
	 */
	public void teclado(int borrar) {
		if (texto.length() > 0) {
			texto = texto.substring(0, texto.length() - 1);
		}
	}

	public String getPregunta() {
		return pregunta + texto;
	}
	public String getMensaje(){
		String temp = "siguientePantalla:"+name +"/"+getPregunta();
		return temp;
	}

	public void resetPreg() {
		pregunta = "";
	}

	public void setEtiqueta(String val) {
		etiqueta = val;
	}
	
	public void setInterrogante(boolean val){
		interrogante = val;
	}
}
