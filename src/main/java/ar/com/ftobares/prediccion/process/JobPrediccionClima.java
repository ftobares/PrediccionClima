package ar.com.ftobares.prediccion.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ar.com.ftobares.prediccion.model.Planeta;
import ar.com.ftobares.prediccion.model.Posicion;
import ar.com.ftobares.prediccion.model.Pronostico;
import ar.com.ftobares.prediccion.service.ClimaService;
import ar.com.ftobares.prediccion.service.DAOService;

@Component
public class JobPrediccionClima implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger logger = LogManager.getLogger(JobPrediccionClima.class);
	
	@Autowired
	DAOService daoService;
	
	@Autowired
	ClimaService climaService;
	
	private static final int ANIOS = 10;
	private static final int DIAS = 365;

	/**
	 * <p>Metodo que se ejecuta una vez al inicio de la aplicacion.
	 * Lo primero que hace es validar si el calculo para los proximos 10 años
	 * ya se encuentra calculado y persistido. En caso de serlo anula la ejecución.</p>
	 * 
	 * <p>En caso que el ultimo dia no este calculado pero si lo este el primer dia
	 * (sintoma de inconsistencia) se ejecuta el borrado de todos los registros para
	 * realizar una ejecucion limpia.</p>
	 * */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		logger.info("## INICIO PRECALCULO DE CLIMA #");
		
		Pronostico pronosticoEnd = daoService.getPronosticoByDia(new Long(3650));
		Pronostico pronosticoStart = daoService.getPronosticoByDia(new Long(1));
		
		if(pronosticoEnd != null) {
			logger.info("# EJECUCION ANULADA - PRONOSTICOS YA HAN SIDO PRECALCULADOS #");
		}else {
			if(pronosticoStart != null) {
				daoService.borrarBase();
			}
			Planeta vulcanos = new Planeta("Vulcanos",new Posicion(0,1000),1000,5,false);
			Planeta ferengis = new Planeta("Ferengis", new Posicion(0,500),500,1,true);
			Planeta betasoide = new Planeta("Betasoides", new Posicion(0,2000),2000,3,true);
			List<Pronostico> listPronosticos = new ArrayList<Pronostico>();
			
			vulcanos.calcularPosicionesDeOrbita(ANIOS);
			ferengis.calcularPosicionesDeOrbita(ANIOS);
			betasoide.calcularPosicionesDeOrbita(ANIOS);		
			
			climaService.setContext(vulcanos, ferengis, betasoide, ANIOS);
			climaService.calcularPerimetroMaximo();
			
			int dias = ANIOS * DIAS;	
			
			for(int i=0; i <= dias; i++) {
				
				vulcanos.mover(i);
				ferengis.mover(i);
				betasoide.mover(i);
							
				Pronostico pronostico = new Pronostico();
				pronostico.setDia(i);
				pronostico.setClima(climaService.calcularClima());
				listPronosticos.add(pronostico);
			}			
			daoService.insertarPronosticos(listPronosticos);
		}
		
		logger.info("## FIN PRECALCULO DE CLIMA #");
	}
}
