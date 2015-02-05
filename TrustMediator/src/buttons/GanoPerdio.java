package buttons;

import processing.core.*;

public class GanoPerdio {

	PApplet app;
	PVector pos;

	public GanoPerdio(PApplet app) {
		this.app = app;
		pos = new PVector(app.width / 2, app.height / 2);
	}

	public void show(boolean val) {
		if (val) {
			// si gano
			app.noStroke();
			app.fill(10, 225, 45);
			app.rect(0, 0, app.width, app.height);
			app.fill(20);
			app.quad(pos.x - 114, pos.y - 23, pos.x - 151, pos.y + 13,
					pos.x - 39, pos.y + 125, pos.x - 3, pos.y + 89);
			app.quad(pos.x - 39, pos.y + 125, pos.x + 151, pos.y - 65,
					pos.x + 114, pos.y - 101, pos.x - 75, pos.y + 89);

			app.textAlign(PConstants.CENTER);
			// app.textFont(robotoThin, 16);
			app.text("Por favor, haz clic aquí para responder un cuestionario",
					app.width / 2, 550);
		} else {
			// si perdio
			app.noStroke();
			app.fill(249, 19, 19);
			app.rect(0, 0, app.width, app.height);
			app.fill(20);
			app.quad(pos.x - 115, pos.y - 80, pos.x - 80, pos.y - 115,
					pos.x + 115, pos.y + 80, pos.x + 80, pos.y + 115);
			app.quad(pos.x + 115, pos.y - 80, pos.x + 80, pos.y - 115,
					pos.x - 115, pos.y + 80, pos.x - 80, pos.y + 115);

			app.textAlign(PConstants.CENTER);
			// app.textFont(robotoThin, 16);
			app.text("Por favor, haz clic aquí para responder un cuestionario",
					app.width / 2, 550);
		}
	}
}
