package ar.com.ftobares.prediccion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.ftobares.prediccion.model.Pronostico;

public interface ClimaRepository extends MongoRepository<Pronostico, String> {
	
	public Pronostico getPronosticoByDia(Long dia);
	
}
