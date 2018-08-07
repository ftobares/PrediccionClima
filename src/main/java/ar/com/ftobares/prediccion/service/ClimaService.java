package ar.com.ftobares.prediccion.service;

import ar.com.ftobares.prediccion.model.Planeta;

public interface ClimaService {
	
	public void setContext(Planeta planeta1, Planeta planeta2, Planeta planeta3, int periodoTiempo);
	
	public String calcularClima();
	
	public void calcularPerimetroMaximo();	

}
