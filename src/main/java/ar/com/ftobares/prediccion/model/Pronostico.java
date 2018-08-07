package ar.com.ftobares.prediccion.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pronostico")
public class Pronostico {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	private int dia;
	
	private String clima;
	
	public Pronostico() {}
	
	public Pronostico(int dia, String clima) {
		this.dia = dia;
		this.clima = clima;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	

}
