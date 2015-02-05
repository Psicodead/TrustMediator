package buttons;

import processing.core.PApplet;
import processing.core.PConstants;

public class Calcular extends Button {

	float value;
	float valueLimit;

	public Calcular(PApplet app, int x, int y, float diam, String name) {
		super(app, x, y, diam);
		super.name = name;
		value = 0;
		// Valor en grados que indica hasta donde gira
		valueLimit = 180;
	}

	/*
	 * (non-Javadoc) otroListo es la señal que recibe cuando el otro ya opromio
	 * el boton cuando el mouse este encima
	 * 
	 * @see buttons.Button#show(boolean)
	 */
	public void show(boolean otroListo) {
		if (isVisible()) {
			if (!getPressed()) {
				app.textFont(robotoThinBig, 32);
				app.textAlign(PConstants.CENTER);
				app.fill(255);
				app.text(name, orig.x, orig.y + 15);
				// Dibuja el arco
				showArc(255, 180, 0, 190, 10);
			} else {
				if (!otroListo) {
					// si no esta listo
					app.fill(255);
					app.textFont(robotoThinBig, 32);
					app.text("esperado...", orig.x, orig.y + 15);
					showArc(255, 180, 0, 50, 10);
				}
			}
		}
	}
//	public void show(){
//		if (listo) {
//			// cuando el mouse haya hecho click
//			app.strokeWeight(10);
//			if (value < 120) {
//				app.stroke(249, 19, 19);
//			}
//			if (value >= 120 && value < 240) {
//				app.stroke(255, 170, 0);
//			}
//			if (value >= 240) {
//				app.stroke(10, 225, 45);
//			}
//			// Arco indicador
//			app.noFill();
//			app.arc(300, 300, 200, 200, (float) -1.6,
//					(float) -1.6 + PApplet.radians(value));
//
//			// Texto interior
//			app.textSize(40);
//			app.textAlign(PConstants.CENTER);
//			app.fill(255);
//			app.textFont(robotoThinBig, 40);
//			app.text((int) (value / 3.6) + "%", 300, 318);
//			app.fill(200);
//			app.textSize(12);
//			app.textFont(robotoThin, 16);
//
//			// Forma de la línea (puntas curvas o rectas)
//			if (value == 360) {
//				// Punta recta
//				app.strokeCap(PConstants.SQUARE);
//			} else {
//				// Punta curva
//				app.strokeCap(PConstants.ROUND);
//			}
//
//			// Límite del arco
//			if (value < valueLimit) {
//				value += 2;
//			}
//			app.strokeCap(PConstants.ROUND);
//		}
//	}
}
