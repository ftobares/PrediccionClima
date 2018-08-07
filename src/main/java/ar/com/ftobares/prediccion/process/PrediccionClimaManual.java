package ar.com.ftobares.prediccion.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.ftobares.prediccion.model.Planeta;
import ar.com.ftobares.prediccion.model.Posicion;
import ar.com.ftobares.prediccion.model.Pronostico;
import ar.com.ftobares.prediccion.service.ClimaService;
import ar.com.ftobares.prediccion.service.DAOService;

@Component
public class PrediccionClimaManual implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(PrediccionClimaManual.class);
	
	@Autowired
	DAOService daoService;
	
	@Autowired
	ClimaService climaService;
	
	public PrediccionClimaManual() {
		this.status = Boolean.FALSE;
	}
	
	private Boolean status;
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public void run() {

		logger.info("## INICIO FORZADO - PRECALCULO DE CLIMA #");
		
		try{
			Pronostico pronosticoSample = daoService.getPronosticoByDia(new Long(1));
			if(pronosticoSample != null) {
				daoService.borrarBase();
			}
			
			Planeta vulcanos = new Planeta("Vulcanos",new Posicion(0,1000),1000,5,false);
			Planeta ferengis = new Planeta("Ferengis", new Posicion(0,500),500,1,true);
			Planeta betasoide = new Planeta("Betasoides", new Posicion(0,2000),2000,3,true);
			List<Pronostico> listPronosticos = new ArrayList<Pronostico>();
			
			vulcanos.calcularPosicionesDeOrbita(10);
			ferengis.calcularPosicionesDeOrbita(10);
			betasoide.calcularPosicionesDeOrbita(10);		
			
			climaService.setContext(vulcanos, ferengis, betasoide, 10);
			climaService.calcularPerimetroMaximo();
			
			int dias = 3650;
			
			for(int i=0; i <= dias; i++) {
				
				vulcanos.mover(i);
				ferengis.mover(i);
				betasoide.mover(i);
							
				Pronostico pronostico = new Pronostico();
				pronostico.setDia(i);
				pronostico.setClima(climaService.calcularClima());										
			}
			
			daoService.insertarPronosticos(listPronosticos);
			
			this.setStatus(Boolean.TRUE);
			
		}catch(Exception ex) {
			logger.error("## ERROR precalculoForzado ##",ex);
			this.setStatus(Boolean.FALSE);			
		}
		
		logger.info("## FIN FORZADO - PRECALCULO DE CLIMA #");				
	}

}
