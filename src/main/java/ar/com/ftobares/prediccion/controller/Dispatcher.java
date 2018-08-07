package ar.com.ftobares.prediccion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ar.com.ftobares.prediccion.model.ClimaTypes;
import ar.com.ftobares.prediccion.model.CustomResponse;
import ar.com.ftobares.prediccion.model.Pronostico;
import ar.com.ftobares.prediccion.process.PrediccionClimaManual;
import ar.com.ftobares.prediccion.service.DAOService;

@Service(value="dispatcher")
public class Dispatcher {
	
	private static final Logger logger = LogManager.getLogger(Dispatcher.class);
	
	@Autowired
	DAOService daoService;
	
	@Autowired
	PrediccionClimaManual pcManual;
	
	public Pronostico getPronosticoByDia(Long dia) {
		Pronostico pronostico = daoService.getPronosticoByDia(dia);
		if(pronostico == null) {
			pronostico = new Pronostico();
			pronostico.setDia(dia.shortValue());
			pronostico.setClima(ClimaTypes.NULL.toString());			
		}
		return pronostico;
	}

	public CustomResponse getCantidadDiasDeLluvia() {
		CustomResponse response = 
				new CustomResponse(HttpStatus.OK.name(),
						daoService.getCantidadDiasDeLluvia().toString());					
		return response;
	}

	public CustomResponse getCantidadDiasDeLluviaIntensa() {
		CustomResponse response = 
				new CustomResponse(HttpStatus.OK.name(),
						daoService.getCantidadDiasDeLluviaIntensa().toString());					
		return response;
	}

	public CustomResponse getCantidadDiasDeSequia() {
		CustomResponse response = 
				new CustomResponse(HttpStatus.OK.name(),
						daoService.getCantidadDiasDeSequia().toString());					
		return response;
	}

	public CustomResponse getCantidadDiasCondicionesOptimas() {
		CustomResponse response = 
				new CustomResponse(HttpStatus.OK.name(),
						daoService.getCantidadDiasCondicionesOptimas().toString());					
		return response;
	}

	public CustomResponse precalculoForzado() {	
		
		synchronized(pcManual) {
			Thread t_ejecucion = new Thread(pcManual);			
			t_ejecucion.start();
			try {
				t_ejecucion.join();
			} catch (InterruptedException e) {
				logger.error("# ERROR EN THREAD DE EJECUCION MANUAL PrediccionClimaManual.class #", e);
			}			
		}
		
		String code;
		String respuesta;
		if(pcManual.getStatus()) {
			code = HttpStatus.CREATED.name();
			respuesta = new String("Ejecucion finalizada");
		}else {
			code = HttpStatus.ACCEPTED.name();
			respuesta = new String("Ejecucion fallida");
		}
		
		return new CustomResponse(code,respuesta);
	}

	public List<Pronostico> getDiasDeLluviaIntensa() {
		return daoService.getDiasDeLluviaIntensa();
	}
}
