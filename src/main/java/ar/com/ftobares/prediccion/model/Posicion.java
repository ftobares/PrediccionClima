package ar.com.ftobares.prediccion.model;

public class Posicion {

	private float xPos;

	private float yPos;
	
	public Posicion() {
		
	}
		
	public Posicion(float xPos, float yPos) {		
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Posicion actual en X
	 * */
	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	/**
	 * Posicion actual en Y
	 * */	
	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

}
