package ar.com.ftobares.prediccion.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Planeta {
	
	private static final int diasPorAnio = 365;
	
	private static final Logger log = LogManager.getLogger(Planeta.class);
	
	/**
	 * Nombre del planeta o civilizacion
	 * */
	private String nombre;
	
	/**
	 * Posicion actual del planeta
	 * */
	private Posicion posicion;
	
	/**
	 * Lista de posiciones que tendra el planeta dia a dia<br>
	 * 
	 * Observacion:<br>
	 * Como nos interesa el pronostico diario, se toma un valor discreto y no continuo
	 * del movimiento de los planetas.
	 * */
	private List<Posicion> posicionesOrbita;

	/**
	 * Distancia en KM al Sol
	 * */
	private int distanciaSol;
	
	/**
	 * Cantidad de GRADOS por DIA que se traslada
	 * */
	private int velocidadAngular;
		
	/**
	 * Posicion angular inicial, determinada de forma arbitraria en 90 grados
	 * */
	private int posicionAngularInicial;
	
	/**
	 * Direccion del movimiento
	 * 1: movimiento horario
	 * -1: movimiento antihorario
	 * */
	private int direccionMovimiento;
	
	public Planeta(String nombre, 
			Posicion pos, 
			int distanciaSol, 
			int velocidadAngular,
			Boolean movimientoHorario) {
		this.nombre = nombre;
		this.posicion = pos;
		this.distanciaSol = distanciaSol;
		this.velocidadAngular = velocidadAngular;		
		this.posicionAngularInicial = 90; //90 grados
		this.direccionMovimiento = (movimientoHorario) ? 1 : -1; 
	}	
	
	/**
	 * Calcula las posibles posicione que tendra el planeta alrededor de su orbita
	 * por un periodo determinado. Se toma un movimiento discreto (dia a dia) y
	 * no continuo.
	 * */
	public void calcularPosicionesDeOrbita(int anios) {
		
		//Inicializo lista de posiciones de la orbita
		this.posicionesOrbita = new ArrayList<Posicion>();
		
		int dias = anios * diasPorAnio;
		
		int posAngularTemp = this.posicionAngularInicial;		
		
		//calculo posiciones
		for(int i=0;i<=dias;i++) {
			//calcula proxima posicion
			// x = x0 + R * cos (a)
			// y = y0 + R * sen (a)
			// (x0,y0) = (0,0) / R: radio / a: posicion angular
			float xPos; float yPos;
			if(posAngularTemp == 90 || posAngularTemp == 270) {
				xPos = this.distanciaSol * (int)Math.cos(Math.toRadians(posAngularTemp));
				yPos = this.distanciaSol * (int)Math.sin(Math.toRadians(posAngularTemp));
			}else {
				BigDecimal posAngularX = new BigDecimal(Double.toString(Math.cos(Math.toRadians(posAngularTemp)) * (float)(this.distanciaSol) )).setScale(2, RoundingMode.HALF_UP);
				BigDecimal posAngularY = new BigDecimal(Double.toString(Math.sin(Math.toRadians(posAngularTemp)) * (float)(this.distanciaSol) )).setScale(2, RoundingMode.HALF_UP);
				xPos = posAngularX.floatValue();
				yPos = posAngularY.floatValue();						
			}
			
			//instancio objeto
			Posicion pos = new Posicion(xPos,yPos);
			
			//agrego a la lista
			this.posicionesOrbita.add(pos);
			
			//calculo la proxima posicion angular segun su velocidad y tipo de movimiento
			posAngularTemp = posAngularTemp - (this.direccionMovimiento * this.velocidadAngular);
		}
		
	}
	
	/**
	 * Mueve al planeta a la posicion de la orbita indicada por el dia
	 * */
	public void mover(int dia) {		
		this.posicion = this.posicionesOrbita.get(dia);		
	}
	
	public List<Posicion> getOrbita(){
		return this.posicionesOrbita;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getDistanciaSol() {
		return distanciaSol;
	}

	public void setDistanciaSol(Integer distanciaSol) {
		this.distanciaSol = distanciaSol;
	}

	public Integer getVelocidadTraslacion() {
		return velocidadAngular;
	}

	public void setVelocidadTraslacion(Integer velocidadTraslacion) {
		this.velocidadAngular = velocidadTraslacion;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}	
	
}
