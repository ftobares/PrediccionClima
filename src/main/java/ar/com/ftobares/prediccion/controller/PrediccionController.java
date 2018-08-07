package ar.com.ftobares.prediccion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ftobares.prediccion.model.ClimaTypes;
import ar.com.ftobares.prediccion.model.CustomResponse;
import ar.com.ftobares.prediccion.model.Pronostico;
import ar.com.ftobares.prediccion.repository.ClimaRepository;
import ar.com.ftobares.prediccion.service.impl.DAOServiceImpl;

@RestController
@RequestMapping(value = "/prediccion")
public class PrediccionController {
	
	@Autowired
	Dispatcher dispatcher;
	
	/**
	 * Request para obtener el clima para el dia solicitado
	 * */
	@RequestMapping(value = "/clima/{dia}", method = RequestMethod.GET)
    public Pronostico obtenerPronosticoByDia(@PathVariable("dia") Long dia) {		
		return dispatcher.getPronosticoByDia(dia);
    }
	
	/**
	 * Request para obtener la cantidad de dias que estuvo lloviendo
	 * */
	@RequestMapping(value = "/cantidadDiasDeLluvia", method = RequestMethod.GET)
    public CustomResponse cantidadDiasDeLluvia() {
    	return dispatcher.getCantidadDiasDeLluvia();
    }
	
	/**
	 * Request para obtener la cantidad de dias que estuvo lloviendo de forma intensa
	 * */
	@RequestMapping(value = "/cantidadDiasDeLluviaIntensa", method = RequestMethod.GET)
    public CustomResponse cantidadDiasDeLluviaIntensa() {
    	return dispatcher.getCantidadDiasDeLluviaIntensa();
    }
	
	/**
	 * Request para obtener que dias hubo un pico de lluvia
	 * */
	@RequestMapping(value = "/listDiasLluviaIntensa", method = RequestMethod.GET)
    public List<Pronostico> obtenerDiasLluviaIntensa() {
    	return dispatcher.getDiasDeLluviaIntensa();
    }	
	
	/**
	 * Request para obtener la cantidad de dias que hubo sequia
	 * */
	@RequestMapping(value = "/cantidadDiasDeSequia", method = RequestMethod.GET)
    public CustomResponse cantidadDiasDeSequia() {
    	return dispatcher.getCantidadDiasDeSequia();
    }
	
	/**
	 * Request para obtener la cantidad de dias que hubo condiciones optimas
	 * */
	@RequestMapping(value = "/cantidadDiasCondOptimas", method = RequestMethod.GET)
    public CustomResponse cantidadDiasCondicionesOptimas() {
    	return dispatcher.getCantidadDiasCondicionesOptimas();
    }
	
	/**
	 * <b>ATENCION:</b><br>
	 * Este metodo BORRA la base de datos actual.<br>
	 * 
	 * Request para forzar el calculo del clima para los proximos 10 años,
	 * sin importar si ya esta calculado. 
	 * */
	@RequestMapping(value = "/precalculoForzado", method = RequestMethod.GET)
    public CustomResponse forzarPrecalculo() {
		return dispatcher.precalculoForzado();		
    }	
}
