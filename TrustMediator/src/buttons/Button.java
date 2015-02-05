package buttons;

import processing.core.*;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Button implements MouseListener {
	public PApplet app;
	public PVector orig;
	public PFont robotoThin; 
	public PFont robotoThinBig;
	public PFont robotoLight;
	public PFont robotoThinSmall;
	public float wdth; // length of sensitive area
	public float hght; // height of sensitive area
	public float diam; // diameter of sensitive area
	private boolean pressed;
	public boolean switchButton;
	public String name;
	
	// variables for spinning effect
	private int valueLimit;
	private float easing;
	private boolean devolverse;
	
	// variables de control de operacion del boton
	private boolean enabled;
	private boolean setup;
	private boolean visible;
	//
	public String mensaje;

	/**
	 * This constructor creates a rectangular sensitive area
	 */
	public Button(PApplet app, int x, int y, float wdth, float hght) {
		this.app = app;
		PVector o = new PVector(x, y);
		this.orig = o;
		this.wdth = wdth;
		this.hght = hght;
		setSwitchButton(false);
		valueLimit = 360;
		devolverse = false;
		easing = (float) 0.05;
		enabled = true;
		visible = true;
		mensaje = null;
		setup=false;
		robotoThin = app.loadFont("Roboto-Thin-16.vlw");
		robotoThinBig = app.loadFont("Roboto-Thin-40.vlw");
		robotoLight = app.loadFont("Roboto-Light-24.vlw");
		robotoThinSmall = app.loadFont("Roboto-Thin-12.vlw");
		app.addMouseListener(this);
	}

	/**
	 * This constructor creates a circular sensitive area
	 */
	public Button(PApplet app, int x, int y, float diam) {
		this.app = app;
		PVector o = new PVector(x, y);
		this.orig = o;
		this.wdth = 0;
		this.hght = 0;
		this.diam = diam;
		setSwitchButton(false);
		valueLimit = 360;
		devolverse = false;
		easing = (float) 0.05;
		mensaje = null;
		enabled = true;
		visible = true;
		setup=false;
		robotoThin = app.loadFont("Roboto-Thin-16.vlw");
		robotoThinBig = app.loadFont("Roboto-Thin-40.vlw");
		robotoLight = app.loadFont("Roboto-Light-24.vlw");
		robotoThinSmall = app.loadFont("Roboto-Thin-12.vlw");
		app.addMouseListener(this);
	}

	/**
	 * This method detects mouse over. It does not detect the mouse if the
	 * parameter is false
	 * 
	 * @param enabled
	 * @return
	 */
	public boolean detectOver(boolean enabled) {
		this.enabled = enabled;
		boolean isOver = false;
		// in case the button is not enabled the button is not in operation.
		if (enabled) {
			// if it is a rectangle
			if (wdth != 0 || hght != 0) {
				if (app.mouseX > orig.x && app.mouseX < orig.x + wdth
						&& app.mouseY > orig.y && app.mouseY < orig.y + hght) {
					isOver = true;
				} else {
					isOver = false;
				}
			} else {
				PVector mousePos = new PVector(app.mouseX, app.mouseY);
				if (orig.dist(mousePos) < diam / 2) {
					isOver = true;
				} else {
					isOver = false;
				}
			}
		}
		return isOver;
	}

	/**
	 * The method returns a float value to control spinning effect on buttons
	 */
	float ease = 0;

	public float getEase() {

		// efecto de circulo que gira
		if (detectOver(enabled)) {
			// Límite del arco
			float dx = valueLimit - ease;
			float gx = valueLimit + ease;
			if (PApplet.abs(dx) >= 0) {
				if (ease == 360) {
					devolverse = true;
				}

				if (ease > -50 && ease < -20) {
					devolverse = false;
				}

				if (devolverse) {
					ease -= gx * easing / 5;
				} else {
					ease += dx * easing;
				}
			}
		} else {
			ease = 0;
		} // fin efecto
		return ease;
	} // end getEase

	/**
	 * The method draws a spinning wheel on buttons
	 */
	public void showArc(int red, int green, int blue, int alpha, int weight) {
		app.stroke(red, green, blue, alpha);
		app.strokeWeight(weight);
		app.noFill();
		// grey arch
		if (!detectOver(enabled)) {
			app.stroke(200, 40);
			app.arc(orig.x, orig.y, diam, diam, (float) -1.6,
					(float) PApplet.radians(360));
		}
		if (!getPressed()) {
			// dynamic arch
			app.pushMatrix();
			app.translate(orig.x, orig.y);
			app.rotate(PApplet.radians(getEase()));
			app.translate(-orig.x, -orig.y);
			app.arc(orig.x, orig.y, diam, diam, (float) -1.6, (float) -1.6
					+ PApplet.radians(getEase()));
			app.popMatrix();
		} else {
			// solid color arch
			app.stroke(red, green, blue);
			app.arc(orig.x, orig.y, diam, diam, (float) -1.6,
					(float) PApplet.radians(360));
		}
	}

	private void printMessage(String message) {
		if (detectOver(enabled))
			PApplet.println("boton: " + name + "" + message);
	}

	/**
	 * Retorna un mensaje que contiene: nombreDelBoton/mesajeAdicional;
	 * 
	 * @param val
	 * @return
	 */
	public String getMensaje(String val) {
		String temp = "siguientePantalla:"+name + "/" + "adicioneme un mensaje como parametro";
		return temp;
	}

	/**
	 * 
	 * Retorna un mensaje que contiene el nombreDelBoton;
	 * 
	 * @return
	 */
	public String getMensaje() {
		String temp = "siguientePantalla:" + name;
		return temp;
	}

	/**
	 * This method resets the button to original values
	 */
	public void reset() {
		pressed = false;
		enabled = false;
		switchButton = false;
		devolverse = false;
		setup = false;
	}
	/**
	 * Instala el boton para que sea visible y operable en la pantalla
	 */
	public void setupBoton(){
		if (!setup){
			setVisible(true);
			setEnabled(true);
			setup = true;
		}
	}
	// ---------- setters and getters -------------

	public boolean getSwitchButton() {
		return switchButton;
	}

	public void setSwitchButton(boolean switchButton) {
		this.switchButton = switchButton;
	}

	/**
	 * metodo sin cuerpo
	 * 
	 * @param b
	 */
	public void show(boolean b) {
		// TODO Auto-generated method stub

	}

	/**
	 * metodo sin cuerpo
	 * 
	 * @param b
	 */
	public void show(int b) {
		// TODO Auto-generated method stub

	}

	/**
	 * metodo sin cuerpo
	 */
	public void show() {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns the pressed status
	 * 
	 * @return
	 */
	public boolean getPressed() {
		return pressed;
	}

	/**
	 * Switch the pressed status
	 */
	public void setPressed() {
		pressed = !pressed;
	}
	
	/**
	 * @param val
	 */
	public void setPressed(boolean val) {
		pressed = val;
	}

	/**
	   * 
	   */
	public void setEnabled(boolean val) {
		enabled = val;
	}

	/**
	   * 
	   */
	public boolean getEnabled() {
		return enabled;
	}
	
	/**
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	

	// ---------------- implementing MouseEvent methods ----------------
	public void mousePressed(MouseEvent e) {
		// printMessage("mouse pressed");
	}

	public void mouseReleased(MouseEvent e) {
		// printMessage("mouseReleased");
	}

	public void mouseOver() {
		// It needs to be activated in the show()method
		printMessage("mouse Over & pressed = " + pressed);
	}

	public void mouseExited(MouseEvent e) {
		// println("mouse exited");
	}

	public void mouseEntered(MouseEvent e) {
		// println("mouse entered");
	}

	public void mouseClicked(MouseEvent e) {
		if (detectOver(enabled))
			setPressed();
		// printMessage(". Mouse Clicked, pressed="+ pressed);
	}

}
