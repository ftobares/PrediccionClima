package ar.com.ftobares.test.funcionales;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import ar.com.ftobares.prediccion.PrediccionClimaApplication;
import ar.com.ftobares.prediccion.model.ClimaTypes;
import ar.com.ftobares.prediccion.model.CustomResponse;
import ar.com.ftobares.prediccion.model.Pronostico;
import ar.com.ftobares.prediccion.service.DAOService;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=PrediccionClimaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	DAOService service;
	
	@Autowired
	
	
    @Test
    public void testClimaPorDia() throws Exception {
    	Pronostico result = this.restTemplate.getForObject("/prediccion/clima/360", Pronostico.class);
		assertThat(result.getClima()).isEqualTo(ClimaTypes.SEQUIA.toString()); 	
    	
    }
    
    @Test
    public void testCantidadDiasDeLluvia() throws Exception {
    	CustomResponse result = this.restTemplate.getForObject("/prediccion/cantidadDiasDeLluvia", CustomResponse.class);
		assertThat(result.getMessage()).isEqualTo(service.getCantidadDiasDeLluvia().toString()); 	
    	
    }
    
    @Test
    public void testCantidadDiasDeLluviaIntensa() throws Exception {            	    
    	CustomResponse result = this.restTemplate.getForObject("/prediccion/cantidadDiasDeLluviaIntensa", CustomResponse.class);
		assertThat(result.getMessage()).isEqualTo(service.getCantidadDiasDeLluviaIntensa().toString());
    }
    
    @Test
    public void testCantidadDiasDeSequia() throws Exception {
    	CustomResponse result = this.restTemplate.getForObject("/prediccion/cantidadDiasDeSequia", CustomResponse.class);
		assertThat(result.getMessage()).isEqualTo(service.getCantidadDiasDeSequia().toString()); 	
    	
    }
    
    @Test
    public void testCantidadDiasCondicionesOptimas() throws Exception {
    	CustomResponse result = this.restTemplate.getForObject("/prediccion/cantidadDiasCondOptimas", CustomResponse.class);
		assertThat(result.getMessage()).isEqualTo(service.getCantidadDiasCondicionesOptimas().toString());    	
    }
    
    @Test
    public void testListaDiasDeLluviaIntensa() throws Exception {
    	Pronostico[] pronosticoArray = this.restTemplate.getForObject("/prediccion/listDiasLluviaIntensa",Pronostico[].class);
		List<Pronostico> result = Arrays.asList(pronosticoArray);
    	assertThat(result.size()).isEqualTo(service.getDiasDeLluviaIntensa().size());    	
    }
}
