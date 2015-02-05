package buttons;

import processing.core.*;

public class YNButton extends Button {

	public boolean switchButton;

	public YNButton(PApplet app, int x, int y, float diam, String name) {
		super(app, x, y, diam);
		super.name = name;
		this.switchButton = true;
	}

	public void show() {
		if (isVisible()) {
			// Texto interior
			app.textSize(40);
			app.textAlign(PConstants.CENTER, PConstants.CENTER);
			app.fill(255);
			app.textFont(robotoThinBig, 40);
			app.text(name, orig.x, orig.y);
			if (name == "si") {
				if (!getPressed()) {
					app.stroke(50);
					app.noFill();
					app.arc(orig.x, orig.y, diam, diam, (float) -1.6,
							(float) PApplet.radians(360));
					// Dibuja el arco dinámico
					showArc(0, 255, 0, 160, 5);
				} else {
					app.fill(0, 255, 0);
					app.noStroke();
					app.arc(orig.x, orig.y, diam, diam, (float) -1.6,
							(float) PApplet.radians(360));
					app.fill(0);
					app.quad(orig.x - 64, orig.y + 7, orig.x - 17, orig.y + 54,
							orig.x + 2, orig.y + 35, orig.x - 45, orig.y - 13);
					app.quad(orig.x + 46, orig.y - 49, orig.x + 66,
							orig.y - 28, orig.x - 17, orig.y + 54, orig.x - 36,
							orig.y + 35);
				}
			}
			if (name == "no") {
				if (!getPressed()) {
					app.stroke(50);
					app.noFill();
					app.arc(orig.x, orig.y, diam, diam, (float) -1.6,
							(float) PApplet.radians(360));
					// Dibuja el arco dinámico
					showArc(255, 0, 0, 160, 5);
				} else {
					app.fill(255, 0, 0);
					app.noStroke();
					app.arc(orig.x, orig.y, diam, diam, (float) -1.6,
							(float) PApplet.radians(360));
					app.fill(0);
					app.quad(orig.x - 51, orig.y + 32, orig.x + 32,
							orig.y - 51, orig.x + 51, orig.y - 32, orig.x - 32,
							orig.y + 51);
					app.quad(orig.x - 51, orig.y - 32, orig.x + 32,
							orig.y + 51, orig.x + 51, orig.y + 32, orig.x - 32,
							orig.y - 51);
				}
			}
		}
	}
}
