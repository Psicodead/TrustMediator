package trustServer;

import processing.core.*;

public class SimboloComunicacion {

	PVector pos;
	PApplet app;
	int diam = 40;
	public PFont robotoThinSmall;

	/**
	 * @param app
	 *            The applet from the main class
	 * @param x
	 *            X position on the applet
	 * @param y
	 *            Y position on the applet
	 */
	public SimboloComunicacion(PApplet app, int x, int y) {
		this.app = app;
		pos = new PVector(x, y);
		robotoThinSmall = app.loadFont("Roboto-Thin-12.vlw");
	}

	/**
	 * @param val
	 */
	public void show(int val) {
		switch (val) {
		case 0: // *********************** no se muestra nada
			break;
		case 1: // *********************** se muestra enviado
			app.noFill();
			app.stroke(120);
			app.strokeWeight(3);
			app.arc(pos.x, pos.y, diam, diam, (float) -1.6,
					(float) Math.toRadians(360));
			app.fill(120);
			app.noStroke();
			app.ellipse(pos.x, pos.y, 4, 4);
			app.ellipse(pos.x - 7, pos.y, 4, 4);
			app.ellipse(pos.x + 7, pos.y, 4, 4);
			//
			app.textFont(robotoThinSmall, 12);
			app.textAlign(PConstants.CENTER);
			app.fill(0, 185, 255);
			app.text("Esperando oponente ", pos.x - 90, pos.y + 5);
			// text("Esperando", pos.x, pos.y+(diam/2)+15);
			break;

		case 2: // *********************** se muestra esperando
			app.fill(0, 185, 255);
			app.noFill();
			app.textAlign(PConstants.CENTER);
			app.textFont(robotoThinSmall, 12);
			// rect(pos.x-148, pos.y-23, 170, 48, 50);
			app.fill(20);
			//
			app.noStroke();
			app.fill(0, 185, 255);
			app.arc(pos.x, pos.y, diam, diam, (float) -1.6,
					(float) PApplet.radians(360));
			app.noFill();
			app.stroke(20);
			app.strokeWeight(5);
			app.strokeJoin(PConstants.ROUND);
			app.beginShape();
			app.vertex(pos.x - 10, pos.y);
			app.vertex(pos.x - 2, pos.y + 8);
			app.vertex(pos.x + 10, pos.y - 8);
			app.endShape();
			//
			app.text("Oponente listo", pos.x - 90, pos.y + 5);
			// text("Enviado", pos.x, pos.y+(diam/2)+15);
			break;

		case 3: // *********************** el oponenete quiere una nueva ronda

			app.noFill();
			app.stroke(0, 255, 0);
			app.strokeWeight(3);
			app.strokeJoin(PConstants.ROUND);
			//app.fill(0, 185, 255);
			app.arc(pos.x, pos.y, diam, diam, (float) -1.6,
					(float) PApplet.radians(360));
			app.beginShape();
			app.vertex(pos.x + 4, pos.y - 15);
			app.vertex(pos.x, pos.y - 11);
			app.vertex(pos.x + 3, pos.y - 4);
			app.endShape();
			app.arc(pos.x, pos.y, 22, 22, (float) -1.2,
					(float) PApplet.radians(180));
			app.textFont(robotoThinSmall, 12);
			app.textAlign(PConstants.CENTER);
			app.text("Nueva ronda", pos.x - 5, pos.y + (diam / 2) + 15);

			break;

		case 4: // *********************** el oponenete quiere adivinar
			
			app.noFill();
			app.stroke(0, 255, 0);
			app.strokeWeight(3);
			app.strokeJoin(PConstants.ROUND);
			app.arc(pos.x, pos.y, diam, diam, (float) -1.6,
					(float) PApplet.radians(360));
			app.beginShape();
			app.vertex(pos.x + 10, pos.y + 10);
			app.vertex(pos.x + 10, pos.y - 5);
			app.vertex(pos.x, pos.y - 13);
			app.vertex(pos.x - 10, pos.y - 5);
			app.vertex(pos.x - 10, pos.y + 10);
			app.endShape(PConstants.CLOSE);
			app.textFont(robotoThinSmall, 14);
			app.text("?", pos.x + 1, pos.y + 5);
			app.textFont(robotoThinSmall, 12);
			app.textAlign(PConstants.CENTER);
			app.text("Adivinar", pos.x, pos.y + (diam / 2) + 15);
		}
	}
}
