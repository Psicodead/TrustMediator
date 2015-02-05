package buttons;

import processing.core.*;

public class Siguiente extends Button {

	public PApplet app;
	public String etiqueta;
	boolean activo;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param diam
	 * @param name
	 */
	public Siguiente(PApplet app, int x, int y, float diam, String name) {
		super(app, x, y, diam);
		super.name = name;
		this.app = app;
		etiqueta = "Use setEtiqueta() de la clase Siguiente para cambiar este texto";
	}

	public void show() {
		if (isVisible()) {
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
			app.textFont(robotoThinSmall, 12);
			app.text(etiqueta, orig.x - 4, orig.y + 43);
		}
	}

	public void setEtiqueta(String val) {
		etiqueta = val;
	}
}
