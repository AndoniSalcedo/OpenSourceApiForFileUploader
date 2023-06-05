package es.uv.andoni.consumer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(
  info = @Info(
    title = "CONSUMER API",
    version = "v1",
    contact = @Contact(
      name = "Andoni Salcedo Navarro",
      email = "ansalna@alumni.uv.es",
      url = "ansalna@alumni.uv.es"
    ),
    license = @License(
      name = "Apache 2.0",
      url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    description = "CONSUMER API for OPEN DATA Aplication"
  ),
  servers = @Server(url = "http://localhost", description = "Production")
)
public class ConsumerApplication {

  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }
}
