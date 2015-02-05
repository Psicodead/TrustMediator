package buttons;

import processing.core.PApplet;
import processing.core.PConstants;

public class Casa extends Button {

	public Casa(PApplet app, int x, int y, float d, String name) {
		super(app, x, y, d);
		super.name = name;
		super.switchButton = true;
	}

	public void show() {
		if (isVisible()) {
			// Dibuja la casa
			app.noFill();
			app.stroke(255);
			app.strokeWeight(10);
			app.strokeJoin(PConstants.ROUND);
			app.beginShape();
			app.vertex(orig.x - 35, orig.y - 20);
			app.vertex(orig.x - 35, orig.y + 35);
			app.vertex(orig.x + 35, orig.y + 35);
			app.vertex(orig.x + 35, orig.y - 20);
			app.vertex(orig.x, orig.y - 45);
			app.endShape(PConstants.CLOSE);
			// Casa con ventana
			if (name.contains("casaVentana")) {
				app.noStroke();
				app.fill(255);
				app.rect(orig.x - 24, orig.y - 10, 18, 18, 3);
			}
			// Casa con puerta
			if (name.contains("casaPuerta")) {
				app.noStroke();
				app.fill(255);
				app.rect(orig.x + 2, orig.y, 22, 35, 3);
			}
			// Casa con puerta y ventana
			if (name.contains("casaPuertaVentana")) {
				app.noStroke();
				app.fill(255);
				app.rect(orig.x - 24, orig.y - 10, 18, 18, 3);
				app.rect(orig.x + 2, orig.y, 22, 35, 3);
			}
			// Dibuja el arco
			showArc(0, 255, 0, 160, 5);
		}
	}

}
