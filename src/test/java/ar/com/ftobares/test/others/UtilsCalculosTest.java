package ar.com.ftobares.test.others;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import ar.com.ftobares.prediccion.PrediccionClimaApplication;
import ar.com.ftobares.prediccion.model.Posicion;
import ar.com.ftobares.prediccion.service.impl.UtilsCalculos;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PrediccionClimaApplication.class)
public class UtilsCalculosTest {
	
	private static final Logger logger = LogManager.getLogger(UtilsCalculosTest.class);
	
	private static final String SI = "SI";
	private static final String NO = "NO";
	
	@Autowired
	UtilsCalculos utilsCalculos;
	
	@Test
	public void testTresPuntosEstanAlineados() {		
		
		Posicion p1 = new Posicion(-3,11);
		Posicion p2 = new Posicion(-3,5);
		Posicion p3 = new Posicion(-3,-3);
		
		String result1 = (utilsCalculos.tresPuntosEstanAlineados(p1, p2, p3)) ? SI : NO;
		
		logger.info("Estan alineados => "+result1.toString());
		
		Posicion p4 = new Posicion(-7,-10);
		Posicion p5 = new Posicion(0,-1);
		Posicion p6 = new Posicion(7,8);
		
		String result2 = (utilsCalculos.tresPuntosEstanAlineados(p4, p5, p6)) ? SI : NO;
		
		logger.info("Estan alineados => "+result2.toString());
		
		Posicion p7 = new Posicion(-3,7);
		Posicion p8 = new Posicion(13,-1);
		Posicion p9 = new Posicion(4,6);
		
		String result3 = (utilsCalculos.tresPuntosEstanAlineados(p8, p9, p7)) ? SI : NO;
		
		logger.info("Estan alineados => "+result3.toString());

		assertThat(result1.equals(SI) && result2.equals(SI) && result3.equals(NO));
	}
	
	@Test
	public void testCalcularPerimetro() {
		
		final double perimetroEsperado = 16;
		
		Posicion pos1 = new Posicion(1,0);
		Posicion pos2 = new Posicion(7,0);
		Posicion pos3 = new Posicion(4,4);
		
		double perimetroCalculado = utilsCalculos.calcularPerimetro(pos1, pos2, pos3);
		
		logger.info("perimetroEsperado ("+perimetroEsperado+") == perimetroCalculado ("+perimetroCalculado+")");
		assertThat(perimetroCalculado == perimetroEsperado);		
	}
	
	@Test
	public void testCalcularDistancia() {
		
		final double distanciaEsperada = Math.sqrt(50);
		
		Posicion pos1 = new Posicion(2,1);
		Posicion pos2 = new Posicion(7,6);
		
		double distanciaCalculada = utilsCalculos.calcularDistancia(pos1, pos2);
		
		logger.info("distanciaEsperada ("+distanciaEsperada+") == distanciaCalculada ("+distanciaCalculada+")");
		assertThat(distanciaCalculada == distanciaEsperada);		
	}

}
