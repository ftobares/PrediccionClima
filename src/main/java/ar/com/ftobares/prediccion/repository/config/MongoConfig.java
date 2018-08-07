package ar.com.ftobares.prediccion.repository.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
	
	MongoClientURI uri = new MongoClientURI("mongodb+srv://ftobares:WGK41HFx5pYxuP89@mycluster-tpzjw.gcp.mongodb.net/test?retryWrites=true");
  
    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected String getMappingBasePackage() {
        return "ar.com.ftobares.prediccion";
    }

	@Override
	public MongoClient mongoClient() {		
		return new MongoClient(uri);
	}
	
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }	
}
