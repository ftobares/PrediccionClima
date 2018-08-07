package ar.com.ftobares.prediccion.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ar.com.ftobares.prediccion.model.ClimaTypes;
import ar.com.ftobares.prediccion.model.Pronostico;

@Repository
public class ClimaRepositoryCustomImpl implements ClimaRepositoryCustom {
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Long getCantidadDiasDeLluvia() {
		Query query = new Query(Criteria.where("clima").is(ClimaTypes.LLUVIA.toString()));		
		return mongoTemplate.count(query, new String("pronostico"));
	}

	@Override
	public Long getCantidadDiasDeSequia() {
		Query query = new Query(Criteria.where("clima").is(ClimaTypes.SEQUIA.toString()));		
		return mongoTemplate.count(query, new String("pronostico"));
	}

	@Override
	public Long getCantidadDiasCondicionesOptimas() {
		Query query = new Query(Criteria.where("clima").is(ClimaTypes.CONDICIONES_OPTIMAS.toString()));		
		return mongoTemplate.count(query, new String("pronostico"));
	}

	@Override
	public Long getCantidadDiasLluviaIntensa() {
		Query query = new Query(Criteria.where("clima").is(ClimaTypes.LLUVIA_INTENSA.toString()));		
		return mongoTemplate.count(query, new String("pronostico"));
	}

	@Override
	public List<Pronostico> getDiasDeLLuviaIntensa() {
		Query query = new Query(Criteria.where("clima").is(ClimaTypes.LLUVIA_INTENSA.toString()));		
		return mongoTemplate.find(query, Pronostico.class);		
	}

}
