package ar.com.ftobares.prediccion.service.impl;

import org.springframework.stereotype.Service;

import ar.com.ftobares.prediccion.model.Posicion;

@Service(value="utilsCalculos")
public class UtilsCalculos {
	
	/**
	 * Calcula la distancia entre dos puntos, basandose en la teoria de pitagoras<br>
	 * Formula:<br>
		<b>d = raiz{ (x2 - x1)^2 + (y2 - y1)^2 }</b>
	 * */
	public double calcularDistancia(Posicion pos1, Posicion pos2) {	
		
		double diferenciaEnX = pos2.getxPos() - pos1.getxPos();
		double diferenciaEnY = pos2.getyPos() - pos1.getyPos();
		double diferenciaEnXAlCuadrado = Math.pow(diferenciaEnX, 2);
		double diferenciaEnYAlCuadrado = Math.pow(diferenciaEnY, 2);
		
		return Math.sqrt((diferenciaEnXAlCuadrado+diferenciaEnYAlCuadrado));
	}	

	/**
	 * Calcula el perimetro de un triangulo. Se asume que las posiciones justamente
	 * forman un triangulo. Se utiliza el metodo <b>calcularDistancia</b>
	 * */
	public double calcularPerimetro(Posicion pos1, Posicion pos2, Posicion pos3) {
		
		return (calcularDistancia(pos1, pos2) +
				calcularDistancia(pos2, pos3) +
				 calcularDistancia(pos3, pos1));
	}
	
	/**
	 * Calcula la orientacion de un triangulo. 
	 * */
	public double obtenerOrientacion(Posicion pos1, Posicion pos2, Posicion pos3) {		
		double orientacion =  ((pos1.getxPos() - pos3.getxPos()) * (pos2.getyPos() - pos3.getyPos()))
				- ((pos1.getyPos() - pos3.getyPos()) * (pos2.getxPos() - pos3.getxPos()));
			
		return (orientacion >= 0) ? 1 : -1;
	}
	
	/**
	 * Obtiene la orientacion de 3 puntos y el sol. Es decir, calcula la orientacion de los
	 * tres triangulos que se forman entre los tres puntos y el sol.
	 * */
	public Boolean calcularOrientacionPlanetasYSol(Posicion posicion1, Posicion posicion2, Posicion posicion3, double orientacionPlanetas) {
		
		Posicion sol = new Posicion(0,0);
		
		double orientacionTriangulo1 = obtenerOrientacion(posicion1, posicion2, sol);
		double orientacionTriangulo2 = obtenerOrientacion(posicion2, posicion3, sol);
		double orientacionTriangulo3 = obtenerOrientacion(posicion3, posicion1, sol);
		
		if((orientacionTriangulo1 == orientacionPlanetas) &&
		(orientacionTriangulo2 == orientacionPlanetas) &&
		(orientacionTriangulo3 == orientacionPlanetas)) {
			return Boolean.TRUE;
		}else {			
			return Boolean.FALSE;
		}
	}	
	
	/**
	 * Determina si el sol se encuentra en la zona interna formada por el triangulo.
	 * Para hacerlo, verifica que la orientacion de los 3 puntos y 
	 * la orientacion de los 3 puntos mas el Sol sea la misma.
	 * */
	public Boolean solEstaEnAreaInterna(Posicion posicion1, Posicion posicion2, Posicion posicion3) {
		
		double orientacionTriangulo = obtenerOrientacion(posicion1, posicion2, posicion3);
		
		//Si la orientacion del triangulo formado por los planetas
		//y la orientacion de los 3 triangulos formados por 2 planetas y el Sol
		//son iguales, entonces el Sol se encuentra en el area interna		
		Boolean esInterno = calcularOrientacionPlanetasYSol(posicion1, posicion2, posicion3,orientacionTriangulo);
				
		return esInterno;
	}
	
