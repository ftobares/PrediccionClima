package ar.com.ftobares.prediccion.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ftobares.prediccion.model.Pronostico;
import ar.com.ftobares.prediccion.repository.ClimaRepository;
import ar.com.ftobares.prediccion.repository.ClimaRepositoryCustom;
import ar.com.ftobares.prediccion.service.DAOService;

@Service(value="daoService")
public class DAOServiceImpl implements DAOService {
	
	private static final Logger logger = LogManager.getLogger(DAOServiceImpl.class);
	
	@Autowired
    private ClimaRepository repository;
	
	@Autowired
	private ClimaRepositoryCustom customRepository;	
	
	@Override
	public void insertarPronosticos(List<Pronostico> pronosticos) {
		repository.saveAll(pronosticos);
	}		

	@Override
	public Pronostico getPronosticoByDia(Long dia) {
		logger.info("# OBTENGO PRONOSTICO PARA EL DIA "+dia+" #");
		Pronostico pronostico = repository.getPronosticoByDia(dia);
		return pronostico;
	}

	@Override
	public Long getCantidadDiasDeLluvia() {
		logger.info("# OBTENGO CANTIDAD DE DIAS CON LLUVIA #");
		return customRepository.getCantidadDiasDeLluvia();
	}
	
	@Override
	public Long getCantidadDiasDeLluviaIntensa() {
		logger.info("# OBTENGO CANTIDAD DE DIAS CON LLUVIA INTENSA #");
		return customRepository.getCantidadDiasLluviaIntensa();
	}		

	@Override
	public Long getCantidadDiasDeSequia() {
		logger.info("# OBTENGO CANTIDAD DE DIAS CON SEQUIA #");
		return customRepository.getCantidadDiasDeSequia();
	}

	@Override
	public Long getCantidadDiasCondicionesOptimas() {
		logger.info("# OBTENGO CANTIDAD DE DIAS CON CONDICIONES OPTIMAS #");
		return customRepository.getCantidadDiasCondicionesOptimas();
	}
	
	public void borrarBase() {
		repository.deleteAll();
		logger.info("# BD BORRADA PARA NUEVA EJECUCION #");
	}

	@Override
	public List<Pronostico> getDiasDeLluviaIntensa() {
		logger.info("# OBTENGO DIAS DE LLUVIA INTENSA #");
		return customRepository.getDiasDeLLuviaIntensa();
	}
}
