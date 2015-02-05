package buttons;

import processing.core.PApplet;
import processing.core.PConstants;

public class Inicio extends Button {

	public Inicio(PApplet app, int x, int y, float diam, String name) {
		super(app, x, y, diam);
		super.name = name;
		super.switchButton = true;
	}

	public void show() {
		if (isVisible()) {
			// Arco indicador
			// showArc(0,185,255,130,5);
			// Dibujo de la casa
			app.noFill();
			app.stroke(255);
			app.strokeWeight(10);
			app.strokeJoin(PConstants.ROUND);
			app.beginShape();
			app.vertex(orig.x + 35, orig.y + 35);
			app.vertex(orig.x + 35, orig.y - 20);
			app.vertex(orig.x, orig.y - 45);
			app.vertex(orig.x - 35, orig.y - 20);
			app.vertex(orig.x - 35, orig.y + 35);
			app.vertex(orig.x + 15, orig.y + 35);
			app.endShape();

			app.fill(255);
			app.noStroke();
			app.beginShape();
			app.vertex(orig.x - 16, orig.y);
			app.vertex(orig.x - 4, orig.y + 11);
			app.vertex(orig.x + 17, orig.y - 9);
			app.vertex(orig.x + 12, orig.y - 14);
			app.vertex(orig.x - 4, orig.y + 1);
			app.vertex(orig.x - 11, orig.y - 5);
			app.endShape();

			app.fill(0, 185, 255);
			app.textFont(robotoLight, 18);
			app.textAlign(PConstants.CENTER);
			app.text("TRUST", orig.x - 11, orig.y + 59);
		}
	}
}
