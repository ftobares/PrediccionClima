package ar.com.ftobares.test.others;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import ar.com.ftobares.prediccion.PrediccionClimaApplication;
import ar.com.ftobares.prediccion.model.Planeta;
import ar.com.ftobares.prediccion.model.Posicion;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PrediccionClimaApplication.class)
public class MovimientoSistemaTest{	
	
	private static final int DIAS = 365;
	private static final int ANIOS = 1;
	
	@Test
	public void testMovimientoPlaneta() {
		Planeta vulcanos = new Planeta("Vulcanos",new Posicion(0,1000),1000,5,false);
		vulcanos.calcularPosicionesDeOrbita(ANIOS);
		
		int posiciones = 0;
		
		for(int i=0; i <= DIAS; i++) {
			vulcanos.mover(i);
			posiciones++;
		}
		
		assertThat(posiciones == vulcanos.getOrbita().size());
	}
}