	/**
	 * Calculo si tres puntos estan alineados entre si
	 * */
	public Boolean tresPuntosEstanAlineados (Posicion posicion1,
			Posicion posicion2,
			Posicion posicion3) {
		
		/* 	Primero me fijo si estan alineados a traves de una de sus coordenadas.
			Segundo, sabiendo que los vectores que obtenga tendran pendiente distinta de 0
			busco si estan alineados entre si comparando que las coordenadas de los vectores
			posicion de cada uno sean proporcionales */
		if((posicion1.getxPos() == posicion2.getxPos() &&
				posicion2.getxPos() == posicion3.getxPos())
			|| (posicion1.getyPos() == posicion2.getyPos() &&
					posicion2.getyPos() == posicion3.getyPos())) {
			return Boolean.TRUE;
		}else{
			// vector entre puntos 1 y 2
			// (x2 - x1 ; y2 - y1)
			Posicion vectorPosicion12 = new Posicion();			
			vectorPosicion12.setxPos(posicion2.getxPos() - posicion1.getxPos());
			vectorPosicion12.setyPos(posicion2.getyPos() - posicion1.getyPos());
			
			// vector entre puntos 2 y 3
			// (x3 - x2 ; y3 - y2)		
			Posicion vectorPosicion23 = new Posicion();
			vectorPosicion23.setxPos(posicion3.getxPos() - posicion2.getxPos());
			vectorPosicion23.setyPos(posicion3.getyPos() - posicion2.getyPos());

			//Para evitar errores al dividir por 0, obtengo 
			//la proporcionalidad mediante la multiplicacion
			//haciendo un pasaje de terminos
			// (x2 - x1) / (x3 - x2) = (y2 - y1) / (y3 - y2)
			// (x2 - x1) * (y3 - y2) = (y2 - y1) * (x3 - x2)
			double valorProporcional1 = vectorPosicion12.getxPos() * vectorPosicion23.getyPos();
			double valorProporcional2 = vectorPosicion12.getyPos() * vectorPosicion23.getxPos();
				
			if(valorProporcional1 == valorProporcional2) {
				return Boolean.TRUE;
			}else {
				return Boolean.FALSE;
			}
		}					
	}
	
	/**
	 * Calculo si tres puntos estan alineados entre si y ademas estan alineados 
	 * con el centro de coordenadas
	 * */
	public Boolean tresPuntosEstanAlineadosConElCentro (Posicion posicion1,
			Posicion posicion2,
			Posicion posicion3) {
				
		//Me fijo si estan alienados entre si y con el sol a traves de una de sus coordenadas
		//Si no lo estan, verifico que los 3 vectores sea proporcionales.
		if((posicion1.getxPos() == posicion2.getxPos() &&
				posicion2.getxPos() == posicion3.getxPos() &&
				posicion3.getxPos() == 0)
			|| (posicion1.getyPos() == posicion2.getyPos() &&
					posicion2.getyPos() == posicion3.getyPos() &&
					posicion3.getyPos() == 0)) {
			return Boolean.TRUE;
		}else{			
			Posicion posicionSol = new Posicion(0,0);
			
			// vector entre puntos 1 y 2
			// (x2 - x1 ; y2 - y1)
			Posicion vectorPosicion12 = new Posicion();			
			vectorPosicion12.setxPos(posicion2.getxPos() - posicion1.getxPos());
			vectorPosicion12.setyPos(posicion2.getyPos() - posicion1.getyPos());
			
			// vector entre puntos 2 y 3
			// (x3 - x2 ; y3 - y2)		
			Posicion vectorPosicion23 = new Posicion();
			vectorPosicion23.setxPos(posicion3.getxPos() - posicion2.getxPos());
			vectorPosicion23.setyPos(posicion3.getyPos() - posicion2.getyPos());
			
			// vector entre puntos 3 y 4
			// (x4 - x3 ; y4 - y3)		
			Posicion vectorPosicion34 = new Posicion();
			vectorPosicion34.setxPos(posicionSol.getxPos() - posicion3.getxPos());
			vectorPosicion34.setyPos(posicionSol.getyPos() - posicion3.getyPos());
			
			//Para evitar errores al dividir por 0, obtengo 
			//la proporcionalidad mediante la multiplicacion
			//haciendo un pasaje de terminos
			// (x2 - x1) / (x3 - x2) = (y2 - y1) / (y3 - y2)
			// (x2 - x1) * (y3 - y2) = (y2 - y1) * (x3 - x2)
			double valorProporcional1 = vectorPosicion12.getxPos() * vectorPosicion23.getyPos();
			double valorProporcional2 = vectorPosicion12.getyPos() * vectorPosicion23.getxPos();
			
			// (x3 - x2) / (x4 - x3) = (y3 - y2) / (y4 - y3)
			// (x3 - x2) * (y4 - y3) = (y3 - y2) * (x4 - x3)
			double valorProporcional3 = vectorPosicion23.getxPos() * vectorPosicion34.getyPos();
			double valorProporcional4 = vectorPosicion23.getyPos() * vectorPosicion34.getxPos();
			
			
			if(valorProporcional1 == valorProporcional2 &&
					valorProporcional3 == valorProporcional4 &&
					valorProporcional1 == valorProporcional3) {
				return Boolean.TRUE;
			}else {
				return Boolean.FALSE;
			}
		}		
	}	

}
