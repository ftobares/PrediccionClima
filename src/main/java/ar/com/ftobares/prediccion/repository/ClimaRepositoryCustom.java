package ar.com.ftobares.prediccion.repository;

import java.util.List;

import ar.com.ftobares.prediccion.model.Pronostico;

public interface ClimaRepositoryCustom {
	
	public Long getCantidadDiasDeLluvia();
	
	public Long getCantidadDiasDeSequia();
	
	public Long getCantidadDiasCondicionesOptimas();
	
	public Long getCantidadDiasLluviaIntensa();

	public List<Pronostico> getDiasDeLLuviaIntensa();

}
