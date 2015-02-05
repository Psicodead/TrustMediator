package buttons;

import processing.core.PApplet;
import processing.core.PConstants;

public class Adivinar extends Button {
	public Adivinar(PApplet app, int x, int y, float diam, String name) {
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
			app.vertex(orig.x + 10, orig.y + 10);
			app.vertex(orig.x + 10, orig.y - 5);
			app.vertex(orig.x, orig.y - 13);
			app.vertex(orig.x - 10, orig.y - 5);
			app.vertex(orig.x - 10, orig.y + 10);
			app.endShape(PConstants.CLOSE);
			app.textFont(robotoLight, 14);
			app.text("?", orig.x + 1, orig.y + 5);
			// Dibuja el arco dinámico
			showArc(0, 255, 0, 160, 3);
			//
			app.textFont(robotoThinSmall, 12);
			app.textAlign(PConstants.CENTER);
			app.text("Adivinar", orig.x, orig.y + (diam / 2) + 15);
		}
	}
	public String getMensaje() {
		String temp = "siguientePantallaAD:" + name;
		return temp;
	}
}
