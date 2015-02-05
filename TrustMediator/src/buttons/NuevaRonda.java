package buttons;

import processing.core.PApplet;
import processing.core.PConstants;

public class NuevaRonda extends Button {

	public NuevaRonda(PApplet app, int x, int y, float diam, String name) {
		super(app, x, y, diam);
		super.name = name;
	}

	public void show() {
		if (isVisible()) {
			app.noFill();
			app.stroke(120);
			app.strokeWeight(3);
			app.strokeJoin(PConstants.ROUND);
			app.beginShape();
			app.vertex(orig.x + 4, orig.y - 15);
			app.vertex(orig.x, orig.y - 11);
			app.vertex(orig.x + 3, orig.y - 4);
			app.endShape();
			app.arc(orig.x, orig.y, 22, 22, (float) -1.2,
					(float) PApplet.radians(180));
			// Dibuja el arco dinámico
			showArc(0, 255, 0, 160, 3);
			//
			app.textFont(robotoThinSmall, 12);
			app.textAlign(PConstants.CENTER);
			app.text("Nueva ronda", orig.x, orig.y + (diam / 2) + 15);
		}
	}
	/**
	 * 
	 * Retorna un mensaje que contiene el nombreDelBoton y lka etiqueta para
	 * cambiar el simbolo de comunicacion al caso 3
	 * 
	 * @return
	 */
	public String getMensaje() {
		String temp = "siguientePantallaNR:" + name;
		return temp;
	}
}
