package ar.com.ftobares.prediccion.model;

public enum ClimaTypes {
	
	SEQUIA("Sequia"),
	CONDICIONES_OPTIMAS("Condiciones optimas"),
	LLUVIA("Lluvia"),
	LLUVIA_INTENSA("Lluvia intensa"),
	NULL("NO CALCULADO");
	
	private String clima;
	
	ClimaTypes(String clima){
		this.clima = clima;
	}
	
	public String getClima() {
		return clima;
	}

}
