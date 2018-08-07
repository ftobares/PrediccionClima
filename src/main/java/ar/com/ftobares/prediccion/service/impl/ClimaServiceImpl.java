package ar.com.ftobares.prediccion.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ftobares.prediccion.model.ClimaTypes;
import ar.com.ftobares.prediccion.model.Planeta;
import ar.com.ftobares.prediccion.model.Posicion;
import ar.com.ftobares.prediccion.service.ClimaService;

@Service(value="climaService")
public class ClimaServiceImpl implements ClimaService {
	
	private static final Logger logger = LogManager.getLogger(ClimaServiceImpl.class);
	
	@Autowired
	UtilsCalculos utilsCalculos;

	private double perimetroMaximo;
	
	private Planeta planeta1;
	
	private Planeta planeta2;
	
	private Planeta planeta3;
	
	private int anios;
	
	private int dias;
	
	private static final int DIAS_PROMEDIO = 365;
	
	/**
	 * Metodo para setear el contexto del sistema. Si bien se considera como parametro de entrada
	 * el periodo de tiempo, no esta desarrollado el soporte para tomar periodos superiores a 10 años
	 * */
	public void setContext(Planeta planeta1, Planeta planeta2, Planeta planeta3, int periodoTiempo) {		
		this.planeta1 = planeta1;
		this.planeta2 = planeta2;
		this.planeta3 = planeta3;
		this.anios = periodoTiempo;
		this.dias = periodoTiempo * DIAS_PROMEDIO;
		logger.info("# CARGADO PLANETA "+planeta1.getNombre()+" #");
		logger.info("# CARGADO PLANETA "+planeta2.getNombre()+" #");
		logger.info("# CARGADO PLANETA "+planeta3.getNombre()+" #");
	}
	
	/**
	 * Metodo principal, encargado de calcular el clima. Devuelve su valor en string
	 * */
	public String calcularClima(){
		
		String climaActual;
		
		if(utilsCalculos.tresPuntosEstanAlineadosConElCentro(this.planeta1.getPosicion(), this.planeta2.getPosicion(), this.planeta3.getPosicion())) {
			climaActual = ClimaTypes.SEQUIA.toString();
		}else if(utilsCalculos.tresPuntosEstanAlineados(this.planeta1.getPosicion(), this.planeta2.getPosicion(), this.planeta3.getPosicion())) {
			climaActual = ClimaTypes.CONDICIONES_OPTIMAS.toString();
		}else if(utilsCalculos.solEstaEnAreaInterna(this.planeta1.getPosicion(), this.planeta2.getPosicion(), this.planeta3.getPosicion())) {
			if(perimetroTrianguloEsMaximo(this.planeta1.getPosicion(), this.planeta2.getPosicion(), this.planeta3.getPosicion())) {
				climaActual = ClimaTypes.LLUVIA_INTENSA.toString();
			}else {
				climaActual = ClimaTypes.LLUVIA.toString();
			}
		}else {
			climaActual = new String ("Desconocido");
		}
	
		return climaActual;
	}
	
	/**
	 * Calcula el perimetro maximo que puede obtenerse en un periodo determinado
	 * */
	public void calcularPerimetroMaximo() {
		
		double perimetroMaximo = 0;
		for(int i=0; i <= this.dias; i++) {
			this.planeta1.mover(i);
			this.planeta2.mover(i);
			this.planeta3.mover(i);
			double perimetroActual = utilsCalculos.calcularPerimetro(this.planeta1.getPosicion(), this.planeta2.getPosicion(), this.planeta3.getPosicion());
			if(perimetroActual > perimetroMaximo) {
				perimetroMaximo = perimetroActual;
			}
		}
		
		this.perimetroMaximo = perimetroMaximo;
	}
	
	/**
	 * Determina si el perimetro que forman los 3 puntos es igual al perimetro maximo calculado
	 * */
	protected boolean perimetroTrianguloEsMaximo(Posicion posicion1, Posicion posicion2, Posicion posicion3) {
		
		double perimetroActual = utilsCalculos.calcularPerimetro(posicion1, posicion2, posicion3);
		
		return (perimetroActual == this.perimetroMaximo) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	public Planeta getPlaneta1() {
		return planeta1;
	}

	public void setPlaneta1(Planeta planeta1) {
		this.planeta1 = planeta1;
	}

	public Planeta getPlaneta2() {
		return planeta2;
	}

	public void setPlaneta2(Planeta planeta2) {
		this.planeta2 = planeta2;
	}

	public Planeta getPlaneta3() {
		return planeta3;
	}

	public void setPlaneta3(Planeta planeta3) {
		this.planeta3 = planeta3;
	}

	public int getAnios() {
		return anios;
	}

	public void setAnios(int anios) {
		this.anios = anios;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public double getPerimetroMaximo() {
		return perimetroMaximo;
	}

	public void setPerimetroMaximo(double perimetroMaximo) {
		this.perimetroMaximo = perimetroMaximo;
	}	

}
