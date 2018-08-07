package ar.com.ftobares.prediccion.service;

import java.util.List;

import ar.com.ftobares.prediccion.model.Pronostico;

public interface DAOService {
	
	public void insertarPronosticos(List<Pronostico> pronosticos);
	
	public Pronostico getPronosticoByDia(Long dia);
	
	public Long getCantidadDiasDeLluvia();
	
	public Long getCantidadDiasDeSequia();
	
	public Long getCantidadDiasCondicionesOptimas();
	
	public Long getCantidadDiasDeLluviaIntensa();
	
	public void borrarBase();

	public List<Pronostico> getDiasDeLluviaIntensa();

}
